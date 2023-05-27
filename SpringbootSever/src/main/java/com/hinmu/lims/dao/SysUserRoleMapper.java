package com.hinmu.lims.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hinmu.lims.model.entity.SysUserRoleEntity;
import org.apache.ibatis.annotations.Param;

import java.util.Set;

/**
 * <p>
 * 用户角色表 Mapper 接口
 * </p>
 *
 * @author zhaohao
 * @since 2019-08-28
 */
public interface SysUserRoleMapper extends BaseMapper<SysUserRoleEntity> {
    /**
     *  查询用的全部角色
     * @param adminUserId
     * @return
     */
    Set<SysUserRoleEntity> getSysUserRoleList(Integer adminUserId);

    Integer selectType(@Param("id") Integer id);
}
