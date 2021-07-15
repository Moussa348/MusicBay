package com.keita.musicbay.security;
/*

import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Arrays;
import java.util.Collection;

public class JwtAuthentication implements Authentication {

    private final DecodedJWT token;
    private final String ipAddress;

    public JwtAuthentication(DecodedJWT token,String ipAddress) {
        this.token = token; this.ipAddress = ipAddress;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        final Claim claimType = token.getClaim(JwtProvider.ROLE_CLAIM_NAME);
        return claimType.isNull()?null: Arrays.asList(new SimpleGrantedAuthority(claimType.asString()));
    }

    @Override
    public Object getCredentials() {
        return token;
    }

    @Override
    public Object getDetails() {
        return token;
    }

    @Override
    public Object getPrincipal() {
        return token.getSubject();
    }

    @Override
    public boolean isAuthenticated() {
        return true;
    }

    @Override
    public void setAuthenticated(boolean authenticated) throws IllegalArgumentException {
        if(!authenticated){
            throw new IllegalArgumentException("User could not be authenticated");
        }
    }

    @Override
    public String getName() {
        return token.getSubject();
    }
}
 */
