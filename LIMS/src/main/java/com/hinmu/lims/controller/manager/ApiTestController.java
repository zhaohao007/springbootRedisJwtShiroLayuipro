package com.hinmu.lims.controller.manager;

import com.hinmu.lims.config.aoplog.MethodLog;
import com.hinmu.lims.controller.BaseController;
import com.hinmu.lims.model.respbean.RespResultMessage;
import com.hinmu.lims.service.ISysAdminUserService;
import com.hinmu.lims.shiro.annotation.LoginUser;
import com.hinmu.lims.shiro.annotation.LoginUserBean;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiTestController extends BaseController {

    @Autowired
    private ISysAdminUserService sysAdminUserService;

    @RequiresPermissions(value = {"admin:updateUserInfo"})
    @PostMapping(value = "/api/admin/get/{id}")
    @MethodLog(remarks = "获取用户信息")
    public RespResultMessage add(@PathVariable int id, @LoginUser LoginUserBean loginUser) {
        System.out.println("[[[[[[[[[[]]]]]]]]]]"+loginUser.getAccount());
        return  renderSuccess(sysAdminUserService.getById(id));
    }

    @RequiresPermissions(value = {"admin:updateUserInfo2"})
    @PostMapping(value = "/api/admin/get2/{id}")
    public RespResultMessage add2(@PathVariable int id) {
        return  renderSuccess(sysAdminUserService.getById(id));
    }
}
