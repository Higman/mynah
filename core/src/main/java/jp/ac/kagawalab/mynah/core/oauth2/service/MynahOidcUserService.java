package jp.ac.kagawalab.mynah.core.oauth2.service;

import jp.ac.kagawalab.mynah.core.dto.mapper.MynahModelMapper;
import jp.ac.kagawalab.mynah.core.dto.model.RoleDto;
import jp.ac.kagawalab.mynah.core.entity.user.User;
import jp.ac.kagawalab.mynah.core.oauth2.exception.IllegalEmailDomainException;
import jp.ac.kagawalab.mynah.core.oauth2.security.MynahOidcUser;
import jp.ac.kagawalab.mynah.core.repository.UserRepository;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.OAuth2ErrorCodes;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

@ConfigurationProperties(prefix = "oauth2")
public class MynahOidcUserService extends OidcUserService {
    final UserRepository userRepository;

    @Setter(onMethod=@__({@Autowired}))
    private MynahModelMapper modelMapper;

    @Setter
    private String[] targetEmailDomains;

    public MynahOidcUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) {
        OidcUser oidcUser = super.loadUser(userRequest);

        // Emailのドメイン名検証
        String email = oidcUser.getEmail();
        if (Arrays.stream(targetEmailDomains).noneMatch(ted -> email.substring(email.lastIndexOf('@')).matches("@"+ted))) {
            throw new IllegalEmailDomainException("", email, targetEmailDomains);
        }

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
