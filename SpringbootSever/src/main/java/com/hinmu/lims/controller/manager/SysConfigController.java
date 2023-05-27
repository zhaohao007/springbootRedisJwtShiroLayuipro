package com.hinmu.lims.controller.manager;


import com.hinmu.lims.config.aoplog.MethodLog;
import com.hinmu.lims.config.paramconfig.SystemParamConfig;
import com.hinmu.lims.controller.BaseController;
import com.hinmu.lims.model.reqbean.SysConfigReqValid;
import com.hinmu.lims.model.respbean.RespResultMessage;
import com.hinmu.lims.service.ISysConfigService;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 配置表 前端控制器
 * </p>
 *
 * @author zhaohao
 * @since 2019-10-26
 */
@RestController
@RequestMapping("/api/admin/sys/config")
public class SysConfigController extends BaseController {

    @Autowired
    private SystemParamConfig config;

    @Autowired
    private ISysConfigService sysConfigService;

    @RequiresPermissions(value = {"admin:sys:config:list"}, logical = Logical.AND)
    @PostMapping("/list")
    @MethodLog(remarks = "配置信息页面展示")
    public RespResultMessage list(@RequestParam Map map){
        return renderPageSuccess(sysConfigService.listPage(map));
    }

    @RequiresPermissions(value = {"admin:sys:config:edit"}, logical = Logical.AND)
    @GetMapping(value = "/get/{id}")
    @MethodLog(remarks = "进入编辑配置编辑页面")
    public RespResultMessage get(@PathVariable("id")Integer id){
        return renderSuccess(sysConfigService.getById(id));
    }

    @RequiresPermissions(value = {"admin:sys:config:edit"}, logical = Logical.AND)
    @PostMapping("/edit")
    @MethodLog(remarks = "编辑配置信息")
    public RespResultMessage edit(@Validated @RequestBody SysConfigReqValid req){
        config.update(req);
        return renderSuccess();
    }

    @RequiresPermissions(value = {"admin:sys:config:list"}, logical = Logical.AND)
    @GetMapping("/reload")
    @MethodLog(remarks = "重载配置信息")
    public RespResultMessage reload(){
        config.reload();
        return renderSuccess();
    }

}
