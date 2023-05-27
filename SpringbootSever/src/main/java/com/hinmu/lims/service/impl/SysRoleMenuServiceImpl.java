package com.hinmu.lims.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hinmu.lims.dao.SysRoleMenuMapper;
import com.hinmu.lims.model.entity.SysRoleMenuEntity;
import com.hinmu.lims.service.ISysRoleMenuService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 角色与菜单对应关系 服务实现类
 * </p>
 *
 * @author zhaohao
 * @since 2019-08-28
 */
@Service
public class SysRoleMenuServiceImpl extends ServiceImpl<SysRoleMenuMapper, SysRoleMenuEntity> implements ISysRoleMenuService {

    @Override
    public List<SysRoleMenuEntity> getSysRoleMenuList(Integer roleId) {
        QueryWrapper<SysRoleMenuEntity> ew = new QueryWrapper<>();
        ew.eq("role_id",roleId);
        return this.list(ew);
    }

    @Override
    public List<SysRoleMenuEntity> getSysRoleMenuListByMenuId(Integer menuId) {
        QueryWrapper<SysRoleMenuEntity> ew = new QueryWrapper<>();
        ew.eq("menu_id",menuId);
        return this.list(ew);
    }

    @Override
    public void removeByRoleId(Integer roleId) {
        QueryWrapper<SysRoleMenuEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("role_id",roleId);
        this.remove(queryWrapper);
    }
}
