package com.hinmu.lims.shiro.annotation;

import com.hinmu.lims.model.enums.LoginClientTypeEnum;
import lombok.Data;

@Data
public class LoginUserBean {
    /**
     * id
     */
    private Integer userId;

    /**
     * 登录账户
     */
    private String account;


    /**
     *  登陆端类型
     */
    private LoginClientTypeEnum loginClientType;
}
