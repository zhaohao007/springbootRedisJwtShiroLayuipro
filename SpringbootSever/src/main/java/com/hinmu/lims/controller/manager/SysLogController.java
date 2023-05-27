package com.hinmu.lims.controller.manager;


import com.hinmu.lims.config.aoplog.MethodLog;
import com.hinmu.lims.controller.BaseController;
import com.hinmu.lims.model.respbean.PageResult;
import com.hinmu.lims.model.respbean.RespResultMessage;
import com.hinmu.lims.service.ISysLogService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * <p>
 * 后台操作日志 前端控制器
 * </p>
 *
 * @author zhaohao
 * @since 2019-10-22
 */
@RestController
@RequestMapping("/sys-log-entity")
public class SysLogController extends BaseController {
    @Autowired
    ISysLogService iSysLogService;

    @RequiresPermissions("admin:syslog:list")
    @RequestMapping("/selectLog")
    @MethodLog(remarks = "日志")
    public RespResultMessage selectrReCode(@RequestParam Map map){
            PageResult pageResult= iSysLogService.list(map);
            return this.renderPageSuccess(pageResult);
    }


    @RequiresPermissions("admin:syslog:list")
    @RequestMapping("/selectLogTo")
    @MethodLog(remarks = "日志")
    public RespResultMessage selectrReCodeTo(@RequestParam Map map ){
        PageResult pageResult= iSysLogService.listTo(map);
            return this.renderPageSuccess(pageResult);

    }


}
