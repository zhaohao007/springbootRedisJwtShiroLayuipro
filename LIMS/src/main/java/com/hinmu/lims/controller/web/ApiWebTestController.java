package com.hinmu.lims.controller.web;

import com.hinmu.lims.controller.BaseController;
import com.hinmu.lims.model.respbean.RespResultMessage;
import com.hinmu.lims.service.ISysAdminUserService;
import com.hinmu.lims.shiro.annotation.LoginUser;
import com.hinmu.lims.shiro.annotation.LoginUserBean;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiWebTestController extends BaseController {

    @Autowired
    private ISysAdminUserService sysAdminUserService;

    @RequiresRoles(value = {"WEBROLE"},logical = Logical.OR)
    @PostMapping(value = "/api/web/get/{id}")
    public RespResultMessage add(@PathVariable int id, @LoginUser LoginUserBean loginUser) {
        System.out.println("[[[[[[[[[[]]]]]]]]]]"+loginUser.getAccount());
        return  renderSuccess(sysAdminUserService.getById(id));
    }

    @RequiresPermissions(value = {"admin:updateUserInfo"})
    @PostMapping(value = "/api/web/get2/{id}")
    public RespResultMessage add2(@PathVariable int id, @LoginUser LoginUserBean loginUser) {
        System.out.println("[[[[[[[[[[]]]]]]]]]]"+loginUser.getAccount());
        return  renderSuccess(sysAdminUserService.getById(id));
    }

}
