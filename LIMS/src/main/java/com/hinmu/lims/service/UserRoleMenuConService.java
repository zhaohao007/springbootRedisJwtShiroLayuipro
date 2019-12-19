package com.hinmu.lims.service;

import com.hinmu.lims.exception.ApiClientException;
import com.hinmu.lims.model.entity.*;
import com.hinmu.lims.model.enums.InnerTypeEnum;
import com.hinmu.lims.model.enums.MenuTypeEnum;
import com.hinmu.lims.model.reqbean.BatchDelReqValid;
import com.hinmu.lims.model.reqbean.LoginReqValid;
import com.hinmu.lims.model.reqbean.SysRoleReqValid;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserRoleMenuConService {
    @Autowired
    private ISysAdminUserService sysAdminUserService;
    @Autowired
    private ISysUserRoleService sysUserRoleService;
    @Autowired
    private ISysRoleService sysRoleService;
    @Autowired
    private ISysRoleMenuService sysRoleMenuService;
    @Autowired
    private ISysMenuService sysMenuService;

    /**
     * 通过用户id获取级联角色菜单信息
     *
     * @param userId
     * @return
     */
    @RequiresPermissions(value = {"admin:updateUserInfo"})
    public SysAdminUserEntity findSysAdminUserRoleMenuListByAccount(Integer userId) {
        //获取用户
        SysAdminUserEntity sysAdminUserEntity = sysAdminUserService.getById(userId);
        if (sysAdminUserEntity == null) return null;
        //获取角色-用户关系
        List<SysUserRoleEntity> sysUserRoleList = sysUserRoleService.getSysUserRoleList(sysAdminUserEntity.getId());
        if (sysUserRoleList == null) return sysAdminUserEntity;
        //组装角色信息
        Set<SysRoleEntity> roles = new HashSet<>();
        for (SysUserRoleEntity userRole : sysUserRoleList) {
            SysRoleEntity sysRoleEntity = sysRoleService.getById(userRole.getRoleId());

            List<SysRoleMenuEntity> sysRoleMenuList = sysRoleMenuService.getSysRoleMenuList(sysRoleEntity.getId());
            if (sysUserRoleList == null) continue;
            //组装权限信息
            Set<SysMenuEntity> permissions = new HashSet<>();
            for (SysRoleMenuEntity roleMenu : sysRoleMenuList) {
                SysMenuEntity sysMenuEntity = sysMenuService.getById(roleMenu.getMenuId());
                permissions.add(sysMenuEntity);
            }
            sysRoleEntity.setPermissions(permissions);

            roles.add(sysRoleEntity);
        }
        sysAdminUserEntity.setRoles(roles);
        return sysAdminUserEntity;
    }

    /**
     * 通过账户获取级联角色菜单信息
     *
     * @param account
     * @return
     */
    public SysAdminUserEntity findSysAdminUserRoleMenuListByAccount(String account, String password) {
        //获取用户
        LoginReqValid loginReq = new LoginReqValid();
        loginReq.setAccount(account);
        loginReq.setPassword(password);
        SysAdminUserEntity sysAdminUserEntity = sysAdminUserService.findLoginByAccountPassword(loginReq);
        if (sysAdminUserEntity == null) return null;
        //获取角色-用户关系
        List<SysUserRoleEntity> sysUserRoleList = sysUserRoleService.getSysUserRoleList(sysAdminUserEntity.getId());
        if (sysUserRoleList == null) return sysAdminUserEntity;
        //组装


        //组装角色信息
        Set<SysRoleEntity> roles = new HashSet<>();
        for (SysUserRoleEntity userRole : sysUserRoleList) {
            SysRoleEntity sysRoleEntity = sysRoleService.getById(userRole.getRoleId());

            List<SysRoleMenuEntity> sysRoleMenuList = sysRoleMenuService.getSysRoleMenuList(sysRoleEntity.getId());
            if (sysUserRoleList == null) continue;
            //组装权限信息
            Set<SysMenuEntity> permissions = new HashSet<>();
            for (SysRoleMenuEntity roleMenu : sysRoleMenuList) {
                SysMenuEntity sysMenuEntity = sysMenuService.getById(roleMenu.getMenuId());
                permissions.add(sysMenuEntity);
            }
            sysRoleEntity.setPermissions(permissions);

            roles.add(sysRoleEntity);
        }
        sysAdminUserEntity.setRoles(roles);
        return sysAdminUserEntity;
    }


    /**
     * 通过账户获取级联角色菜单信息
     *
     * @param userId
     * @return
     */
    public Set<SysMenuEntity> findSysMenuEntityListByUerId(Integer userId) {
        //获取角色-用户关系
        List<SysUserRoleEntity> sysUserRoleList = sysUserRoleService.getSysUserRoleList(userId);
        if (sysUserRoleList == null) return null;
        //组装角色信息
        Set<SysMenuEntity> permissions = new HashSet<>();
        for (SysUserRoleEntity userRole : sysUserRoleList) {
            //角色-菜单-关系
            List<SysRoleMenuEntity> sysRoleMenuList = sysRoleMenuService.getSysRoleMenuList(userRole.getRoleId());
            if (sysUserRoleList == null) continue;
            //组装权限信息
            for (SysRoleMenuEntity roleMenu : sysRoleMenuList) {
                SysMenuEntity sysMenuEntity = sysMenuService.getById(roleMenu.getMenuId());
                permissions.add(sysMenuEntity);
            }
        }
        return permissions;
    }

    /**
     * 通过账户获取级联角色菜单信息
     *
     * @param userId
     * @return
     */
    public Set<Map> findSysMenuEntityMapSetByUerId(Integer userId) {
        //获取角色-用户关系
        List<SysUserRoleEntity> sysUserRoleList = sysUserRoleService.getSysUserRoleList(userId);
        if (sysUserRoleList == null) return null;
        //组装角色信息
        Set<Map> permissions = new HashSet<>();
        for (SysUserRoleEntity userRole : sysUserRoleList) {
            //角色-菜单-关系
            List<SysRoleMenuEntity> sysRoleMenuList = sysRoleMenuService.getSysRoleMenuList(userRole.getRoleId());
            if (sysUserRoleList == null) continue;
            //组装权限信息
            for (SysRoleMenuEntity roleMenu : sysRoleMenuList) {
                SysMenuEntity sysMenuEntity = sysMenuService.getById(roleMenu.getMenuId());
                Map map = new HashMap();
                map.put("id", sysMenuEntity.getId());
                map.put("parentId", sysMenuEntity.getParentId());
                map.put("name", sysMenuEntity.getName());
                map.put("frontUrl", sysMenuEntity.getFrontUrl());
                map.put("perms", sysMenuEntity.getPerms());
                map.put("icon", sysMenuEntity.getIcon());
                permissions.add(map);
            }
        }
        return permissions;
    }

    /**
     * 创建新角色
     *
     * @param req
     * @param sessionAdminUserId
     */
    public boolean saveRole(SysRoleReqValid req, Integer sessionAdminUserId) {
        SysRoleEntity sysRoleEntity = sysRoleService.selectByRoleName(req.getRoleName());
        if (sysRoleEntity != null) throw new ApiClientException("系统已经存在该角色名");
        sysRoleEntity = new SysRoleEntity();
        sysRoleEntity.setRoleName(req.getRoleName());
        sysRoleEntity.setRemark(req.getRemark());
        sysRoleEntity.setInnerType(InnerTypeEnum.NORMAL);
        Date now = new Date();
        sysRoleEntity.setWhenCreated(now);
        sysRoleEntity.setWhoCreated(sessionAdminUserId);
        sysRoleService.save(sysRoleEntity);
        if (req.getPermissions() != null && req.getPermissions().size() > 0) {
            List<SysRoleMenuEntity> sysRoleMenuEntityList = new ArrayList<>();
            for (Integer menuId : req.getPermissions()) {
                SysRoleMenuEntity roleMenuEntity = new SysRoleMenuEntity();
                roleMenuEntity.setMenuId(menuId);
                roleMenuEntity.setRoleId(sysRoleEntity.getId());
                roleMenuEntity.setWhenCreated(now);
                roleMenuEntity.setWhoCreated(sessionAdminUserId);
                sysRoleMenuEntityList.add(roleMenuEntity);
            }
            boolean flag = sysRoleMenuService.saveBatch(sysRoleMenuEntityList);
            if (!flag) throw new ApiClientException("保存失败");
        }
        return true;
    }

    /**
     * 更新角色
     *
     * @param req
     * @param sessionAdminUserId
     */
    public boolean updateRole(SysRoleReqValid req, Integer sessionAdminUserId) {

        SysRoleEntity sysRoleEntity = sysRoleService.getById(req.getId());
        if (sysRoleEntity == null) throw new ApiClientException("role.error.notexist");
        if (sysRoleEntity.getInnerType().equals(InnerTypeEnum.INNER))
            throw new ApiClientException("role.error.inner.notalter");

        if (!sysRoleEntity.getRoleName().equals(req.getRoleName())) {
            SysRoleEntity sysRoleEntity2 = sysRoleService.selectByRoleName(req.getRoleName());
            if (sysRoleEntity2 != null) throw new ApiClientException("role.error.notexist");
        }
        sysRoleEntity.setRoleName(req.getRoleName());
        sysRoleEntity.setRemark(req.getRemark());
        sysRoleEntity.setInnerType(InnerTypeEnum.NORMAL);
        Date now = new Date();
        sysRoleEntity.setWhenModified(now);
        sysRoleEntity.setWhoModified(sessionAdminUserId);
        sysRoleService.updateById(sysRoleEntity);
        if (req.getPermissions() != null && req.getPermissions().size() > 0) {
            //删除之前的角色关系
            sysRoleMenuService.removeByRoleId(sysRoleEntity.getId());
            List<SysRoleMenuEntity> sysRoleMenuEntityList = new ArrayList<>();
            for (Integer menuId : req.getPermissions()) {
                SysRoleMenuEntity roleMenuEntity = new SysRoleMenuEntity();
                roleMenuEntity.setMenuId(menuId);
                roleMenuEntity.setRoleId(sysRoleEntity.getId());
                roleMenuEntity.setWhenCreated(now);
                roleMenuEntity.setWhoCreated(sessionAdminUserId);
                sysRoleMenuEntityList.add(roleMenuEntity);
            }
            boolean flag = sysRoleMenuService.saveBatch(sysRoleMenuEntityList);
            if (!flag) throw new ApiClientException("role.error.saveBatch");

        }
        return true;
    }

    /**
     * 返回全部菜单，勾选某个角色是否选  编辑角色用
     *
     * @param roleId
     * @return
     */
    public List<Map> selectRoleAllMenu(Integer roleId) {
        List<Map> result = new ArrayList<>();
        List<SysMenuEntity> sysMenuEntityList = sysMenuService.list();
        List<SysRoleMenuEntity> sysRoleMenuEntityList = sysRoleMenuService.getSysRoleMenuList(roleId);
        if (sysMenuEntityList != null && sysMenuEntityList.size() > 0) {
            for (SysMenuEntity menuEntity : sysMenuEntityList) {
                Map map = new HashMap();
                map.put("id", menuEntity.getId());
                map.put("parentId", menuEntity.getParentId());
                map.put("name", menuEntity.getName());
                map.put("serverUrl", menuEntity.getServerUrl());
                map.put("frontUrl", menuEntity.getFrontUrl());
                map.put("perms", menuEntity.getPerms());
                map.put("type", menuEntity.getType());
                map.put("innerType", menuEntity.getInnerType());
                if (sysRoleMenuEntityList != null && sysRoleMenuEntityList.size() > 0) {
                    map.put("checked", chooseFlag(menuEntity.getId(), sysRoleMenuEntityList));
                } else {
                    map.put("checked", false);
                }
                result.add(map);
            }
        }
        return result;
    }

    /**
     * 删除角色 同时删除角色菜单关联、管理员角色关联  相关
     *
     * @param roleId
     */
    public void deleteRole(Integer roleId) {
        SysRoleEntity sysRole = sysRoleService.getById(roleId);
        if (sysRole != null && sysRole.getInnerType().equals(InnerTypeEnum.INNER))
            throw new ApiClientException("内置角色不允许删除");
        sysRoleService.removeById(roleId);
        //删除角色菜单关联
        sysRoleMenuService.removeByRoleId(roleId);
        //删除管理员角色关联
        sysUserRoleService.removeByRoleId(roleId);
    }

    public void deleteBatchRole(BatchDelReqValid req) {
        for (Integer roleId : req.getIdList()) {
            deleteRole(roleId);
        }
    }

    private boolean chooseFlag(Integer menuId, List<SysRoleMenuEntity> list) {
        for (SysRoleMenuEntity sysRoleMenuEntity : list) {
            if (sysRoleMenuEntity.getMenuId().intValue() == menuId.intValue())
                return true;
        }
        return false;
    }
    /**
     * 左侧菜单组装
     */
    public List<Map> menuList(Integer sessionAdminUserId) {
        Set<SysMenuEntity> sysMenuEntitySet = findSysMenuEntityListByUerId(sessionAdminUserId);
        List<SysMenuEntity> sysMenuEntityList = new ArrayList<>(sysMenuEntitySet);
        Collections.sort(sysMenuEntityList);
        List<Map> topMenuList = new ArrayList<>();
        for (SysMenuEntity menuEntity : sysMenuEntityList) {
            if (menuEntity.getParentId().intValue() == 0 && menuEntity.getType().equals(MenuTypeEnum.TOP_MENU)) {
                //顶级菜单
                Map map1 = new HashMap();
                map1.put("id", menuEntity.getId());

                map1.put("title", menuEntity.getName());
                map1.put("icon", menuEntity.getIcon());
                map1.put("jump", menuEntity.getFrontUrl());

                map1.put("parentId", menuEntity.getParentId());
                map1.put("serverUrl", menuEntity.getServerUrl());
                map1.put("perms", menuEntity.getPerms());
                map1.put("type", menuEntity.getType());
                map1.put("innerType", menuEntity.getInnerType());

                List<Map> secondMenuList = getChildMenu(sysMenuEntityList, menuEntity.getId(), MenuTypeEnum.SECOND_MENU);
                map1.put("list", secondMenuList);
                topMenuList.add(map1);
            }
        }
        return topMenuList;
    }

    public List<Map> getChildMenu(List<SysMenuEntity> menuEntityList, Integer parentId, MenuTypeEnum menuTypeEnum) {
        List<Map> menuList = new ArrayList<>();
        for (SysMenuEntity menuEntity : menuEntityList) {
            if (menuEntity.getParentId().intValue() == parentId.intValue()
                    && menuEntity.getType().equals(menuTypeEnum)) {
                Map map1 = new HashMap();
                map1.put("id", menuEntity.getId());

                map1.put("title", menuEntity.getName());
                map1.put("icon", menuEntity.getIcon());
                map1.put("jump", menuEntity.getFrontUrl());



                map1.put("parentId", menuEntity.getParentId());
                map1.put("serverUrl", menuEntity.getServerUrl());
                map1.put("perms", menuEntity.getPerms());
                map1.put("type", menuEntity.getType());
                map1.put("innerType", menuEntity.getInnerType());
                menuList.add(map1);
            }
        }
        return menuList;
    }
}
