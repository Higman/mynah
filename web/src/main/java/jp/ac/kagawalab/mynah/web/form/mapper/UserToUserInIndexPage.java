package jp.ac.kagawalab.mynah.web.form.mapper;

import jp.ac.kagawalab.mynah.core.entity.User;
import jp.ac.kagawalab.mynah.web.form.model.UserInIndexPage;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;

public class UserToUserInIndexPage implements Converter<User, UserInIndexPage> {
    @Override
    public UserInIndexPage convert(MappingContext<User, UserInIndexPage> context) {
        return new UserInIndexPage(context.getSource().getUserName());
    }
}
