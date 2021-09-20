package com.gxu.just4me.exception;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Chanmoey
 */
@Getter
@AllArgsConstructor
public class UnifyResponse {

    private int code;
    private String message;
    private String request;
}
