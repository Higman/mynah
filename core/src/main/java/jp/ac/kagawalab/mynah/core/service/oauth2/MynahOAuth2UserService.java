package jp.ac.kagawalab.mynah.core.service.oauth2;

import jp.ac.kagawalab.mynah.core.dto.mapper.MynahModelMapper;
import jp.ac.kagawalab.mynah.core.dto.model.RoleDto;
import jp.ac.kagawalab.mynah.core.entity.user.User;
import jp.ac.kagawalab.mynah.core.repository.UserRepository;
import jp.ac.kagawalab.mynah.core.security.oauth2.MynahOAuth2User;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Optional;

public class MynahOAuth2UserService extends DefaultOAuth2UserService {

    final UserRepository userRepository;

    @Setter(onMethod=@__({@Autowired}))
    MynahModelMapper modelMapper;

    public MynahOAuth2UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        String subject = "";  // 一意キー
        switch (userRequest.getClientRegistration().getClientName()) {
            case "Google":
                subject = oAuth2User.getAttributes().get("id").toString();
                break;
            default: throw new IllegalArgumentException("there is no such identity provider. cannot login by " + userRequest.getClientRegistration().getClientName());
        }
        // すでに登録済みのユーザ情報があるか検索
        int userId;
        RoleDto roleDto = RoleDto.ROLE_USER;
        Optional<User> existedUser = getExistedUser(subject);
        if (existedUser.isPresent()) {
            userId = existedUser.get().getId();
            roleDto = modelMapper.getModelMapper().map(existedUser.get().getRole(), RoleDto.class);
        } else {
            userId = 0;
        }
        String clientName = userRequest.getClientRegistration().getClientName();
        return new MynahOAuth2User(userId, clientName, subject, roleDto, oAuth2User);
    }

    private Optional<User> getExistedUser(String subject) {
        return Optional.ofNullable(userRepository.findByProviderId(subject));
    }
}
