package jp.ac.kagawalab.mynah.core.dto.mapper;

import jp.ac.kagawalab.mynah.core.entity.User;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import org.springframework.security.oauth2.core.user.OAuth2User;

public class OAuth2UserToUser implements Converter<OAuth2User, User> {
    @Override
    public User convert(MappingContext<OAuth2User, User> context) {
        Integer tmpId;
        return new User();
    }
}
