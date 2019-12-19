package com.hinmu.lims.config.aoplog;

import com.hinmu.lims.model.Constant;
import com.hinmu.lims.model.entity.SysLogEntity;
import com.hinmu.lims.model.enums.LoginClientTypeEnum;
import com.hinmu.lims.service.ISysLogService;
import com.hinmu.lims.util.common.JsonConvertUtil;
import com.hinmu.lims.util.common.StringUtil;
import com.hinmu.lims.util.jwt.JwtUtil;
import org.apache.shiro.SecurityUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.NamedThreadLocal;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@Aspect
public class SystemLogAspect {
    //这段代码调用了org.slf4j.LoggerFactory line:280
    private static final Logger logger = LoggerFactory.getLogger(SystemLogAspect.class);
    private static final ThreadLocal<Date> beginTimeThreadLocal = new NamedThreadLocal("ThreadLocal beginTime");
    private static final ThreadLocal<SysLogEntity> logThreadLocal = new NamedThreadLocal("ThreadLocal log");
    private static final ThreadLocal<Map> currentUser = new NamedThreadLocal("ThreadLocal user");
    @Autowired
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;
    @Autowired
    private ISysLogService sysLogService;
    /**
     * Controller层切点 注解拦截
     */
    @Pointcut("@annotation(com.hinmu.lims.config.aoplog.MethodLog)")
    public void controllerAspect() {
    }
    /**
     * 用于拦截Controller层记录用户的操作的开始时间
     *
     * @param joinPoint 切点
     * @throws InterruptedException
     */
    @Before("controllerAspect()")
    public void doBefore(JoinPoint joinPoint) throws InterruptedException {
        Date beginTime = new Date();
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes()).getRequest();
        beginTimeThreadLocal.set(beginTime);//线程绑定变量（该数据只有当前请求的线程可见）

        Map loginUserBean = new HashMap();
        try {
            //读取用户
            String token = SecurityUtils.getSubject().getPrincipal().toString();
            if(StringUtil.isNotBlank(token)){
                // 解密获得Account
                String account = JwtUtil.getClaim(token, Constant.ACCOUNT);
                String userid = JwtUtil.getClaim(token, Constant.USERID);
                String loginClientType = JwtUtil.getClaim(token, Constant.LOGINCLIENTTYPE);
                loginUserBean.put("account",account);
                loginUserBean.put("userid",Integer.valueOf(userid));
                loginUserBean.put("loginClientType", LoginClientTypeEnum.valueOf(loginClientType));

            }
        }catch (Exception e){
        }


        currentUser.set(loginUserBean);

    }


//    @Around("controllerAspect()")
//    public void doAround(ProceedingJoinPoint pjp) throws Throwable {
////        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
//        ServletRequestAttributes sra = (ServletRequestAttributes) ra;
//        HttpServletRequest request = sra.getRequest();
//
//        Map loginUserBean = currentUser.get();
//
//
//        String url = request.getRequestURL().toString();
//        String method = request.getMethod();
//        String uri = request.getRequestURI();
//        String queryString = request.getQueryString();
        //重点 这里就是获取@RequestBody参数的关键  调试的情况下 可以看到o变量已经获取到了请求的参数
      //  Object[] o = pjp.getArgs();

        // result的值就是被拦截方法的返回值
       // Object result = pjp.proceed();

//        Object[] o1=null;
//        BeanUtils.copyProperties(o,o1);
//
//        Object result1 =null;
//        BeanUtils.copyProperties(result,result1);
//        loginUserBean.put("requestBody",JSON.toJSONString(o1[0]));
//        loginUserBean.put("result",JSON.toJSONString(result1));
        //这里可以获取到get请求的参数和其他信息
//        logger.info("请求开始, 各个参数, url: {}, method: {}, uri: {}, params: {}, RequestBody参数: {}, result的值: {}",
//                url, method, uri, queryString, JSON.toJSONString(o),JSON.toJSONString(result));

//    }


    /**
     * 用于拦截Controller层记录用户的操作
     *
     * @param joinPoint 切点
     */
    @SuppressWarnings("unchecked")
    @After("controllerAspect()")
    public void doAfter(JoinPoint joinPoint)throws Throwable  {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes()).getRequest();




        Map loginUserBean  = currentUser.get();
        if (loginUserBean != null) {
            SysLogEntity logModel = new SysLogEntity();

            String title = "";
            String type = "info";                       //日志类型(info:入库,error:错误)
            String remoteAddr = SystemLogAspect.getIp();//请求的IP
            String requestUri = request.getRequestURI();//请求的Uri
            try {
                title = getControllerMethodDescription2(joinPoint);
                String method = request.getMethod();
                //重点 这里就是获取@RequestBody参数的关键  调试的情况下 可以看到o变量已经获取到了请求的参数
                Object[] o = joinPoint.getArgs();
                if(o!=null && o.length>0){
                    JsonConvertUtil.objectToJson(o[0]);
                    logModel.setMethod(method);
                    logModel.setArguments(JsonConvertUtil.objectToJson(o[0]));
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            logModel.setTitle(title);
            logModel.setType(type);
            logModel.setIp(remoteAddr);
            logModel.setUrl(requestUri);
            logModel.setException("无异常");
            logModel.setUserName((String)loginUserBean.get("account"));


            //通过线程池来执行日志保存
            threadPoolTaskExecutor.execute(new SaveLogThread(logModel, sysLogService));
            logThreadLocal.set(logModel);
        }
    }



    /**
     *  异常通知 记录操作报错日志
     * @param joinPoint
     * @param e
     */
    @AfterThrowing(pointcut = "controllerAspect()", throwing = "e")
    public  void doAfterThrowing(JoinPoint joinPoint, Throwable e) {
        SysLogEntity sysLogEntity = logThreadLocal.get();
        sysLogEntity.setType("error");
        sysLogEntity.setException(e.toString());
        new UpdateLogThread(sysLogEntity, sysLogService).start();
    }
    /**
     * 获取注解中对方法的描述信息 用于Controller层注解
     * @param joinPoint 切点
     * @return 方法描述
     */

    public static String getControllerMethodDescription2(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        MethodLog controllerLog = method
                .getAnnotation(MethodLog.class);
        String discription = controllerLog.remarks();
        return discription;
    }


    /**
     * 获取请求ip
     */
    public static String getIp()throws Exception {
        InetAddress ia=null;
        ia=ia.getLocalHost();
        String localip=ia.getHostAddress();
        return localip;
    }

    /**
     * 保存日志线程
     *
     * @author lin.r.x
     *
     */
    private static class SaveLogThread implements Runnable {
        private SysLogEntity sysLogEntity;
        private ISysLogService sysLogService;

        public SaveLogThread(SysLogEntity sysLogEntity, ISysLogService sysLogService) {
            this.sysLogEntity = sysLogEntity;
            this.sysLogService = sysLogService;
        }

        @Override
        public void run() {
            sysLogService.save(sysLogEntity);
        }
    }

    /**
     * 日志更新线程
     *
     * @author lin.r.x
     *
     */
    private static class UpdateLogThread extends Thread {
        private SysLogEntity sysLogEntity;
        private ISysLogService sysLogService;
        public UpdateLogThread(SysLogEntity sysLogEntity, ISysLogService sysLogService) {
            super(UpdateLogThread.class.getSimpleName());
            this.sysLogEntity = sysLogEntity;
            this.sysLogService = sysLogService;
        }

        @Override
        public void run() {
            this.sysLogService.updateById(sysLogEntity);
        }
    }
}
