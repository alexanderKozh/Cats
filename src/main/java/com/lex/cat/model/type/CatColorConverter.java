package com.lex.cat.model.type;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class CatColorConverter implements AttributeConverter<CatColor, String> {

    @Override
    public String convertToDatabaseColumn(CatColor color) {
        return color != null ? color.getName() : null;
    }

    @Override
    public CatColor convertToEntityAttribute(String colorName) {
        return CatColor.fromName(colorName);
    }
}