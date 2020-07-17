package jp.ac.kagawalab.mynah.core.security.oauth2;

import jp.ac.kagawalab.mynah.core.dto.model.RoleDto;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.io.Serializable;
import java.util.*;

@AllArgsConstructor
public class MynahOAuth2User implements OAuth2User, Serializable {
    private static final long serialVersionUID = -7313088102246374282L;

    private final int id;
    private final String provider;
    private final String providerId;
    private final RoleDto role;
    private final OAuth2User oAuth2User;

    @Override
    public Map<String, Object> getAttributes() {
        return oAuth2User.getAttributes();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Role 情報に基づいて権限を追加する
        Collection<GrantedAuthority> authorities = new HashSet<>(oAuth2User.getAuthorities());
        authorities.add(new SimpleGrantedAuthority(role.toString()));
        return authorities;
    }

    @Override
    public String getName() {
        return oAuth2User.getName();
    }
}
