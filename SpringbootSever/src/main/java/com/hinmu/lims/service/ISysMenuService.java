package com.hinmu.lims.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hinmu.lims.model.entity.SysMenuEntity;

import java.util.List;

/**
 * <p>
 * 角色 服务类
 * </p>
 *
 * @author zhaohao
 * @since 2019-08-28
 */
public interface ISysMenuService extends IService<SysMenuEntity> {

    List<SysMenuEntity>  findPermissionListByUserId(Integer adminUserId);
    List<SysMenuEntity>  findSysMenuEntityListByPerms(String perms);

    int selectPidByid(int deptPid);
}
