package com.hinmu.lims.exception.handler;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.hinmu.lims.config.message.MessageService;
import com.hinmu.lims.exception.ApiClientException;
import com.hinmu.lims.exception.ApiValidationException;
import com.hinmu.lims.model.respbean.RespResultMessage;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.permission.InvalidPermissionStringException;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpStatusCodeException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.nio.file.AccessDeniedException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 这个类用来统一处理应用程序抛出的异常
 *
 * @ControllerAdvice 无法处理filter中的异常，此处注解输出错误信息
 * 原因： 请求进来 会按照 filter -> interceptor -> controllerAdvice -> aspect  -> controller的顺序调用
 */
@ControllerAdvice
public class ApplicationExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(ApplicationExceptionHandler.class);
    @Autowired
    protected MessageService messageService;
    @Value("${spring.profiles.active}")
    private String active;

    @ResponseBody
    @ExceptionHandler({ApiClientException.class})
    public RespResultMessage handleApiClientException(ApiClientException e) {
        if(active.equals("dev")){//错误信息明细打印出来
            return new RespResultMessage().internal_server_error500(this.processSignMessage(e.getMessage(),null));
        }else if(active.equals("prod")){//生产环境下只需要返回内部错误
            return new RespResultMessage().internal_server_error500(this.processSignMessage("common.error.general",null));
        }
        return null;
    }
    @ResponseBody
    @ExceptionHandler({ApiValidationException.class})
    public RespResultMessage handleApiValidationException(ApiValidationException e) {
        return new RespResultMessage().unprocessable_entity422(this.processApiValidationErrors(e));
    }



    /**
     * 处理其他微服务异常返显
     * @param e
     * @return
     */
    @ResponseBody
    @ExceptionHandler({HttpClientErrorException.class})
    public ResponseEntity handleRestClientResponseException(HttpClientErrorException e) {
        return new ResponseEntity(e.getResponseBodyAsString(), e.getStatusCode());
    }

    @ResponseBody
    @ExceptionHandler(AuthorizationException.class)
    public RespResultMessage handleAuthorizationException(AuthorizationException e){
        return new RespResultMessage().unauthorized401();
    }






    @ResponseBody
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public RespResultMessage handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        HashMap fieldErrors = new HashMap();
        Iterator var3 = e.getBindingResult().getFieldErrors().iterator();

        while(var3.hasNext()) {
            FieldError fieldError = (FieldError)var3.next();
            fieldErrors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        return new RespResultMessage().unprocessable_entity422(this.processApiValidationErrors(new ApiValidationException(fieldErrors)));
    }

    /**
     * 违反唯一约束异常
     * @param e
     * @return
     */
    @ResponseBody
    @ExceptionHandler({ConstraintViolationException.class})
    public RespResultMessage handleApiValidationException(ConstraintViolationException e) {
        HashMap fieldErrors = new HashMap();
        Iterator var3 = e.getConstraintViolations().iterator();

        while(var3.hasNext()) {
            ConstraintViolation violation = (ConstraintViolation)var3.next();
            fieldErrors.put(((PathImpl)violation.getPropertyPath()).getLeafNode().getName(), violation.getMessage());
        }
        return new RespResultMessage().unprocessable_entity422(this.processApiValidationErrors(new ApiValidationException(fieldErrors)));
    }

    /**
     * springboot 的 restTemplate发生异常
     * @param e
     * @return
     */
    @ResponseBody
    @ExceptionHandler({HttpStatusCodeException.class})
    public RespResultMessage handleHttpStatusCodeException(HttpStatusCodeException e) {
        return new RespResultMessage().internal_server_error500("内部异常");

    }


    @ResponseBody
    @ExceptionHandler({InvalidPermissionStringException.class})
    public RespResultMessage handleInvalidPermissionException(InvalidPermissionStringException e) {
        return new RespResultMessage().internal_server_error500("禁止访问");
    }


    @ResponseBody
    @ExceptionHandler({JWTVerificationException.class})
    public RespResultMessage handleJWTVerificationException(JWTVerificationException e) {
        return new RespResultMessage().internal_server_error500("服务器内部异常");
    }

    @ResponseBody
    @ExceptionHandler({AccessDeniedException.class})
    public RespResultMessage handleAccessDeniedException(AccessDeniedException e) {
        return new RespResultMessage().internal_server_error500("禁止访问");
    }

    @ResponseBody
    @ExceptionHandler({HttpMessageNotReadableException.class})
    public RespResultMessage handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        return new RespResultMessage().internal_server_error500("请求路径404");
    }
    @ResponseBody
    @ExceptionHandler({DuplicateKeyException.class})
    public RespResultMessage handleDuplicateKeyException(DuplicateKeyException e) {
        String error = e.getMessage();
        int start = error.indexOf(40);
        int end = error.indexOf(41);
        String field = error.substring(start + 1, end);
        error = error.substring(end + 1);
        start = error.indexOf(40);
        end = error.indexOf(41);
        String value = error.substring(start + 1, end);
        String message = "\'" + value + "\'" + this.messageService.getMessage("common.error.duplicateKey");
        ApiValidationException ave = new ApiValidationException(field, message);
        return new RespResultMessage().unprocessable_entity422(this.processApiValidationErrors(ave));

    }
    @ResponseBody
    @ExceptionHandler({Exception.class})
    public RespResultMessage handleException(Exception e) {
        e.printStackTrace();
        return new RespResultMessage().internal_server_error500("服务器内部异常");
    }









    protected Map<String, Object> processApiValidationErrors(ApiValidationException e) {
        Map map = this.processMessage(e.getMessage(), "Server validation failed!");
        map.put("errors", e.getErrors());
        if(e.getErrors() != null) {
            Iterator var3 = e.getErrors().entrySet().iterator();

            while(var3.hasNext()) {
                Map.Entry entry = (Map.Entry)var3.next();
                if(((String)entry.getValue()).contains(".")) {
                    entry.setValue(this.messageService.getMessage((String)entry.getValue()));
                }
            }
        }

        return map;
    }

    protected Map<String, Object> processMessage(String message) {
        return this.processMessage(message, (String)null);
    }

    protected Map<String, Object> processMessage(String message, String defaultValue) {
        HashMap map = new HashMap();
        map.put("message", message != null && message.contains(".")?this.messageService.getMessageWithDefault(message, defaultValue):message);
        return map;
    }

    protected String processSignMessage(String message, String defaultValue) {
        return message != null && message.contains(".")?this.messageService.getMessageWithDefault(message, defaultValue):message;

    }

}
