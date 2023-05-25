package com.hinmu.lims.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hinmu.lims.model.entity.SysLogEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 后台操作日志 Mapper 接口
 * </p>
 *
 * @author zhaohao
 * @since 2019-10-22
 */
public interface SysLogMapper extends BaseMapper<SysLogEntity> {
    List<SysLogEntity> getPage(Page<SysLogEntity> buildFloorPage);
    List<SysLogEntity> getPageTo(@Param("buildFloorPage") Page<SysLogEntity> buildFloorPage, @Param("userName") String userName, @Param("ip") String ip, @Param("map") Map map, @Param("type") String type);
}
