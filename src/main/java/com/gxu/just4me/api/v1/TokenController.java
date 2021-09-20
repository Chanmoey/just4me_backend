package com.gxu.just4me.api.v1;

import com.gxu.just4me.dto.TokenGetDTO;
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

    @PostMapping("")
    public Map<String, String> getToken(@RequestBody @Validated TokenGetDTO tokenGetDTO) {
        Map<String, String> map = new HashMap<>(2);
        String token = null;

        /*switch(tokenGetDTO.getType()) {
            case USER_WX:
                // TODO: 生成token字符串

        }*/
        return map;
    }
}
