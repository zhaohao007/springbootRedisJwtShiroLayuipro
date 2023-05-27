package com.hinmu.lims.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hinmu.lims.dao.SysMenuMapper;
import com.hinmu.lims.model.entity.SysMenuEntity;
import com.hinmu.lims.service.ISysMenuService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 角色 服务实现类
 * </p>
 *
 * @author zhaohao
 * @since 2019-08-28
 */
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenuEntity> implements ISysMenuService {

    @Override
    public List<SysMenuEntity> findPermissionListByUserId(Integer adminUserId) {
        return null;
    }

    @Override
    public List<SysMenuEntity> findSysMenuEntityListByPerms(String perms) {
        QueryWrapper<SysMenuEntity> ew = new QueryWrapper<>();
        ew.eq("perms",perms);
        return this.list(ew);
    }

    @Override
    public int selectPidByid(int deptPid) {
       return this.baseMapper.selectPidByid(deptPid);
    }
}
