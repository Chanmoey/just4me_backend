package com.gxu.just4me.exception;


import com.gxu.just4me.core.configuration.ExceptionCodeConfiguration;
import com.gxu.just4me.exception.http.HttpException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

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
    public UnifyResponse handleException(HttpServletRequest request) {
        String requestMessage = this.getRequestMessage(request);
        String message = this.configuration.getMessage(9999);
        return new UnifyResponse(9999, message, requestMessage);
    }

    @ExceptionHandler(value = HttpException.class)
    public ResponseEntity<UnifyResponse> handleHttpException(HttpServletRequest request, HttpException e) {
        // ResponseEntity 要接收三个参数:UnifyResponse, HttpHeaders, HttpStatus.

        String requestMessage = this.getRequestMessage(request);
        UnifyResponse unifyResponse = new UnifyResponse(e.getCode(),
                this.configuration.getMessage(e.getCode()),
                requestMessage);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpStatus httpStatus = HttpStatus.resolve(e.getHttpStatusCode());
        assert httpStatus != null;
        return new ResponseEntity<>(unifyResponse, headers, httpStatus);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public UnifyResponse handleMethodArgumentNotValidException(HttpServletRequest request,
                                                               MethodArgumentNotValidException e) {
        String requestMessage = this.getRequestMessage(request);

        List<ObjectError> errors = e.getBindingResult().getAllErrors();
        String message = this.formatAllErrorMessages(errors);

        return new UnifyResponse(10001, message, requestMessage);
    }

    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public UnifyResponse handleHttpMessageNotReadableException(HttpServletRequest request,
                                                               HttpMessageNotReadableException e) {
        String requestMessage = this.getRequestMessage(request);
        return new UnifyResponse(10000, this.configuration.getMessage(10000), requestMessage);
    }

    @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public UnifyResponse handleHttpMessageNotReadableException(HttpServletRequest request,
                                                               HttpRequestMethodNotSupportedException e) {
        String requestMessage = this.getRequestMessage(request);
        return new UnifyResponse(10006, this.configuration.getMessage(10006), requestMessage);
    }

    /**
     * 拼接多个body参数校验异常消息
     */
    private String formatAllErrorMessages(List<ObjectError> errors) {
        StringBuffer errorMsg = new StringBuffer();
        errors.forEach(error ->
                errorMsg.append(error.getDefaultMessage()).append(';')
        );
        return errorMsg.toString();
    }

    private String getRequestMessage(HttpServletRequest request) {
        String method = request.getMethod();
        String url = request.getRequestURI();
        return method + " " + url;
    }
}
