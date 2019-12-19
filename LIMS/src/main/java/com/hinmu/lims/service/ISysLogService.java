package com.hinmu.lims.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hinmu.lims.model.entity.SysLogEntity;
import com.hinmu.lims.model.respbean.PageResult;

import java.util.Map;

/**
 * <p>
 * 后台操作日志 服务类
 * </p>
 *
 * @author zhaohao
 * @since 2019-10-22
 */
public interface ISysLogService extends IService<SysLogEntity> {
    PageResult list(Map map);
    PageResult listTo(Map map);
}
