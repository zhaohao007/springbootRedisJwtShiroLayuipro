package com.hinmu.lims.model.reqbean;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class GrantSysAdminUserReqValid {
    @NotNull
    private Integer id;
    /**
     * 角色集合
     */
    private List<String> roleListId;

}
