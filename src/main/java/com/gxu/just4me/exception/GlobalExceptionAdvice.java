package com.gxu.just4me.exception;


import com.gxu.just4me.core.configuration.ExceptionCodeConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Chanmoey
 */

@ControllerAdvice
public class GlobalExceptionAdvice {

    @Autowired
    private ExceptionCodeConfiguration configuration;

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    public UnifyResponse handlerException(HttpServletRequest request) {
        String requestMessage = this.getRequestMessage(request);
        String message = this.configuration.getMessage(9999);
        return new UnifyResponse(9999, message, requestMessage);
    }

    private String getRequestMessage(HttpServletRequest request) {
        String method = request.getMethod();
        String url = request.getRequestURI();
        return method + " " + url;
    }
}
