package com.hinmu.lims.controller.manager;

import com.hinmu.lims.config.aoplog.MethodLog;
import com.hinmu.lims.controller.BaseController;
import com.hinmu.lims.model.reqbean.BatchDelReqValid;
import com.hinmu.lims.model.reqbean.SysRoleReqValid;
import com.hinmu.lims.model.respbean.PageResult;
import com.hinmu.lims.model.respbean.RespResultMessage;
import com.hinmu.lims.service.ISysRoleService;
import com.hinmu.lims.service.UserRoleMenuConService;
import com.hinmu.lims.shiro.annotation.LoginUser;
import com.hinmu.lims.shiro.annotation.LoginUserBean;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 角色 前端控制器
 * </p>
 *
 * @author zhaohao
 * @since 2019-08-28
 */
@RestController
@RequestMapping("/api/admin/role")
public class SysRoleController extends BaseController {
    @Autowired
    private ISysRoleService sysRoleService;
    @Autowired
    private UserRoleMenuConService userRoleMenuConService;

    @RequiresPermissions(value = {"admin:sys:role:list"}, logical = Logical.AND)
    @PostMapping(value = "/list")
    @MethodLog(remarks = "查看角色列表数据")
    public RespResultMessage list(@RequestParam Map map) {
        PageResult pageResult = sysRoleService.selectSysRoleListPage(map);
        return renderPageSuccess(pageResult);
    }


    @RequiresPermissions(value = {"admin:sys:role:get"}, logical = Logical.AND)
    @GetMapping(value = "/get/{roleId}")
    @MethodLog(remarks = "进入角色列表编辑页面")
    public RespResultMessage get(@PathVariable("roleId") Integer roleId) {
        return renderSuccess(sysRoleService.getById(roleId));
    }

    @RequiresPermissions(value = {"admin:sys:role:edite:choose"}, logical = Logical.AND)
    @GetMapping(value = "/edite/choose/{roleId}")
    @MethodLog(remarks = "获取角色拥有全部菜单")
    public RespResultMessage editeChoose(@PathVariable("roleId") Integer roleId) {
        List<Map> result = userRoleMenuConService.selectRoleAllMenu(roleId);
        return this.renderSuccess(result);
    }

    @RequiresPermissions(value = {"admin:sys:role:edite"}, logical = Logical.AND)
    @PostMapping(value = "/edite")
    @MethodLog(remarks = "保存角色编辑信息")
    public RespResultMessage edite(@LoginUser LoginUserBean loginUserBean, @Validated @RequestBody SysRoleReqValid req) {
        //todo 正常应该，查询出来该角色下所有的用户，redis缓存信息清楚掉
        return renderSuccess(userRoleMenuConService.updateRole(req, loginUserBean.getUserId()));
    }


    @RequiresPermissions(value = {"admin:sys:role:add"}, logical = Logical.AND)
    @PostMapping(value = "/add")
    @MethodLog(remarks = "保存新增角色信息")
    public RespResultMessage add(@LoginUser LoginUserBean loginUserBean, @Validated @RequestBody SysRoleReqValid req) {
        return renderSuccess(userRoleMenuConService.saveRole(req, loginUserBean.getUserId()));
    }

    @RequiresPermissions(value = {"admin:sys:role:delete"}, logical = Logical.AND)
    @GetMapping(value = "/delete/{roleId}")
    @MethodLog(remarks = "删除角色")
    public RespResultMessage delete(@PathVariable("roleId") Integer roleId) {
        userRoleMenuConService.deleteRole(roleId);
        return renderSuccess();
    }

    @RequiresPermissions(value = {"admin:sys:role:delete:batch"}, logical = Logical.AND)
    @PostMapping(value = "/delete/batch")
    @MethodLog(remarks = "批量删除角色")
    public RespResultMessage deleteBatch(@Validated @RequestBody BatchDelReqValid req) {
        userRoleMenuConService.deleteBatchRole(req);
        return renderSuccess();
    }
}
