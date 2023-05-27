package com.hinmu.lims.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hinmu.lims.dao.SysLogMapper;
import com.hinmu.lims.model.entity.SysLogEntity;
import com.hinmu.lims.model.respbean.PageResult;
import com.hinmu.lims.service.ISysLogService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 后台操作日志 服务实现类
 * </p>
 *
 * @author zhaohao
 * @since 2019-10-22
 */
@Service
public class SysLogServiceImpl extends ServiceImpl<SysLogMapper, SysLogEntity> implements ISysLogService {

    @Override
    public PageResult list(Map map) {
        if (!map.containsKey("page") || !map.containsKey("limit")) {
            return null;
        }
        try {
            int page = Integer.parseInt(String.valueOf(map.get("page")));
            int limit = Integer.parseInt(String.valueOf(map.get("limit")));
            Page<SysLogEntity> buildFloorPage = new Page<>(page, limit);
            List<SysLogEntity> list = this.baseMapper.getPage(buildFloorPage);
            return new PageResult(buildFloorPage.setRecords(list));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    @Override
    public PageResult listTo(Map map) {
        if (!map.containsKey("page") || !map.containsKey("limit")) {
            return null;
        }
        Map tamap =new HashMap();
        try {
            int page = Integer.parseInt(String.valueOf(map.get("page")));
            int limit = Integer.parseInt(String.valueOf(map.get("limit")));
            String userName= (String) map.get("userName");
            String ip= (String) map.get("ip");
            String date= (String) map.get("whenCreated");
            if (date!=null && !"".equals(date)) {
                String[] timeArr = date.split("-");
                tamap.put("entDate", (timeArr[0] + "-" + timeArr[1] + "-" + timeArr[2] + " 00:00:00").trim());
                tamap.put("outDate", (timeArr[3] + "-" + timeArr[4] + "-" + timeArr[5] + " 23:59:59").trim());
            }
            String type= (String) map.get("type");
            Page<SysLogEntity> buildFloorPage = new Page<>(page, limit);
            List<SysLogEntity> list = this.baseMapper.getPageTo(buildFloorPage,userName,ip,tamap,type);
            return new PageResult(buildFloorPage.setRecords(list));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
