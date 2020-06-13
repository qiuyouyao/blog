package com.tutu.blog.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author tutu 2020/5/4 7:14
 */
@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class NotAllowDeleteException extends RuntimeException{

    public NotAllowDeleteException() {
    }

    public NotAllowDeleteException(String message) {
        super(message);
    }

    public NotAllowDeleteException(String message, Throwable cause) {
        super(message, cause);
    }
}
