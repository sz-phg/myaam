package com.xxxx.server.aspect;

import com.xxxx.server.mapper.LogMapper;
import com.xxxx.server.pojo.Admin;
import com.xxxx.server.pojo.LogBean;
import io.swagger.annotations.ApiOperation;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.time.LocalDate;

@Aspect
@Component
public class LogAspect {
    LogBean logBean;

    @Autowired
    private LogMapper logMapper;


    @Around("execution(* com.xxxx.server.controller..*.*(..))")
    public Object Log(ProceedingJoinPoint proceedingJoinPoint){
        long startTime = System.nanoTime();
        // 1. 获取目标方法对象
        MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
        // 这个方法默认获取的是接口的方法
        Method method = signature.getMethod();
        // 获取实现类的方法 真正的目标方法
        Object target = proceedingJoinPoint.getTarget();
        // 获取目标对象中的方法
        Method instanceMethod = null;
        try {
            instanceMethod = target.getClass().getMethod(method.getName(), method.getParameterTypes());
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
        // 2. 判断目标方法是否有注解
        if (instanceMethod.isAnnotationPresent(ApiOperation.class)){
            // 3. 如果有注解
            //  3.1  获取注解的值
            ApiOperation annotation = instanceMethod.getAnnotation(ApiOperation.class);
            String content = annotation.value();

            RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
            HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
            String ip = request.getRemoteHost();

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userName = "无";
            if(authentication!=null){
                Admin admin = (Admin) authentication.getPrincipal();
                userName= admin.getUsername();
            }
            long endTime = System.nanoTime();
            logBean = new LogBean(0,userName,content,ip,method.toString(), LocalDate.now(), (int) (endTime-startTime),1);
        }
        try {
            return proceedingJoinPoint.proceed();
        } catch (Throwable e){
            throw new RuntimeException(e);
        }

    }
    @AfterThrowing(value = "execution(* com.xxxx.server.controller..*.*(..))",throwing = "throwable")
    private void afterThrowing(Throwable throwable){
        logBean.setStatus(0);
    }
    @After("execution(* com.xxxx.server.controller..*.*(..))")
    private void after(){
        if(logBean!=null){
            logMapper.insert(logBean);
        }
    }

}
