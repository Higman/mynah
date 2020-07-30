package jp.ac.kagawalab.mynah.core.oauth2.model;

import jp.ac.kagawalab.mynah.core.dto.model.RoleDto;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public abstract class AbstractMynahUserDetails extends org.springframework.security.core.userdetails.User {
    public AbstractMynahUserDetails(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

    public abstract int getId();
    public abstract String getUserName();
    public abstract RoleDto getRole();
}
