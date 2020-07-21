package jp.ac.kagawalab.mynah.core.oauth2.service;

import jp.ac.kagawalab.mynah.core.dto.mapper.MynahModelMapper;
import jp.ac.kagawalab.mynah.core.dto.model.RoleDto;
import jp.ac.kagawalab.mynah.core.entity.user.User;
import jp.ac.kagawalab.mynah.core.repository.RoleRepository;
import jp.ac.kagawalab.mynah.core.repository.UserRepository;
import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class OAuth2UserUtil {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final MynahModelMapper mapper;
    static final int NON_EXISTED_ID = -1;

    @Autowired
    public OAuth2UserUtil(UserRepository userRepository, RoleRepository roleRepository, MynahModelMapper mapper) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.mapper = mapper;
    }

    Optional<User> getExistedUser(final String subject) {
        return Optional.ofNullable(userRepository.findByProviderId(subject));
    }

    RegisterResult register(String provider, String providerId) {
        User entity = new User()
                .setProvider(provider)
                .setProviderId(providerId)
                .setRole(roleRepository.findByRole(RoleDto.ROLE_USER.toString()))
                .setOAuth2User(true);
        User user = userRepository.save(entity);
        return new RegisterResult(user.getId(), mapper.getModelMapper().map(user.getRole(), RoleDto.class));
    }

    String getProviderId(OAuth2UserRequest request, OAuth2User user) {
        if (request.getClientRegistration().getScopes().contains("openid")) {
            return ((OidcUser) user).getSubject();
        }
        switch (request.getClientRegistration().getClientName()) {
            case "Google":
                return user.getAttributes().get("id").toString();
            default: throw new IllegalArgumentException("there is no such identity provider. cannot login by " + request.getClientRegistration().getClientName());
        }
    }

    @Value
    class RegisterResult {
        int id;
        RoleDto roleDto;
    }
}
