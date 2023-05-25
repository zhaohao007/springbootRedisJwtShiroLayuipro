package com.hinmu.lims.shiro.annotation;

import com.hinmu.lims.model.Constant;
import com.hinmu.lims.model.enums.LoginClientTypeEnum;
import com.hinmu.lims.util.jwt.JwtUtil;
import org.apache.shiro.SecurityUtils;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;

/**
 * 用于方法参数上加Token4User标签获取当前登录用户的信息
 */
@Component
public class LoginUserArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        AnnotatedElement annotatedElement = methodParameter.getAnnotatedElement();
        Annotation[] annotations = annotatedElement.getAnnotations();
        return methodParameter.hasParameterAnnotation(LoginUser.class);
    }

    @Override
    public LoginUserBean resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        try {
            String token = SecurityUtils.getSubject().getPrincipal().toString();
            // 解密获得Account
            String account = JwtUtil.getClaim(token, Constant.ACCOUNT);
            String userid = JwtUtil.getClaim(token, Constant.USERID);
            String loginClientType = JwtUtil.getClaim(token, Constant.LOGINCLIENTTYPE);
            LoginUserBean loginUserBean = new LoginUserBean();
            loginUserBean.setAccount(account);
            loginUserBean.setUserId(Integer.valueOf(userid));
            loginUserBean.setLoginClientType(LoginClientTypeEnum.valueOf(loginClientType));
            return loginUserBean;
        }catch (Exception e){
            return null;
        }

    }
}
