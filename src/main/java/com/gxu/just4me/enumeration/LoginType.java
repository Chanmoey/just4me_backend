package com.gxu.just4me.enumeration;

/**
 * @author Chanmoey
 */

public enum LoginType {
    // 微信登录
    USER_WX(0, "微信登录"),

    // 邮箱登录
    USER_EMAIL(1, "邮箱登录");

    private final Integer value;
    private final String description;

    LoginType(Integer value, String description) {
        this.value = value;
        this.description = description;
    }
}
