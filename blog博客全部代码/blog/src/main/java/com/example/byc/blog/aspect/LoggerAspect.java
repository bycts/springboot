package com.example.byc.blog.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/*日志使用切面实现*/
/*AOP：面向切面编程。 aop本来就负责日志输出
        简单的说它就是把我们程序重复的代码抽取出来，在需要执行的时候，使用动态代理的技术，在不修改源码的基础上，对我们的已有方法进行增强。*/
@Aspect
@Component
public class LoggerAspect {
    private final Logger logger= LoggerFactory.getLogger(this.getClass());

  /*<!-- 配置通知的类型，并且建立通知方法和切入点方法的关联-->*/
    @Pointcut("execution(* com.example.byc.blog.web.*.*(..))")/*切面拦截的类*/
    public void log(){}

    @Before("log()")
    public void doBefore(JoinPoint joinPoint){
        ServletRequestAttributes attributes= (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request=attributes.getRequest();
        String url=request.getRequestURL().toString();
        String ip=request.getRemoteAddr();
        String classMethod=joinPoint.getSignature().getDeclaringTypeName()+"-"+joinPoint.getSignature().getName();
        Object[] args=joinPoint.getArgs();

        RequestLog requestLog=new RequestLog(url,ip,classMethod,args);
            logger.info("Requst:"+requestLog);
        }
    @After("log()")
    public void doAfer(){
        logger.info("---------doAfter------");
    }

    @AfterReturning(returning = "result",pointcut = "log()")/*捕获方法返回内容*/
    public void doAfterReturn(Object result){

        logger.info("Result:"+result);
    }


}

 class RequestLog{
    private String url;
    private String ip;
    private String classMethod;
    private Object[] args;
    /*alter + insert 构造方法生成*/

     public RequestLog(String url, String ip, String classMethod, Object[] args) {
         this.url = url;
         this.ip = ip;
         this.classMethod = classMethod;
         this.args = args;
     }
 }
