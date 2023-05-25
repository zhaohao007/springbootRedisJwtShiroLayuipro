package com.hinmu.lims.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hinmu.lims.model.entity.SysConfigEntity;

/**
 * <p>
 * 配置表 Mapper 接口
 * </p>
 *
 * @author zhaohao
 * @since 2019-10-26
 */
public interface SysConfigMapper extends BaseMapper<SysConfigEntity> {


    void updateByKey(SysConfigEntity sysConfigEntities);
}
