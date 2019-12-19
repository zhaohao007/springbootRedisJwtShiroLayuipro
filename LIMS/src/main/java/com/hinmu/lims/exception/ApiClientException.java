package com.hinmu.lims.exception;

/**
 * 自定义异常
 *
 */
public class ApiClientException extends RuntimeException {

    public ApiClientException(String msg){
        super(msg);
    }

    public ApiClientException() {
        super();
    }
}
