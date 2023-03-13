package com.zhu.blog.aspect;

import com.alibaba.fastjson.JSON;
import com.zhu.blog.annotation.SystemLog;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * @author GH
 * @Description: aop实现日志记录
 * @date 2023/3/6 18:35
 */
@Aspect
@Slf4j
public class LogAspect {

    /**
     * 日志切入点
     */
    @Pointcut("@annotation(com.zhu.blog.annotation.SystemLog)")
    public void pointCut(){}

    @Around("pointCut()")
    public Object printLog(ProceedingJoinPoint joinPoint) throws Throwable {

        Object proceed;
        try {
            handlerBefore(joinPoint);
            proceed = joinPoint.proceed();
            handlerAfter(proceed);
        } finally {
            log.info("=======End=======" + System.lineSeparator());
        }
        return proceed;
    }

    private void handlerAfter(Object proceed) {
        // 打印出参
        log.info("Response       : {}", JSON.toJSONString(proceed));
    }

    private void handlerBefore(ProceedingJoinPoint joinPoint) {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        HttpServletRequest request = requestAttributes.getRequest();
        MethodSignature signature = getSignature(joinPoint);
        Method method = getMethod(signature);
        SystemLog systemLog = getSystemLog(signature);

        log.info("=======Start=======");
        // 打印请求 URL
        log.info("URL            : {}", request.getRequestURL());
        // 打印描述信息
        log.info("BusinessName   : {}", systemLog.businessName());
        // 打印 Http method
        log.info("HTTP Method    : {}", request.getMethod());
        // 打印调用 controller 的全路径以及执行方法
        log.info("Class Method   : {}.{}", signature.getDeclaringTypeName(), method.getName());
        // 打印请求的 IP
        log.info("IP             : {}", getIp(request));
        // 打印请求入参
        log.info("Request Args   : {}", JSON.toJSONString(joinPoint.getArgs()));
    }

    private MethodSignature getSignature(ProceedingJoinPoint joinPoint) {
        return (MethodSignature) joinPoint.getSignature();
    }

    private Method getMethod(MethodSignature signature) {
        return signature.getMethod();
    }

    private SystemLog getSystemLog(MethodSignature signature) {
        return getMethod(signature).getAnnotation(SystemLog.class);
    }

    private String getIp(HttpServletRequest request) {
        return request.getRemoteHost();
    }
}
