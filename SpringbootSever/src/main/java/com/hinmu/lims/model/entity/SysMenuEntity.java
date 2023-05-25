package com.hinmu.lims.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.hinmu.lims.model.BaseEntity;
import com.hinmu.lims.model.enums.InnerTypeEnum;
import com.hinmu.lims.model.enums.MenuTypeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

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
@TableName("tb_sys_menu")
public class SysMenuEntity extends BaseEntity implements Comparable<SysMenuEntity> {

    private static final long serialVersionUID = 1L;

    /**
     * 父菜单id，一级菜单为0
     */
    @TableField("parent_id")
    private Integer parentId;

    /**
     * 菜单名称
     */
    @TableField("name")
    private String name;

    /**
     * 服务端url
     */
    @TableField("server_url")
    private String serverUrl;
    /**
     * 前端url
     */
    @TableField("front_url")
    private String frontUrl;

    /**
     * 授权(多个用逗号分隔，如：user:list,user:create)
     */
    @TableField("perms")
    private String perms;

    /**
     * 类型目录;菜单;按钮
     */
    @TableField("type")
    private MenuTypeEnum type;

    /**
     * 菜单图标
     */
    @TableField("icon")
    private String icon;

    /**
     * 是否内置账户
     */
    @TableField("inner_type")
    private InnerTypeEnum innerType;
    /**
     * 排序
     */
    @TableField("order_num")
    private Integer orderNum;


    @Override
    public int compareTo(SysMenuEntity o) {
        if ( o !=null && this !=null){
            if(this.orderNum > o.orderNum){
                return 1;
            }else if(this.orderNum < o.orderNum){
                return -1;
            }else {
                return 0;
            }
        }
      return -1;
    }

}
