package jp.ac.kagawalab.mynah.core.oauth2.model;

import jp.ac.kagawalab.mynah.core.dto.mapper.DtoModelMapper;
import jp.ac.kagawalab.mynah.core.dto.model.RoleDto;
import jp.ac.kagawalab.mynah.core.entity.User;
import org.springframework.security.core.authority.AuthorityUtils;

public class FormLoginUser extends AbstractMynahUserDetails {

    private final User user;
    private final DtoModelMapper modelMapper;

    public FormLoginUser(User user, DtoModelMapper modelMapper) {
        super(user.getUserId(),
              user.getPassword(),
              AuthorityUtils.createAuthorityList(modelMapper.getModelMapper().map(user.getRole(), RoleDto.class).toString()));
        this.user = user;
        this.modelMapper = modelMapper;
    }

    @Override
    public int getId() {
        return this.user.getId();
    }

    @Override
    public String getUserName() {
        return this.user.getUserName();
    }
}
