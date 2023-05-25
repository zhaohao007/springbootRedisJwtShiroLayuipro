package com.hinmu.lims.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hinmu.lims.dao.SysUserRoleMapper;
import com.hinmu.lims.model.entity.SysUserRoleEntity;
import com.hinmu.lims.service.ISysUserRoleService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 用户角色表 服务实现类
 * </p>
 *
 * @author zhaohao
 * @since 2019-08-28
 */
@Service
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRoleEntity> implements ISysUserRoleService {

    @Override
    public List<SysUserRoleEntity> getSysUserRoleList(Integer adminUserId) {
        QueryWrapper<SysUserRoleEntity> ew = new QueryWrapper<>();
        ew.eq("user_id",adminUserId);
        return this.list(ew);
    }

    @Override
    public void removeByRoleId(Integer roleId) {
        QueryWrapper<SysUserRoleEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("role_id",roleId);
        this.remove(queryWrapper);
    }

    @Override
    public void removeByUserId(Integer userId) {
        QueryWrapper<SysUserRoleEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",userId);
        this.remove(queryWrapper);
    }

    /**
     * 判断用户是否为超级管理员
     * @param id
     * @return
     */
    @Override
    public Integer selectType(Integer id) {
        return this.baseMapper.selectType(id);
    }
}
