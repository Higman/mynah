package jp.ac.kagawalab.mynah.core.dto.mapper;

import jp.ac.kagawalab.mynah.core.dto.model.FormTypeDto;
import jp.ac.kagawalab.mynah.core.entity.FormType;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;

public class FormTypeToDto implements Converter<FormType, FormTypeDto> {
    public FormTypeDto convert(MappingContext<FormType, FormTypeDto> context) {
        FormType formType = context.getSource();
        return FormTypeDto.valueOf(formType.getName());
    }
}
