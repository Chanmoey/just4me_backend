package com.gxu.just4me.api.v1;

import com.gxu.just4me.dto.TokenDTO;
import com.gxu.just4me.dto.TokenGetDTO;
import com.gxu.just4me.exception.http.ParameterException;
import com.gxu.just4me.service.WxAuthenticationService;
import com.gxu.just4me.utils.JwtToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Chanmoey
 */

@RestController
@RequestMapping("token")
public class TokenController {

    @Autowired
    WxAuthenticationService wxAuthenticationService;

    @PostMapping("")
    public Map<String, String> getToken(@RequestBody @Validated TokenGetDTO tokenGetDTO) {
        Map<String, String> map = new HashMap<>(2);
        String token = null;

        switch (tokenGetDTO.getType()) {

            case USER_WX: {
                token = this.wxAuthenticationService.code2Session(tokenGetDTO.getAccount());
                break;
            }

            case USER_EMAIL: {
                // TODO: 添加邮箱登录方式
                break;
            }

            default: {
                throw new ParameterException(10004);
            }
        }
        map.put("token", token);
        return map;
    }

    @PostMapping("/verify")
    public Map<String, Boolean> verify(@RequestBody @Validated TokenDTO tokenDTO) {

        if (tokenDTO == null) {
            throw new ParameterException(10005);
        }

        Map<String, Boolean> map = new HashMap<>(2);

        Boolean valid = JwtToken.verifyToken(tokenDTO.getToken());
        map.put("verify", valid);
        return map;
    }
}
