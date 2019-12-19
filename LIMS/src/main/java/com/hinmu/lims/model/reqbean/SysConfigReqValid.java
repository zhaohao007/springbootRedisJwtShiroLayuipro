package com.hinmu.lims.model.reqbean;

import lombok.Data;

@Data
public class SysConfigReqValid {

    /**
     * key值
     */
    private String keyName;

    /**
     * value值
     */
    private String keyValue;

    /**
     * 描述信息
     */
    private String desrc;

    /**
     * 排序号
     */
    private Integer ordernum;
}
