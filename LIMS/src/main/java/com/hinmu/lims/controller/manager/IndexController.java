package com.hinmu.lims.controller.manager;


import com.hinmu.lims.config.aoplog.MethodLog;
import com.hinmu.lims.controller.BaseController;
import com.hinmu.lims.exception.ApiClientException;
import com.hinmu.lims.model.entity.SysAdminUserEntity;
import com.hinmu.lims.model.reqbean.UpdatePwdReqValid;
import com.hinmu.lims.model.respbean.RespResultMessage;
import com.hinmu.lims.service.ISysAdminUserService;
import com.hinmu.lims.service.UserRoleMenuConService;
import com.hinmu.lims.shiro.annotation.LoginUser;
import com.hinmu.lims.shiro.annotation.LoginUserBean;
import com.hinmu.lims.util.common.GeneratePass;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 首页控制器类
 */
@RequestMapping("/api/admin/index")
@RestController
public class IndexController extends BaseController {

    @Autowired
    private UserRoleMenuConService userRoleMenuConService;

    @Autowired
    private ISysAdminUserService sysAdminUserService;

    @GetMapping(value = "/menu/list")
    @MethodLog(remarks = "首页")
    public RespResultMessage list(@LoginUser LoginUserBean loginUser) {
        List<Map> menuList = userRoleMenuConService.menuList(loginUser.getUserId());
        return renderSuccess(menuList);
    }


    /**
     * 进入首页用户修改资料页面
     *
     * @return
     */
    @RequiresPermissions(value = {"admin:toUpdateUserInfo"}, logical = Logical.AND)
    @GetMapping("/user/get")
    @MethodLog(remarks = "用户修改资料")
    public RespResultMessage getUserInfo(@LoginUser LoginUserBean loginUser) {
        return renderSuccess(sysAdminUserService.getById(loginUser.getUserId()));
    }


    /**
     * 修改用户资料
     *
     * @param sysAdminUserEntity
     * @return
     */
    @RequiresPermissions(value = {"admin:updateUserInfo"}, logical = Logical.AND)
    @PostMapping("/user/updateUserInfo")
    @MethodLog(remarks = "修改用户资料")
    public RespResultMessage updateUserInfo(@LoginUser LoginUserBean loginUser, @RequestBody SysAdminUserEntity sysAdminUserEntity) {
        if (sysAdminUserEntity != null) {
            sysAdminUserEntity.setId(loginUser.getUserId());
            sysAdminUserService.updateById(sysAdminUserEntity);
        }
        return renderSuccess(true);
    }

    /**
     * 修改用户密码
     *
     * @param reqValid
     * @return
     */
    @RequiresPermissions(value = {"admin:updatePassword"}, logical = Logical.AND)
    @PostMapping("/user/updatePassword")
    @MethodLog(remarks = "修改用户密码")
    public RespResultMessage updatePassword(@LoginUser LoginUserBean loginUser, @RequestBody UpdatePwdReqValid reqValid) {
        SysAdminUserEntity user = sysAdminUserService.getById(loginUser.getUserId());
        if (user == null) throw new ApiClientException("user.error.notfound");
        String salt = user.getSalt();
        String oldPwd = GeneratePass.SHA512(reqValid.getOldPassword() + salt);
        if (!oldPwd.equals(user.getPassword())) throw new ApiClientException("user.error.oldpassword");
        String newPwd = GeneratePass.SHA512(reqValid.getPassword() + salt);
        SysAdminUserEntity newUser = new SysAdminUserEntity();
        newUser.setId(loginUser.getUserId());
        newUser.setPassword(newPwd);
        sysAdminUserService.updateById(newUser);
        return renderSuccess();
    }

}