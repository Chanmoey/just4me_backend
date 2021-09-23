package com.gxu.just4me.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gxu.just4me.dto.SessionDTO;
import com.gxu.just4me.exception.http.ParameterException;
import com.gxu.just4me.model.User;
import com.gxu.just4me.repository.UserRepository;
import com.gxu.just4me.utils.JwtToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.MessageFormat;
import java.util.Optional;

/**
 * @author Chanmoey
 */
@Service
public class WxAuthenticationServiceImpl implements WxAuthenticationService {

    @Value("${wx.code2session}")
    private String code2SessionUrl;

    @Value("${wx.app-id}")
    private String appid;

    @Value("${wx.app-secret}")
    private String appSecret;

    @Autowired
    ObjectMapper mapper;

    @Autowired
    UserRepository userRepository;

    @Override
    public String code2Session(String code) {
        // TODO: 1.生成URL，2.请求Url，并返回session文本，3.通过session文本生成SessionDTO对象注册用户。
        String url = MessageFormat.format(this.code2SessionUrl, this.appid, this.appSecret, code);
        RestTemplate restTemplate = new RestTemplate();
        String sessionText = restTemplate.getForObject(url, String.class);

        SessionDTO sessionDTO;
        try {
            sessionDTO = mapper.readValue(sessionText, SessionDTO.class);
        } catch (Exception e) {
            throw new ParameterException(10002);
        }
        return this.registerUser(sessionDTO);
    }

    @Override
    public String registerUser(SessionDTO sessionDTO) {
        // TODO: 1. 获取openid，判断是否为空。
        //  2.向数据库查询用户数据，如果用户存在，则返回JWT令牌。
        //  如果用户不存在，则注册用户，并返回JWT令牌。
        String openid = sessionDTO.getOpenid();
        if (openid == null) {
            throw new ParameterException(10003);
        }

        Optional<User> user = this.userRepository.findByOpenid(openid);
        if (user.isPresent()) {
            return JwtToken.getToken(user.get().getId());
        }

        User newUser = User.builder()
                .openid(openid)
                .build();
        this.userRepository.save(newUser);

        Long uid = newUser.getId();
        return JwtToken.getToken(uid);
    }
}