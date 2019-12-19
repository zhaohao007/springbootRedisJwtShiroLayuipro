package com.hinmu.lims.model.reqbean;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
public class SysRoleReqValid {
    private Integer id;

    /**
     * 角色名称
     */
    @NotEmpty(message = "角色名字不能为空")
    private String roleName;

    /**
     * 备注
     */
    private String remark;

    /**
     * 角色对应的权限集合 非表字段
     */
    private List<Integer> permissions;
}
