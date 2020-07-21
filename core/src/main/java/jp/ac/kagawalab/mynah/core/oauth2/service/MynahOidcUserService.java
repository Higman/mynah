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
import org.springframework.security.oauth2.core.oidc.user.OidcUser;

import java.util.Arrays;
import java.util.Optional;

@ConfigurationProperties(prefix = "oauth2")
public class MynahOidcUserService extends OidcUserService {
    private final UserRepository userRepository;
    private final OAuth2UserUtil oAuth2UserUtil;

    @Setter(onMethod=@__({@Autowired}))
    private MynahModelMapper modelMapper;

    @Setter
    private String[] targetEmailDomains;

    @Autowired
    public MynahOidcUserService(UserRepository userRepository, OAuth2UserUtil oAuth2UserUtil) {
        this.userRepository = userRepository;
        this.oAuth2UserUtil = oAuth2UserUtil;
    }

    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) {
        OidcUser oidcUser = super.loadUser(userRequest);

        // Emailのドメイン名検証
        String email = oidcUser.getEmail();
        if (Arrays.stream(targetEmailDomains).noneMatch(ted -> email.substring(email.lastIndexOf('@')).matches("@"+ted))) {
            throw new IllegalEmailDomainException("", email, targetEmailDomains);
        }

        // 一意キーの取得
        String providerId = oAuth2UserUtil.getProviderId(userRequest, oidcUser);
        // すでに登録済みのユーザ情報があるか検索
        int userId;
        RoleDto roleDto = null;
        String provider = userRequest.getClientRegistration().getClientName();
        Optional<User> existedUser = oAuth2UserUtil.getExistedUser(oidcUser.getSubject());
        if (existedUser.isPresent()) {
            userId = existedUser.get().getId();
            roleDto = modelMapper.getModelMapper().map(existedUser.get().getRole(), RoleDto.class);
        } else {
            OAuth2UserUtil.RegisterResult result = oAuth2UserUtil.register(provider, providerId);
            userId = result.getId();
            roleDto = result.getRoleDto();
        }
        return new MynahOidcUser(userId, provider, providerId, roleDto, oidcUser);
    }
}
