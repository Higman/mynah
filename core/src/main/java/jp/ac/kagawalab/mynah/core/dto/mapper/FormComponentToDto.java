package jp.ac.kagawalab.mynah.core.dto.mapper;

import jp.ac.kagawalab.mynah.core.dto.model.FormComponentDto;
import jp.ac.kagawalab.mynah.core.dto.model.FormTypeDto;
import jp.ac.kagawalab.mynah.core.entity.FormComponent;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;

public class FormComponentToDto implements Converter<FormComponent, FormComponentDto> {
    private final DtoModelMapper modelMapper;

    public FormComponentToDto(DtoModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public FormComponentDto convert(MappingContext<FormComponent, FormComponentDto> context) {
        FormComponent formComponent = context.getSource();
        return new FormComponentDto(
            modelMapper.getModelMapper().map(formComponent.getType(), FormTypeDto.class),
            formComponent.getValue(),
            formComponent.getDescribing(),
            formComponent.getParamName(),
            formComponent.isRequired(),
            formComponent.getOrderNumber()
        );
    }
}
