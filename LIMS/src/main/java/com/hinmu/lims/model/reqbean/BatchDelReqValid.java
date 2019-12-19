package com.hinmu.lims.model.reqbean;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class BatchDelReqValid {
    @NotNull(message = "{user.error.list.empty}")
    private List<Integer> idList;
}
