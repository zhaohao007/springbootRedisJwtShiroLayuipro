package com.hinmu.lims.exception;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by hao on 2019/4/28.
 */
public class ApiValidationException extends RuntimeException {
    private Map<String, String> errors;

    public ApiValidationException() {
        super("common.error.validation");
    }

    public ApiValidationException(String message) {
        super(message);
    }

    public ApiValidationException(String field, String error) {
        this();
        this.addFieldError(field, error);
    }

    public ApiValidationException(Map<String, String> errors) {
        this();
        this.errors = errors;
    }

    public void addFieldError(String field, String error) {
        if(this.errors == null) {
            this.errors = new HashMap();
        }

        this.errors.put(field, error);
    }

    public Map<String, String> getErrors() {
        return this.errors;
    }

    public void setErrors(Map<String, String> errors) {
        this.errors = errors;
    }
}
