package com.hinmu.lims.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hinmu.lims.model.entity.SysUserRoleEntity;

import java.util.List;

/**
 * <p>
 * 用户角色表 服务类
 * </p>
 *
 * @author zhaohao
 * @since 2019-08-28
 */
public interface ISysUserRoleService extends IService<SysUserRoleEntity> {
    /**
     *  查询用的全部角色关系
     * @param adminUserId
     * @return
     */
    List<SysUserRoleEntity> getSysUserRoleList(Integer adminUserId);
    void removeByRoleId(Integer roleId);
    void removeByUserId(Integer userId);

    /**
     * 判断用户是否为超级管理员 返回1 超级管理员
     * @param id
     * @return
     */
    Integer selectType(Integer id);
}
