package jp.ac.kagawalab.mynah.core.dto.mapper;

import jp.ac.kagawalab.mynah.core.dto.model.RoleDto;
import jp.ac.kagawalab.mynah.core.entity.Role;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;

public class StringToRoleDto implements Converter<String, RoleDto> {
    @Override
    public RoleDto convert(MappingContext<String, RoleDto> context) {
        RoleDto ret = null;
        for (RoleDto value : RoleDto.values()) {
            if (value.toString().equals(context.getSource())) {
                ret = value;
            }
        }
        return ret;
    }
}
