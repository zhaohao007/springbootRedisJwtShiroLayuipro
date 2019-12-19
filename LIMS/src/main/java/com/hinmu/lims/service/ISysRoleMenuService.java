package com.hinmu.lims.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hinmu.lims.model.entity.SysRoleMenuEntity;

import java.util.List;

/**
 * <p>
 * 角色与菜单对应关系 服务类
 * </p>
 *
 * @author zhaohao
 * @since 2019-08-28
 */
public interface ISysRoleMenuService extends IService<SysRoleMenuEntity> {


    /**
     *  查询角色对应的菜单权限
     * @param roleId  角色ID
     * @return
     */
    List<SysRoleMenuEntity> getSysRoleMenuList(Integer roleId);
    List<SysRoleMenuEntity> getSysRoleMenuListByMenuId(Integer menuId);
    void removeByRoleId(Integer roleId);
}
