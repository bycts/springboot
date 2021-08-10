package com.example.byc.blog;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
/*返回状态码注解 对应页面*/
@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotfoundException extends RuntimeException{
/*ctrl+o查看须要实现的方法*/

    public NotfoundException() {
        super();
    }

    public NotfoundException(String message) {
        super(message);
    }

    public NotfoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
