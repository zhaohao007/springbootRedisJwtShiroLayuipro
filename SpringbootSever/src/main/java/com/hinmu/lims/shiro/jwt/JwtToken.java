package com.hinmu.lims.shiro.jwt;

import com.hinmu.lims.model.Constant;
import com.hinmu.lims.util.jwt.JwtUtil;
import org.apache.shiro.authc.AuthenticationToken;

/**
 * JwtToken
 * @author dolyw.com
 * @date 2018/8/30 14:06
 */
public class JwtToken implements AuthenticationToken {

    /**
     *  登陆端类型
     */
    private String loginClientType;

    /**
     * Token
     */
    private String token;

    public JwtToken(String token) {
        this.token = token;
    }

    public String getLoginClientType() {
        return JwtUtil.getClaim(this.token, Constant.LOGINCLIENTTYPE);
    }


    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}
