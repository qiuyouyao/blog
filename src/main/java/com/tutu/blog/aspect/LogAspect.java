package com.tutu.blog.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * AOP日志记录
 * @author tutu 2020/4/30 14:58
 */
@Aspect
@Component
public class LogAspect {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * Controller包及其子包
     */
    @Pointcut("execution(* com.tutu.blog.controller..*.*(..))")
    public void log(){}

    @Before("log()")
    public void doBefore(JoinPoint joinPoint){
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert attributes != null;
        HttpServletRequest request = attributes.getRequest();
        String url = request.getRequestURL().toString();
        String ip = request.getRemoteAddr();
        String classAndMethod = joinPoint.getSignature().getDeclaringTypeName() +"."+joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        RequestLog requestLog = new RequestLog(url, ip, classAndMethod, args);
        logger.info("Request--> {}", requestLog);
    }

    @After("log()")
    public void doAfter(){
    }

    @AfterReturning(returning = "result", pointcut = "log()")
    public void doAfterReturn(Object result){
        logger.info("result--> {}", result);
    }

    private static class RequestLog{
        private final String url;
        private final String ip;
        private final String classAndMethod;
        private final Object[] args;

        public RequestLog(String url, String ip, String classAndMethod, Object[] args) {
            this.url = url;
            this.ip = ip;
            this.classAndMethod = classAndMethod;
            this.args = args;
        }

        @Override
        public String toString() {
            return "RequestLog{" +
                    "url='" + url + '\'' +
                    ", ip='" + ip + '\'' +
                    ", classAndMethod='" + classAndMethod + '\'' +
                    ", args=" + Arrays.toString(args) +
                    '}';
        }
    }
}
