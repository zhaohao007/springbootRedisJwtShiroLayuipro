package com.hinmu.lims.model.enums;

import com.baomidou.mybatisplus.core.enums.IEnum;


public enum SexEnum implements IEnum {
    MALE("MALE", "男"),
    FEMALE("FEMALE", "女");

    private final String value;
    private final String desc;

    SexEnum(final String value, final String desc) {
        this.value = value;
        this.desc = desc;
    }

    @Override
    public String getValue() {
        return this.value;
    }

    // Jackson 注解为 JsonValue 返回中文 json 描述
    public String getDesc() {
        return this.desc;
    }
}