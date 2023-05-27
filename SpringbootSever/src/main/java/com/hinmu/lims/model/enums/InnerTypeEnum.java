package com.hinmu.lims.model.enums;

import com.baomidou.mybatisplus.core.enums.IEnum;

import java.io.Serializable;

public enum InnerTypeEnum implements IEnum {
    INNER("INNER", "内置超级管理员角色"),
    NORMAL("NORMAL", "普通管理员");

    private final String value;
    private final String desc;

    InnerTypeEnum(final String value, final String desc) {
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