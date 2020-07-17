package jp.ac.kagawalab.mynah.core.service.oauth2;

import jp.ac.kagawalab.mynah.core.dto.mapper.MynahModelMapper;
import jp.ac.kagawalab.mynah.core.dto.model.RoleDto;
import jp.ac.kagawalab.mynah.core.entity.user.User;
import jp.ac.kagawalab.mynah.core.repository.UserRepository;
import jp.ac.kagawalab.mynah.core.security.oauth2.MynahOidcUser;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;

import java.util.Optional;

public class MynahOidcUserService extends OidcUserService {
    final UserRepository userRepository;

    @Setter(onMethod=@__({@Autowired}))
    MynahModelMapper modelMapper;

    public MynahOidcUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
        OidcUser oidcUser = super.loadUser(userRequest);

        // すでに登録済みのユーザ情報があるか検索
        int userId;
        RoleDto roleDto = RoleDto.ROLE_USER;
        Optional<User> existedUser = getExistedUser(oidcUser.getSubject());
        if (existedUser.isPresent()) {
            userId = existedUser.get().getId();
            roleDto = modelMapper.getModelMapper().map(existedUser.get().getRole(), RoleDto.class);
        } else {
            userId = 0;
        }
        String clientName = userRequest.getClientRegistration().getClientName();
        return new MynahOidcUser(userId, clientName, oidcUser.getSubject(), roleDto, oidcUser);
    }

    private Optional<User> getExistedUser(final String subject) {
        return Optional.ofNullable(userRepository.findByProviderId(subject));
    }
}
