package jp.ac.kagawalab.mynah.core.oauth2.model;

import jp.ac.kagawalab.mynah.core.dto.mapper.DtoModelMapper;
import jp.ac.kagawalab.mynah.core.dto.model.RoleDto;
import jp.ac.kagawalab.mynah.core.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

import java.util.Set;
import java.util.regex.Pattern;

public class FormLoginUser extends AbstractMynahUserDetails {

    private final User user;
    private final DtoModelMapper modelMapper;
    private final RoleDto role;

    public FormLoginUser(User user, DtoModelMapper modelMapper) {
        super(user.getUserId(),
              user.getPassword(),
              AuthorityUtils.createAuthorityList(modelMapper.getModelMapper()
                                                         .map(user.getRole(), RoleDto.class)
                                                         .toString())
        );
        this.user = user;
        this.modelMapper = modelMapper;
        Set<GrantedAuthority> auth = (Set<GrantedAuthority>) getAuthorities();
        Pattern pattern = Pattern.compile("ROLE_.*");
        String roleStr = auth.stream()
                .filter(grantedAuthority -> pattern.matcher(grantedAuthority.toString()).find())
                .map(GrantedAuthority::getAuthority)
                .findFirst().orElse(RoleDto.ROLE_USER.toString());
        this.role = modelMapper.getModelMapper().map(roleStr, RoleDto.class);
    }

    @Override
    public int getId() {
        return this.user.getId();
    }

    @Override
    public String getUserName() {
        return this.user.getUserName();
    }

    @Override
    public RoleDto getRole() {
        return role;
    }
}
