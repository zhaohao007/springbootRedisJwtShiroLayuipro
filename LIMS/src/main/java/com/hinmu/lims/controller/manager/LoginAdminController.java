package com.hinmu.lims.controller.manager;


import com.hinmu.lims.config.aoplog.MethodLog;
import com.hinmu.lims.controller.BaseController;
import com.hinmu.lims.exception.ApiClientException;
import com.hinmu.lims.model.Constant;
import com.hinmu.lims.model.entity.SysAdminUserEntity;
import com.hinmu.lims.model.enums.LoginClientTypeEnum;
import com.hinmu.lims.model.reqbean.LoginReqValid;
import com.hinmu.lims.model.respbean.RespResultMessage;
import com.hinmu.lims.service.ISysAdminUserService;
import com.hinmu.lims.service.UserRoleMenuConService;
import com.hinmu.lims.shiro.annotation.LoginUser;
import com.hinmu.lims.shiro.annotation.LoginUserBean;
import com.hinmu.lims.util.jwt.JwtUtil;
import com.hinmu.lims.util.redis.JedisUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.*;

@RequestMapping("/api/admin/login")
@RestController
public class LoginAdminController  extends BaseController {
    /**
     * RefreshToken过期时间
     */
    @Value("${refreshTokenExpireTime}")
    private String refreshTokenExpireTime;
    @Autowired
    private ISysAdminUserService sysAdminUserService;
    @Autowired
    private UserRoleMenuConService userRoleMenuConService;

    @PostMapping("/user")
    @MethodLog(remarks = "登陆")
    public RespResultMessage login(@RequestBody LoginReqValid loginReqValid, HttpServletResponse httpServletResponse) {
        //验证输入账号密码是否不正确
        SysAdminUserEntity sysAdminUserEntity = sysAdminUserService.findLoginByAccountPassword(loginReqValid);
        if (sysAdminUserEntity == null) {
            throw new ApiClientException("common.account.password.error");
        }


        LoginClientTypeEnum adminClient = LoginClientTypeEnum.ADMIN_CLENT;

        //生成JWT
        // 清除可能存在的Shiro权限信息缓存
        if (JedisUtil.exists(Constant.getPREFIX_SHIRO_CACHE(adminClient) + sysAdminUserEntity.getAccount())) {
            JedisUtil.delKey(Constant.getPREFIX_SHIRO_CACHE(adminClient)  + sysAdminUserEntity.getAccount());
        }
        // 设置RefreshToken，时间戳为当前时间戳，直接设置即可(不用先删后设，会覆盖已有的RefreshToken)
        String currentTimeMillis = String.valueOf(System.currentTimeMillis());
        JedisUtil.setObject(Constant.getPREFIX_SHIRO_REFRESH_TOKEN(adminClient)  + sysAdminUserEntity.getAccount(), currentTimeMillis, Integer.parseInt(refreshTokenExpireTime));
        // 从Header中Authorization返回AccessToken，时间戳为当前时间戳
        String token = JwtUtil.sign(sysAdminUserEntity.getId().toString(),sysAdminUserEntity.getAccount(), adminClient.name(), currentTimeMillis);
        sysAdminUserEntity.setLoginTime(new Date());
        sysAdminUserService.updateById(sysAdminUserEntity);
        httpServletResponse.setHeader("Authorization", token);
        httpServletResponse.setHeader("Access-Control-Expose-Headers", "Authorization");
        return renderSuccess();

    }



    @GetMapping(value = "/perms/list")
    @MethodLog(remarks = "获取用户菜单以及全部权限")
    public RespResultMessage list(@LoginUser LoginUserBean loginUser) {
        Set<Map>  menuEntitySet= userRoleMenuConService.findSysMenuEntityMapSetByUerId(loginUser.getUserId());
        return renderSuccess(menuEntitySet);
    }


    /**
     * 获取在线用户(查询Redis中的RefreshToken)
     * @param
     * @return com.wang.model.common.ResponseBean
     * @author dolyw.com
     * @date 2018/9/6 9:58
     */
    @GetMapping("/online")
    @RequiresPermissions(logical = Logical.AND, value = {"user:view"})
    public RespResultMessage online() {
        List<Object> userDtos = new ArrayList<Object>();
        LoginClientTypeEnum adminClient = LoginClientTypeEnum.ADMIN_CLENT;

        // 查询所有Redis键
        Set<String> keys = JedisUtil.keysS(Constant.getPREFIX_SHIRO_REFRESH_TOKEN(adminClient) + "*");
        for (String key : keys) {
            if (JedisUtil.exists(key)) {
                // 根据:分割key，获取最后一个字符(帐号)
                String[] strArray = key.split(":");
                String account = strArray[strArray.length - 1];
                SysAdminUserEntity userDto = sysAdminUserService.findSysAdminUserEntityByAccount(account);
                // 设置登录时间
                userDto.setLoginTime(new Date(Long.parseLong(JedisUtil.getObject(key).toString())));
                userDtos.add(userDto);
            }
        }
        return  renderSuccess(userDtos);
    }


    /**
     * 剔除在线用户
     * @param id
     * @return
     */
    @DeleteMapping("/online/{id}")
    @RequiresPermissions(logical = Logical.AND, value = {"user:edit"})
    public RespResultMessage deleteOnline(@PathVariable("id") Integer id) {
        SysAdminUserEntity userDto = sysAdminUserService.getById(id);

        LoginClientTypeEnum adminClient = LoginClientTypeEnum.ADMIN_CLENT;

        if (JedisUtil.exists(Constant.getPREFIX_SHIRO_REFRESH_TOKEN(adminClient) + userDto.getAccount())) {
            if (JedisUtil.delKey(Constant.getPREFIX_SHIRO_REFRESH_TOKEN(adminClient)+ userDto.getAccount()) > 0) {
                return renderSuccess();
            }
        }
        throw new ApiClientException("剔除失败，Account不存在(Deletion Failed. Account does not exist.)");
    }

    @GetMapping(value = "/logout")
    @MethodLog(remarks = "退出系统")
    public RespResultMessage logout(@LoginUser LoginUserBean loginUser) {
        LoginClientTypeEnum adminClient = LoginClientTypeEnum.ADMIN_CLENT;
        if (JedisUtil.exists(Constant.getPREFIX_SHIRO_REFRESH_TOKEN(adminClient) + loginUser.getAccount())) {
            if (JedisUtil.delKey(Constant.getPREFIX_SHIRO_REFRESH_TOKEN(adminClient)+ loginUser.getAccount()) > 0) {
            }
        }
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return renderSuccess();
    }
}

