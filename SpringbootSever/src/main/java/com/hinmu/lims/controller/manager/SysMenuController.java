package com.hinmu.lims.controller.manager;

import com.hinmu.lims.config.aoplog.MethodLog;
import com.hinmu.lims.controller.BaseController;
import com.hinmu.lims.exception.ApiClientException;
import com.hinmu.lims.model.entity.SysMenuEntity;
import com.hinmu.lims.model.entity.SysRoleMenuEntity;
import com.hinmu.lims.model.enums.InnerTypeEnum;
import com.hinmu.lims.model.enums.MenuTypeEnum;
import com.hinmu.lims.model.reqbean.SysMenuReqValid;
import com.hinmu.lims.model.respbean.RespResultMessage;
import com.hinmu.lims.service.ISysMenuService;
import com.hinmu.lims.service.ISysRoleMenuService;
import com.hinmu.lims.shiro.annotation.LoginUser;
import com.hinmu.lims.shiro.annotation.LoginUserBean;
import com.hinmu.lims.util.bean.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Date;
import java.util.List;


/**
 * <p>
 * 角色 前端控制器
 * </p>
 *
 * @author zhaohao
 * @since 2019-08-28
 */
@RestController
@RequestMapping("/api/admin/menu")
public class SysMenuController extends BaseController {
    @Autowired
    private ISysMenuService sysMenuService;
    @Autowired
    private ISysRoleMenuService sysRoleMenuService;


    /**
     * 菜单查询
     *
     * @return
     */
    @RequiresPermissions(value = {"admin:sys:menu:list"}, logical = Logical.AND)
    @GetMapping(value = "/list")
    @MethodLog(remarks = "查询菜单列表")
    public RespResultMessage list() {
        List<SysMenuEntity> sysMenuEntityList = sysMenuService.list();
        Collections.sort(sysMenuEntityList);
        return renderSuccess(sysMenuEntityList);
    }

    @RequiresPermissions(value = {"admin:sys:menu:add"}, logical = Logical.AND)
    @PostMapping(value = "/add")
    @MethodLog(remarks = "新增菜单")
    public RespResultMessage add(@LoginUser LoginUserBean loginUserBean, @Validated @RequestBody SysMenuReqValid req) {
        SysMenuEntity sysMenuEntity = new SysMenuEntity();
        BeanUtils.copyPropertiesIgnoreNull(req, sysMenuEntity);
        if (StringUtils.isNotEmpty(req.getPerms())) {
            List<SysMenuEntity> sysMenuEntityList = sysMenuService.findSysMenuEntityListByPerms(req.getPerms());
            if (sysMenuEntityList != null && sysMenuEntityList.size() > 0)
                throw new ApiClientException("已存在相同的权限");
        }
        if (MenuTypeEnum.SECOND_MENU.getValue().equals(req.getType()) || MenuTypeEnum.TOP_MENU.getValue().equals(req.getType())) {
            //递归查询菜单级别
            //根据父节点判断当前部门的层级
            Integer pid = sysMenuEntity.getParentId();
            if (pid != 0) {
                int level = Recursive(pid, 1);
                if (level >= 2) throw new ApiClientException("菜单最多只能有2级");
            }
        }
        sysMenuEntity.setInnerType(InnerTypeEnum.NORMAL);
        sysMenuEntity.setWhoCreated(loginUserBean.getUserId());
        sysMenuEntity.setType(MenuTypeEnum.valueOf(req.getType()));
        sysMenuService.save(sysMenuEntity);
        return renderSuccess(true);
    }

    /**
     * 递归
     */
    private int Recursive(int deptPid, int count) {
        int pid = sysMenuService.selectPidByid(deptPid);
        if (pid == 0) {
            return count;
        }
        count++;
        return Recursive(pid, count);
    }

    /**
     * 菜单删除
     *
     * @return
     */
    @RequiresPermissions(value = {"admin:sys:menu:delete"}, logical = Logical.AND)
    @GetMapping(value = "/delete/{menuId}")
    @MethodLog(remarks = "删除菜单")
    public RespResultMessage delete(@PathVariable Integer menuId) {
        SysMenuEntity sysMenu = sysMenuService.getById(menuId);
        if (sysMenu == null) throw new ApiClientException("不存在的数据");
        if (sysMenu.getInnerType().equals(InnerTypeEnum.INNER))
            throw new ApiClientException("内置菜单无法删除");
        List<SysRoleMenuEntity> sysRoleMenuEntityList = sysRoleMenuService.getSysRoleMenuListByMenuId(menuId);
        if (sysRoleMenuEntityList != null && sysRoleMenuEntityList.size() > 0)
            throw new ApiClientException("该菜单已经关联角色，无法删除");
        sysMenuService.removeById(menuId);
        return renderSuccess(true);
    }

    @RequiresPermissions(value = {"admin:sys:menu:get"}, logical = Logical.AND)
    @GetMapping(value = "/get/{menuId}")
    @MethodLog(remarks = "获取菜单信息")
    public RespResultMessage get(@PathVariable("menuId") Integer menuId) {
        SysMenuEntity sysMenu = sysMenuService.getById(menuId);
        return renderSuccess(sysMenu);
    }

    /**
     * 菜单更新
     *
     * @return
     */
    @RequiresPermissions(value = {"admin:sys:menu:edite"}, logical = Logical.AND)
    @PostMapping(value = "/edite")
    @MethodLog(remarks = "保存菜单编辑信息")
    public RespResultMessage edite(@LoginUser LoginUserBean loginUserBean, @Validated @RequestBody SysMenuReqValid req) {
        if (req.getId() == null) throw new ApiClientException("参数异常");
        SysMenuEntity sysMenuEntity = sysMenuService.getById(req.getId());
        if (sysMenuEntity == null) throw new ApiClientException("不存在");
        if (sysMenuEntity.getInnerType().equals(InnerTypeEnum.INNER))
            throw new ApiClientException("内置菜单无法更新");
        if (StringUtils.isNotEmpty(req.getPerms()) && !req.getPerms().equals(sysMenuEntity.getPerms())) {
            List<SysMenuEntity> sysMenuEntityList = sysMenuService.findSysMenuEntityListByPerms(req.getPerms());
            if (sysMenuEntityList != null && sysMenuEntityList.size() > 0)
                throw new ApiClientException("已存在相同的权限");
        }
        sysMenuEntity.setInnerType(InnerTypeEnum.NORMAL);
        sysMenuEntity.setWhoModified(loginUserBean.getUserId());
        sysMenuEntity.setWhenModified(new Date());
        sysMenuEntity.setType(MenuTypeEnum.valueOf(req.getType()));
        sysMenuEntity.setPerms(req.getPerms());
        sysMenuEntity.setServerUrl(req.getServerUrl());
        sysMenuEntity.setFrontUrl(req.getFrontUrl());
        sysMenuEntity.setIcon(req.getIcon());
        sysMenuEntity.setName(req.getName());
        sysMenuEntity.setOrderNum(req.getOrderNum());
        sysMenuService.updateById(sysMenuEntity);
        return this.renderSuccess(true);
    }

}
