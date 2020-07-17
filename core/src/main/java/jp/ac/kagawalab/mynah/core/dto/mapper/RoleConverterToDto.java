package jp.ac.kagawalab.mynah.core.dto.mapper;

import jp.ac.kagawalab.mynah.core.dto.model.RoleDto;
import jp.ac.kagawalab.mynah.core.entity.user.Role;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;

class RoleConverterToDto implements Converter<Role, RoleDto> {
    @Override
    public RoleDto convert(MappingContext<Role, RoleDto> context) {
        switch (context.getSource().getRole()) {
            case "admin":
                return RoleDto.ROLE_ADMIN;
            case "user":
                return RoleDto.ROLE_USER;
            default:
                throw new IllegalArgumentException("Role [" + context.getSource() + "] is not exist.");
        }
    }
}
