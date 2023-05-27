package com.hinmu.lims.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hinmu.lims.model.entity.SysRoleEntity;
import com.hinmu.lims.model.respbean.PageResult;

import java.util.Map;

/**
 * <p>
 * 角色 服务类
 * </p>
 *
 * @author zhaohao
 * @since 2019-08-28
 */
public interface ISysRoleService extends IService<SysRoleEntity> {

    PageResult selectSysRoleListPage(Map map);
    SysRoleEntity selectByRoleName(String roleName);
}
