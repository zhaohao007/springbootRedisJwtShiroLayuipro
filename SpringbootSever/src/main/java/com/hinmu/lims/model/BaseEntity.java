package com.hinmu.lims.model;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.util.Objects;

@Data
public class BaseEntity {
    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 创建时间
     */
    @TableField(value = "when_created", fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date whenCreated;

    /**
     * 修改时间
     */
    @TableField(value = "when_modified", fill = FieldFill.UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date whenModified;
    /**
     * 创建人
     */
    @TableField(value = "who_created", fill = FieldFill.INSERT)
    private Integer whoCreated;

    /**
     * 修改人
     */
    @TableField(value = "who_modified", fill = FieldFill.UPDATE)
    private Integer whoModified;



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BaseEntity)) return false;
        BaseEntity that = (BaseEntity) o;
        return getId().intValue()==that.getId().intValue();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

}
