package com.hinmu.lims.model.enums;

import com.baomidou.mybatisplus.core.enums.IEnum;

import java.io.Serializable;

public enum MenuTypeEnum implements IEnum {
    TOP_MENU("TOP_MENU", "目录"),
    SECOND_MENU("SECOND_MENU", "菜单"),
    BUTTON("BUTTON", "按钮");

    private final String value;
    private final String desc;

    MenuTypeEnum(final String value, final String desc) {
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