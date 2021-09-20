package com.gxu.just4me.dto;

import com.gxu.just4me.enumeration.LoginType;
import com.gxu.just4me.validators.TokenPassword;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;


/**
 * @author Chanmoey
 */
@Getter
@Setter
@ToString
public class TokenGetDTO {

    @NotBlank(message = "account不允许为空")
    private String account;

    @TokenPassword(max=30, message = "{token.password}")
    private String password;

    private LoginType type;
}
