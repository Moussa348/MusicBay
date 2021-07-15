package com.keita.musicbay.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.keita.musicbay.model.entity.User;
import lombok.Data;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.security.SecureRandom;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Data
@Component
public class JwtProvider {

    private final Algorithm algorithm;
    private final JWTVerifier jwtVerifier;
    private final long duration;

    public static String ROLE_CLAIM_NAME = "role";
    public static String USER_USERNAME_CLAIM_NAME = "userUsername";

    //add @Value
    public JwtProvider() {
        this.algorithm = Algorithm.HMAC256(SecureRandom.getSeed(16));
        this.jwtVerifier = JWT.require(algorithm).build();
        this.duration = TimeUnit.HOURS.toMillis(2);
    }

    public String generate(final User user){
        final long time = System.currentTimeMillis();

        return JWT.create()
                .withSubject(user.getUsername())
                .withClaim(ROLE_CLAIM_NAME,user.getRoles())
                .withClaim(USER_USERNAME_CLAIM_NAME,user.getUsername())
                .withIssuedAt(new Date(time))
                .withExpiresAt(new Date(time + duration))
                .sign(algorithm);

    }

    public DecodedJWT verify(String token, HttpServletRequest httpServletRequest) throws JWTVerificationException{
        if(token == null)
            throw new JWTVerificationException(("Token cannot be null," + "url=" + httpServletRequest.getRequestURL()));

        if(token.startsWith("Bearer "))
            token = token.replace("Bearer ","");

        return jwtVerifier.verify(token);
    }
}
