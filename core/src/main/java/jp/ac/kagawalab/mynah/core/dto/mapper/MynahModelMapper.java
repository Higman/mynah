package jp.ac.kagawalab.mynah.core.dto.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class MynahModelMapper {
    public ModelMapper getModelMapper() {
        ModelMapper mm = new ModelMapper();
        mm.addConverter(new RoleConverterToDto());
        return mm;
    }
}
