package jp.ac.kagawalab.mynah.core.oauth2.security;

import jp.ac.kagawalab.mynah.core.dto.model.RoleDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.io.Serializable;
import java.util.*;

public class MynahOAuth2User implements OAuth2User, Serializable {
    private static final long serialVersionUID = -7313088102246374282L;
    public static final String NAME_ATTRIBUTE_KEY = "name";

    private final int id;
    private final String provider;
    @Getter
    private final String providerId;
    private final RoleDto role;
    private final Map<String, Object> attributes;
    private final Collection<org.springframework.security.core.GrantedAuthority> authorities;

    public MynahOAuth2User(int id, String provider, String providerId, RoleDto role, OAuth2User oAuth2User) {
        this.id = id;
        this.provider = provider;
        this.providerId = providerId;
        this.role = role;
        this.attributes = new HashMap<>(oAuth2User.getAttributes());
        this.authorities =  new HashSet<>(oAuth2User.getAuthorities());
        // Role 情報に基づいて権限を追加する
        authorities.add(new SimpleGrantedAuthority(role.toString()));
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getName() {
        return getAttribute(NAME_ATTRIBUTE_KEY);
    }
}
