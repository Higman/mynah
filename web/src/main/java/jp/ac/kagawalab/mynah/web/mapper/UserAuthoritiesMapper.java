package jp.ac.kagawalab.mynah.web.mapper;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;

import java.util.Collection;
import java.util.stream.Collectors;

public class UserAuthoritiesMapper implements GrantedAuthoritiesMapper {
    @Override
    public Collection<? extends GrantedAuthority> mapAuthorities(Collection<? extends GrantedAuthority> authorities) {
        return authorities.stream().filter(a -> a.getAuthority().contains("ROLE")).collect(Collectors.toSet());
    }
}
