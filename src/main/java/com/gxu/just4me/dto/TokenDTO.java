package com.gxu.just4me.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

/**
 * @author Chanmoey
 */
@Setter
@Getter
@ToString
public class TokenDTO {

    @NotBlank
    private String token;
}
