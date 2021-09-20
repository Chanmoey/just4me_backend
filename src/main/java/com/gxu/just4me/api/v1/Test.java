package com.gxu.just4me.api.v1;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Chanmoey
 */
@RestController
@RequestMapping("test")
public class Test{

    @GetMapping("/1")
    public String test() {
        throw new RuntimeException();
    }
}
