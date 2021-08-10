package com.example.byc.blog.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/*自定义异常类，404,505页面是springboot自动处理的，可以自定义异常类进行处理*/
@ControllerAdvice /*controller中扫描*/
public class ControllerException {

    private Logger logger= LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler(Exception.class)/*拦截的异常信息是exception类型的*/
    public ModelAndView exception(HttpServletRequest request,Exception e) throws Exception {
        /*控制台输出异常信息*/
        logger.error("Request URL{},Exception:{}",request.getRequestURL());

        /*定义当找状态码的异常就不需要拦截,交给系统自动判断*/
        if (AnnotationUtils.findAnnotation(e.getClass(),ResponseStatus.class)!=null){
            throw e;
        }

        ModelAndView mv=new ModelAndView();
        mv.addObject("url",request.getRequestURL());
        mv.addObject("exception",e);
        mv.setViewName("error/error");
        return mv;
    }

}
