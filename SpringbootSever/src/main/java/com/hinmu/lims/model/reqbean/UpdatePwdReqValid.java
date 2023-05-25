package com.hinmu.lims.model.reqbean;

import lombok.Data;

import javax.validation.constraints.NotEmpty;


@Data
public class UpdatePwdReqValid {
    /**
     * 旧密码
     */
    @NotEmpty(message = "{user.error.oldPassword.valid}")
    private String oldPassword;
    /**
     * 新密码
     */
    @NotEmpty(message = "{user.error.password.valid}")
    private String password;
}
