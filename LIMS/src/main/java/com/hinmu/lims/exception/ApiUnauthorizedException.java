package com.hinmu.lims.exception;

public class ApiUnauthorizedException extends RuntimeException {

    public ApiUnauthorizedException(String msg){
        super(msg);
    }

    public ApiUnauthorizedException() {
        super();
    }
}
