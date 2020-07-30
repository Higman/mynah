package jp.ac.kagawalab.mynah.core.dto.model;

import lombok.Value;

@Value
public class FormComponentDto {
    FormTypeDto formType;
    String value;
    String describing;
    String paramName;
    boolean isRequired;
    int orderNumber;
}
