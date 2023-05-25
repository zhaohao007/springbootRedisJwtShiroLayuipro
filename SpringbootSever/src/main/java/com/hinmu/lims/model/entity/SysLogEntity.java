package com.hinmu.lims.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.hinmu.lims.model.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 后台操作日志
 * </p>
 *
 * @author zhaohao
 * @since 2019-10-22
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("tb_sys_log")
public class SysLogEntity extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 操作用户名
     */
    @TableField("user_name")
    private String userName;

    /**
     * ip地址
     */
    @TableField("ip")
    private String ip;

    /**
     * 请求url
     */
    @TableField("url")
    private String url;
    /**
     * 请求方法名
     */
    @TableField("method")
    private String method;

    /**
     * 入参参数
     */
    @TableField("arguments")
    private String arguments;

    /**
     * 日志类型;菜单;按钮
     */
    @TableField("type")
    private String type;

    /**
     * 日志标题
     */
    @TableField("title")
    private String title;

    /**
     * 日志记录描述
     */
    @TableField("description")
    private String description;

    /**
     * 异常
     */
    @TableField("exception")
    private String exception;


}
