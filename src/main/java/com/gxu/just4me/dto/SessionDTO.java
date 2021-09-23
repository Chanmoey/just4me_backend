package com.gxu.just4me.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Chanmoey
 */
@Getter
@Setter
@ToString
public class SessionDTO {

    private String openid;
    private String sessionKey;
}
