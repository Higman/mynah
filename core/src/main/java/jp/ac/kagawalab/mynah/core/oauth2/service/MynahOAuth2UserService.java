package jp.ac.kagawalab.mynah.core.oauth2.service;

import jp.ac.kagawalab.mynah.core.dto.mapper.DtoModelMapper;
import jp.ac.kagawalab.mynah.core.dto.model.RoleDto;
import jp.ac.kagawalab.mynah.core.entity.User;
import jp.ac.kagawalab.mynah.core.oauth2.security.MynahOAuth2User;
import jp.ac.kagawalab.mynah.core.repository.UserRepository;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Optional;

public class MynahOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;
    private final OAuth2UserUtil oAuth2UserUtil;

    @Setter(onMethod=@__({@Autowired}))
    DtoModelMapper modelMapper;

    @Autowired
    public MynahOAuth2UserService(UserRepository userRepository, OAuth2UserUtil oAuth2UserUtil) {
        this.userRepository = userRepository;
        this.oAuth2UserUtil = oAuth2UserUtil;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        String providerId = oAuth2UserUtil.getProviderId(userRequest, oAuth2User);
        // すでに登録済みのユーザ情報があるか検索
        int userId;
        RoleDto roleDto = RoleDto.ROLE_USER;
        Optional<User> existedUser = oAuth2UserUtil.getExistedUser(providerId);
        String userName;
        if (existedUser.isPresent()) {
            userId = existedUser.get().getId();
            roleDto = modelMapper.getModelMapper().map(existedUser.get().getRole(), RoleDto.class);
            userName = existedUser.get().getUserName();
        } else {
            userId = 0;
            userName = oAuth2User.getName();
        }
        String clientName = userRequest.getClientRegistration().getClientName();
        return new MynahOAuth2User(userId, clientName, providerId, userName, roleDto, oAuth2User);
    }
}
