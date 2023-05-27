package com.hinmu.lims.model.enums;

import com.baomidou.mybatisplus.core.enums.IEnum;

import java.io.Serializable;

public enum  UseStateEnum implements IEnum {
    FREE("FREE", "空闲"),
    BUSY("BUSY", "使用中"),
    LOCK("LOCK", "锁定禁止");

    private final String value;
    private final String desc;

    UseStateEnum(final String value, final String desc) {
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
