package com.gxu.just4me.service;

import com.gxu.just4me.dto.SessionDTO;

/**
 * @author Chanmoey
 */
public interface WxAuthenticationService {

    String code2Session(String code);

    String registerUser(SessionDTO sessionDTO);
}
