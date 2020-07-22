package jp.ac.kagawalab.mynah.core.oauth2.security;

import jp.ac.kagawalab.mynah.core.dto.model.RoleDto;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.io.Serializable;
import java.util.*;

public class MynahOAuth2User extends org.springframework.security.core.userdetails.User implements OAuth2User, Serializable {
    private static final long serialVersionUID = -7313088102246374282L;
    public static final String NAME_ATTRIBUTE_KEY = "name";

    private final int id;
    private final String provider;
    @Getter
    private final String providerId;
    @Getter
    private final String userName;
    private final RoleDto role;
    private final Map<String, Object> attributes;

    public MynahOAuth2User(int id, String provider, String providerId, String userName, RoleDto role, OAuth2User oAuth2User) {
        super(userName, providerId, new HashSet<GrantedAuthority>(oAuth2User.getAuthorities()) {
            {
                // Role 情報に基づいて権限を追加する
                add(new SimpleGrantedAuthority(role.toString()));
            }
        });
        this.id = id;
        this.provider = provider;
        this.providerId = providerId;
        this.userName = userName;
        this.role = role;
        this.attributes = new HashMap<>(oAuth2User.getAttributes());
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public String getName() {
        return getAttribute(NAME_ATTRIBUTE_KEY);
    }
}
