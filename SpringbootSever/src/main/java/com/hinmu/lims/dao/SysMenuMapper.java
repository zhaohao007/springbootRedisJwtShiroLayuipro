package com.hinmu.lims.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hinmu.lims.model.entity.SysMenuEntity;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 角色 Mapper 接口
 * </p>
 *
 * @author zhaohao
 * @since 2019-08-28
 */
public interface SysMenuMapper extends BaseMapper<SysMenuEntity> {

    int selectPidByid(@Param("deptPid") int deptPid);
}
