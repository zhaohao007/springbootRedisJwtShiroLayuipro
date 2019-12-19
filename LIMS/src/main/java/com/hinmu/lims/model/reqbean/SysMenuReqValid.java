package com.hinmu.lims.model.reqbean;

import com.hinmu.lims.model.enums.MenuTypeEnum;
import com.hinmu.lims.util.validator.EnumValid;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class SysMenuReqValid {


    private Integer id;

    /**
     * 父菜单id，一级菜单为0
     */
    @NotNull
    private Integer parentId;

    /**
     * 菜单名称
     */
    @NotEmpty(message = "{valid.menu.name}")
    private String name;

    /**
     * 服务端url
     */
    private String serverUrl;
    /**
     * 前端url
     */
    private String frontUrl;

    /**
     * 授权(多个用逗号分隔，如：user:list,user:create)
     */
    private String perms;

    /**
     * 类型目录;菜单;按钮
     */
    @EnumValid(target = MenuTypeEnum.class, message = "{valid.menu.type}")
    private String type;

    /**
     * 菜单图标
     */
    @NotEmpty(message = "{valid.menu.icon}")
    private String icon;

    /**
     * 排序
     */
    private Integer orderNum;
}
