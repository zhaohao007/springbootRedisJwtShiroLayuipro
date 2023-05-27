package com.hinmu.lims.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hinmu.lims.dao.SysConfigMapper;
import com.hinmu.lims.model.entity.SysConfigEntity;
import com.hinmu.lims.model.respbean.PageResult;
import com.hinmu.lims.service.ISysConfigService;
import com.hinmu.lims.util.common.StringUtil;
import com.hinmu.lims.util.page.Query;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * <p>
 * 配置表 服务实现类
 * </p>
 *
 * @author zhaohao
 * @since 2019-10-26
 */
@Service
public class SysConfigServiceImpl extends ServiceImpl<SysConfigMapper, SysConfigEntity> implements ISysConfigService {


    @Override
    public PageResult listPage(Map map) {
        QueryWrapper<SysConfigEntity> sysConfigEntityQueryWrapper = new QueryWrapper<>();
        sysConfigEntityQueryWrapper.orderByAsc("ordernum");
        if(map.get("keyValue") !=null && StringUtil.isNotBlank((String)map.get("keyValue"))){
            String keyValue = (String) map.get("keyValue");
            sysConfigEntityQueryWrapper.like("key_value","%"+keyValue+"%");
        }
        IPage<SysConfigEntity> page = this.page(new Query<SysConfigEntity>().getPage(map),
                sysConfigEntityQueryWrapper
        );
        return new PageResult(page) ;
    }

    @Override
    public void updateByKey(SysConfigEntity sysConfigEntities) {
        this.baseMapper.updateByKey(sysConfigEntities);
    }
}
