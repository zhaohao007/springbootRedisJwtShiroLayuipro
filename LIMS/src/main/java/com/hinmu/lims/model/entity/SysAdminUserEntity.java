package com.hinmu.lims.model.entity;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.hinmu.lims.model.BaseEntity;
import com.hinmu.lims.model.enums.DisableStatusEnum;
import com.hinmu.lims.model.enums.InnerTypeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * <p>
 * 后台用户表
 * </p>
 *
 * @author zhaohao
 * @since 2019-08-28
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("tb_sys_admin_user")
public class SysAdminUserEntity extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 合作商id
     */
    @TableField(value = "company_id",updateStrategy =FieldStrategy.IGNORED)
    private Integer companyId;

    /**
     * 案场id
     */
    @TableField(value = "sales_case_id",updateStrategy =FieldStrategy.IGNORED)
    private Integer salesCaseId;

    /**
     * 登录账户
     */
    @TableField("account")
    private String account;

    /**
     * 密码
     */
    @TableField("password")
    private String password;

    /**
     * 盐值
     */
    @TableField("salt")
    private String salt;

    /**
     * 是否内置账户
     */
    @TableField("inner_type")
    private InnerTypeEnum innerType;




    /**
     * 姓名
     */
    @TableField("name")
    private String name;

    /**
     * 可用 不可用
     */
    @TableField("status")
    private DisableStatusEnum status;

    /**
     * 描述
     */
    @TableField("description")
    private String description;

    /**
     * 登录时间
     */
    @TableField("login_time")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date loginTime;

    /**
     * 头像
     */
    @TableField(value = "portrait",updateStrategy =FieldStrategy.IGNORED)
    private String portrait;

    /**
     * 用户对应的角色集合 非表字段
     */
    @TableField(exist = false)
    private Set<SysRoleEntity> roles=new HashSet<>();


}
