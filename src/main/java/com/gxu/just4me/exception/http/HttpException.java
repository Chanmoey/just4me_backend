package com.gxu.just4me.exception.http;

import lombok.Getter;

/**
 * @author Chanmoey
 */
@Getter
public class HttpException extends RuntimeException{

    protected int code;
    protected int httpStatusCode = 500;
}
