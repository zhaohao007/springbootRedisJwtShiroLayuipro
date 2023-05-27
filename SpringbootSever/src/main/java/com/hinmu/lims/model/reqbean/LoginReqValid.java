package com.hinmu.lims.model.reqbean;

import lombok.Data;

import javax.validation.constraints.NotEmpty;


@Data
public class LoginReqValid {

    @NotEmpty(message = "{login.error.account.valid}")
    private String account;
    @NotEmpty(message = "{login.error.password.valid}")
    private String password;
}
