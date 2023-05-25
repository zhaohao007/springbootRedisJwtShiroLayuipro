package com.hinmu.lims.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hinmu.lims.model.entity.SysConfigEntity;
import com.hinmu.lims.model.respbean.PageResult;

import java.util.Map;

/**
 * <p>
 * 配置表 服务类
 * </p>
 *
 * @author zhaohao
 * @since 2019-10-26
 */
public interface ISysConfigService extends IService<SysConfigEntity> {

    PageResult listPage(Map map);

    void updateByKey(SysConfigEntity sysConfigEntities);
}
