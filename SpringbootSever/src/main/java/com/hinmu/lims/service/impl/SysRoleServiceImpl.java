package com.hinmu.lims.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hinmu.lims.dao.SysRoleMapper;
import com.hinmu.lims.model.entity.SysRoleEntity;
import com.hinmu.lims.model.respbean.PageResult;
import com.hinmu.lims.service.ISysRoleService;
import com.hinmu.lims.util.common.StringUtil;
import com.hinmu.lims.util.page.Query;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * <p>
 * 角色 服务实现类
 * </p>
 *
 * @author zhaohao
 * @since 2019-08-28
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRoleEntity> implements ISysRoleService {

    @Override
    public PageResult selectSysRoleListPage(Map map) {
        QueryWrapper<SysRoleEntity> queryWrapper = new QueryWrapper<>();
        if(map.get("roleName") !=null && StringUtil.isNotBlank((String) map.get("roleName"))){
            String roleName = (String)map.get("roleName");
            queryWrapper.like("role_name","%"+roleName+"%");
        }
        if(map.get("innerType") !=null && StringUtil.isNotBlank((String)map.get("innerType"))){
            String innerType = (String)map.get("innerType");
            queryWrapper.eq("inner_type",innerType);
        }
        IPage<SysRoleEntity> page = this.page(new Query<SysRoleEntity>().getPage(map),queryWrapper);
        return new PageResult(page) ;
    }

    @Override
    public SysRoleEntity selectByRoleName(String roleName) {
        return  this.getOne(new QueryWrapper<SysRoleEntity>().eq("role_name",roleName));
    }
}
