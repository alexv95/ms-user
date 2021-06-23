package com.bci.msuser.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.bci.msuser.constant.SecurityConstant;
import org.springframework.beans.factory.annotation.Value;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.GrantedAuthority;

import java.nio.charset.StandardCharsets;
import java.util.Date;

public class JWTTokenProvider {
    /**
     * this should be stored in secret on config server or vault, not as string
     * **/
    @Value("${jwt.secret}")
    private String secret;

    public String generateJwtToken(UserPrincipal userPrincipal) {
        //String[] claims = getClaimsFromUser(userPrincipal);
        return JWT.create().withIssuer(SecurityConstant.TOKEN_ORIGIN).
                withIssuedAt(new Date()).
                withSubject(userPrincipal.getUsername()).
                withExpiresAt(new Date(System.currentTimeMillis()+ SecurityConstant.TOKEN_EXPIRATION_TIME) )
                .sign(Algorithm.HMAC512(secret.getBytes(StandardCharsets.UTF_8)));
    }

    private JWTVerifier getJWTVerifier(){
        JWTVerifier verifier;
        try{
            Algorithm algorithm=Algorithm.HMAC512(secret);
            verifier = JWT.require(algorithm).withIssuer(SecurityConstant.TOKEN_ORIGIN).build();
        }
        catch(JWTVerificationException error){
            throw new JWTVerificationException(SecurityConstant.TOKEN_CANNOT_BE_VERIFIED);
        }
        return verifier;
    }

    private boolean isTokenExpired(JWTVerifier verifier, String token) {
        Date expiration = verifier.verify(token).getExpiresAt();
        return expiration.before(new Date());
    }

    public boolean isTokenValid(String username, String token) {
        JWTVerifier verifier = getJWTVerifier();
        return StringUtils.isNotEmpty(username) && !isTokenExpired(verifier, token);
    }
    public String getSubject(String token) {
        JWTVerifier verifier = getJWTVerifier();
        return verifier.verify(token).getSubject();
    }

    //private String getClaimsFromUser(UserPrincipal userPrincipal) {
      // return String[];
    //}
}
