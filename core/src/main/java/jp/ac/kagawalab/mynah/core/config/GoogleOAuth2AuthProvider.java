package jp.ac.kagawalab.mynah.core.config;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.client.authentication.OAuth2LoginAuthenticationToken;

import java.util.Collections;

public class GoogleOAuth2AuthProvider implements AuthenticationProvider {

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) authentication;
        return new OAuth2AuthenticationToken(token.getPrincipal(), Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN")), token.getAuthorizedClientRegistrationId());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return OAuth2AuthenticationToken.class.isAssignableFrom(authentication);
    }
}
