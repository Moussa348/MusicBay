package com.keita.musicbay.security;
/*


import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.java.Log;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Log
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtProvider jwtProvider;

    @Override
    protected void doFilterInternal(@NotNull HttpServletRequest httpServletRequest,@NotNull HttpServletResponse httpServletResponse,@NotNull FilterChain filterChain) throws ServletException, IOException {
        try{
            DecodedJWT token = jwtProvider.verify(httpServletRequest.getHeader("Authorization"),httpServletRequest);

            if(token != null)
                SecurityContextHolder.getContext().setAuthentication(new JwtAuthentication(token,httpServletRequest.getRemoteAddr()));
        }catch (JWTVerificationException e){
            log.warning(e.getMessage().toUpperCase());
        }
        filterChain.doFilter(httpServletRequest,httpServletResponse);
    }
}
 */
