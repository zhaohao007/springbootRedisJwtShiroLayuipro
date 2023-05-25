package com.hinmu.lims.controller.manager;

import com.hinmu.lims.config.aoplog.MethodLog;
import com.hinmu.lims.controller.BaseController;
import com.hinmu.lims.model.enums.DisableStatusEnum;
import com.hinmu.lims.model.enums.InnerTypeEnum;
import com.hinmu.lims.model.enums.MenuTypeEnum;
import com.hinmu.lims.model.respbean.RespResultMessage;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *  常用的数据
 */
@RequestMapping("/api/admin/public")
@RestController
public class PublicDataController  extends BaseController {
    @RequestMapping(value = "/enumdata")
    @MethodLog(remarks = "进入账户列表页面")
    public RespResultMessage getEnumData() {
        List<Map>  innerTypeList = new ArrayList<>();
        List<Map>  menuTypeList = new ArrayList<>();
        List<Map> stateList = new ArrayList<>();
        for (DisableStatusEnum type : DisableStatusEnum.values()) {
            Map map = new HashMap();
            map.put("key", type);
            map.put("desc", type.getDesc());
            stateList.add(map);
        }

        for(InnerTypeEnum type : InnerTypeEnum.values()){
            Map map = new HashMap();
            map.put("key",type);
            map.put("desc",type.getDesc());
            innerTypeList.add(map);
        }
        for(MenuTypeEnum type : MenuTypeEnum.values()){
            Map map = new HashMap();
            map.put("key",type);
            map.put("desc",type.getDesc());
            menuTypeList.add(map);
        }
        Map map = new HashMap();
        map.put("innerTypeList", innerTypeList);
        map.put("menuTypeList", menuTypeList);
        map.put("disableStatusList", stateList);
        return renderSuccess(map);
    }
}
