package com.hinmu.lims.model.enums;

import com.baomidou.mybatisplus.core.enums.IEnum;

import java.io.Serializable;

public enum DisableStatusEnum implements IEnum {
    USABLE("USABLE", "可用"),
    DISABLE("DISABLE", "不可用");

    private final String value;
    private final String desc;

    DisableStatusEnum(final String value, final String desc) {
        this.value = value;
        this.desc = desc;
    }

    @Override
    public Serializable getValue() {
        return this.value;
    }

    // Jackson 注解为 JsonValue 返回中文 json 描述
    public String getDesc() {
        return this.desc;
    }
}