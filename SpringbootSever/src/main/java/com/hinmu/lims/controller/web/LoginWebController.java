package com.hinmu.lims.controller.web;


import com.hinmu.lims.controller.BaseController;
import com.hinmu.lims.exception.ApiClientException;
import com.hinmu.lims.model.Constant;
import com.hinmu.lims.model.entity.SysAdminUserEntity;
import com.hinmu.lims.model.enums.LoginClientTypeEnum;
import com.hinmu.lims.model.reqbean.LoginReqValid;
import com.hinmu.lims.model.respbean.RespResultMessage;
import com.hinmu.lims.service.ISysAdminUserService;
import com.hinmu.lims.util.jwt.JwtUtil;
import com.hinmu.lims.util.redis.JedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;


@RestController
public class LoginWebController extends BaseController {
    /**
     * RefreshToken过期时间
     */
    @Value("${refreshTokenExpireTime}")
    private String refreshTokenExpireTime;
    @Autowired
    private ISysAdminUserService sysAdminUserService;
    @PostMapping("/web/login")
    public RespResultMessage login(@RequestBody LoginReqValid loginReqValid, HttpServletResponse httpServletResponse) {
        //验证输入账号密码是否不正确
        SysAdminUserEntity sysAdminUserEntity = sysAdminUserService.findLoginByAccountPassword(loginReqValid);
        if (sysAdminUserEntity == null) {
            throw new ApiClientException("common.account.password.error");
        }


        LoginClientTypeEnum webClent = LoginClientTypeEnum.WEB_CLENT;

        //生成JWT
        // 清除可能存在的Shiro权限信息缓存
        if (JedisUtil.exists(Constant.getPREFIX_SHIRO_CACHE(webClent) + sysAdminUserEntity.getAccount())) {
            JedisUtil.delKey(Constant.getPREFIX_SHIRO_CACHE(webClent)  + sysAdminUserEntity.getAccount());
        }
        // 设置RefreshToken，时间戳为当前时间戳，直接设置即可(不用先删后设，会覆盖已有的RefreshToken)
        String currentTimeMillis = String.valueOf(System.currentTimeMillis());
        JedisUtil.setObject(Constant.getPREFIX_SHIRO_REFRESH_TOKEN(webClent)  + sysAdminUserEntity.getAccount(), currentTimeMillis, Integer.parseInt(refreshTokenExpireTime));
        // 从Header中Authorization返回AccessToken，时间戳为当前时间戳
        String token = JwtUtil.sign(sysAdminUserEntity.getId().toString(),sysAdminUserEntity.getAccount(), webClent.name(), currentTimeMillis);
        httpServletResponse.setHeader("Authorization", token);
        httpServletResponse.setHeader("Access-Control-Expose-Headers", "Authorization");
        return  renderSuccess();
    }

}

