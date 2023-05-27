package com.hinmu.lims.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.hinmu.lims.model.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 配置表
 * </p>
 *
 * @author zhaohao
 * @since 2019-10-26
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("tb_sys_config")
public class SysConfigEntity extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * key值
     */
    @TableField("key_name")
    private String keyName;

    /**
     * value值
     */
    @TableField("key_value")
    private String keyValue;

    /**
     * 描述信息
     */
    @TableField("desrc")
    private String desrc;

    /**
     * 排序号
     */
    @TableField("ordernum")
    private Integer ordernum;


}
