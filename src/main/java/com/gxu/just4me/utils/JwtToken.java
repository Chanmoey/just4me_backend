package com.gxu.just4me.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Chanmoey
 */
@Component
public class JwtToken {

    private static String jwtKey;
    private static Integer expiredTimeIn;
    private static final Integer defaultScope = 8;
    private static final String TOKEN_PREFIX = "Bearer";

    @Value("${just4me.security.jwt-key}")
    public void setJwtKey(String jwtKey) {
        JwtToken.jwtKey = jwtKey;
    }

    @Value("${just4me.security.token-expired-in}")
    public void setExpiredTimeIn(Integer expiredTimeIn) {
        JwtToken.expiredTimeIn = expiredTimeIn;
    }

    public static String getToken(Long uid, Integer scope) {
        return JwtToken.makeToken(uid, scope);
    }

    public static String getToken(Long uid) {
        return JwtToken.makeToken(uid, JwtToken.defaultScope);
    }

    private static String makeToken(Long uid, Integer scope) {
        Algorithm algorithm = Algorithm.HMAC256(JwtToken.jwtKey);
        Map<String, Date> dateMap = JwtToken.getCrateAndExpiredTime();
        return JWT.create()
                .withClaim("uid", uid)
                .withClaim("scope", scope)
                .withIssuedAt(dateMap.get("now"))
                .withExpiresAt(dateMap.get("expiredTime"))
                .sign(algorithm);
    }

    private static Map<String, Date> getCrateAndExpiredTime() {
        Map<String, Date> dateMap = new HashMap<>(2);
        Calendar calendar = Calendar.getInstance();
        Date now = calendar.getTime();
        calendar.add(Calendar.SECOND, JwtToken.expiredTimeIn);
        dateMap.put("now", now);
        dateMap.put("expiredTime", calendar.getTime());
        return dateMap;
    }

    public static Map<String, Claim> getClaims(String token) {
        DecodedJWT decodedJwt;
        Algorithm algorithm = Algorithm.HMAC256(JwtToken.jwtKey);
        JWTVerifier jwtVerifier = JWT.require(algorithm).build();

        try {
            decodedJwt = jwtVerifier.verify(token);
        } catch (JWTVerificationException e) {
            return null;
        }
        return decodedJwt.getClaims();
    }

    public static Boolean verifyToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(JwtToken.jwtKey);
            JWTVerifier verifier = JWT.require(algorithm).build();
            verifier.verify(token);
        } catch (JWTVerificationException e) {
            return false;
        }

        return true;
    }
}
