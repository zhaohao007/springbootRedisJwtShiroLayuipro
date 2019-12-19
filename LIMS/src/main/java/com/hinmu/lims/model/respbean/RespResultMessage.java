package com.hinmu.lims.model.respbean;

import org.springframework.http.HttpStatus;

/**
 * ResponseBean
 * @author dolyw.com
 * @date 2018/8/30 11:39
 */
public class RespResultMessage {
    /**
     * HTTP状态码
     */
    private Integer code;

    /**
     * 返回信息
     */
    private String msg;

    /**
     * 返回的数据
     */
    private Object data;


    public RespResultMessage ok(Object data) {
        this.code = HttpStatus.OK.value();
        this.msg = HttpStatus.OK.getReasonPhrase();
        this.data = data;
        return this;
    }
    public RespResultMessage unauthorized401() {
        this.code = HttpStatus.UNAUTHORIZED.value();
       // this.msg = HttpStatus.UNAUTHORIZED.getReasonPhrase();
        this.msg = "401权限受限，请退出重新登陆";
        return this;
    }

    public RespResultMessage unprocessable_entity422(Object data) {
        this.code = HttpStatus.UNPROCESSABLE_ENTITY.value();
        this.msg = HttpStatus.UNPROCESSABLE_ENTITY.getReasonPhrase();
        this.data = data;
        return this;
    }

    public RespResultMessage internal_server_error500(String msg) {
        this.code = HttpStatus.INTERNAL_SERVER_ERROR.value();
        this.msg = msg;
        return this;
    }



    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
