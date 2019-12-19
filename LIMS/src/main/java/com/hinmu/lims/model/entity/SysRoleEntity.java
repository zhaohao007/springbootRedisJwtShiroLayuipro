package com.hinmu.lims.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.hinmu.lims.model.BaseEntity;
import com.hinmu.lims.model.enums.InnerTypeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.HashSet;
import java.util.Set;

/**
 * <p>
 * 角色
 * </p>
 *
 * @author zhaohao
 * @since 2019-08-28
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("tb_sys_role")
public class SysRoleEntity extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 角色名称
     */
    @TableField("role_name")
    private String roleName;

    /**
     * 备注
     */
    @TableField("remark")
    private String remark;

    /**
     * 是否内置账户
     */
    @TableField("inner_type")
    private InnerTypeEnum innerType;

    /**
     * 角色对应的权限集合 非表字段
     */
    @TableField(exist = false)
    private Set<SysMenuEntity> permissions=new HashSet<>();
}
