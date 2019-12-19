package com.hinmu.lims.controller.app;

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
public class ApiAppTestController extends BaseController {

    @Autowired
    private ISysAdminUserService sysAdminUserService;

    @RequiresPermissions(value = {"admin:updateUserInfo"})
    @PostMapping(value = "/api/app/get/{id}")
    public RespResultMessage add(@PathVariable int id, @LoginUser LoginUserBean loginUser) {
        System.out.println("[[[[[[[[[[]]]]]]]]]]"+loginUser.getAccount());
        return  renderSuccess(sysAdminUserService.getById(id));
    }

}
