package com.gxu.just4me.exception.http;

/**
 * @author Chanmoey
 */
public class ParameterException extends HttpException{

    public ParameterException(int code) {
        this.code = code;
        this.httpStatusCode = 400;
    }
}
