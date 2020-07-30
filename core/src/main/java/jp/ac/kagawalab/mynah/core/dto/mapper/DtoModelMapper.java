package jp.ac.kagawalab.mynah.core.dto.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public final class DtoModelMapper {
    public ModelMapper getModelMapper() {
        ModelMapper mm = new ModelMapper();
        mm.addConverter(new RoleToDto());
        mm.addConverter(new StringToRoleDto());
        mm.addConverter(new FormTypeToDto());
        mm.addConverter(new FormComponentToDto(this));
        return mm;
    }
}
