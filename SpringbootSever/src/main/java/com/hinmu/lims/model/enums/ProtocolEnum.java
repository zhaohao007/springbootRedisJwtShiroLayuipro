package com.hinmu.lims.model.enums;

import com.baomidou.mybatisplus.core.enums.IEnum;

public enum ProtocolEnum implements IEnum {
    HTTP("http", "http协议"),
    HTTPS("https", "https协议");

    private final String value;
    private final String desc;

    ProtocolEnum(final String value, final String desc) {
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