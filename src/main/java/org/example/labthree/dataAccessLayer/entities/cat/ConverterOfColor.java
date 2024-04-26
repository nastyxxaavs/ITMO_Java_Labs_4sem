package org.example.labthree.dataAccessLayer.entities.cat;


import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.example.labthree.dataAccessLayer.models.CatColors;

import java.awt.*;

@Converter(autoApply = true)
public class ConverterOfColor implements AttributeConverter<CatColors, String> {
    @Override
    public String convertToDatabaseColumn(CatColors color) {
        return color.name().toLowerCase();
    }

    @Override
    public CatColors convertToEntityAttribute(String dbData) {
        return CatColors.valueOf(dbData);
    }
}
