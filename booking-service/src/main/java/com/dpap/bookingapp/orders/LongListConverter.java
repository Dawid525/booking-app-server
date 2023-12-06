package com.dpap.bookingapp.orders;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Converter
public class LongListConverter implements AttributeConverter<List<Long>, String> {
    private static final String SPLIT_CHAR = ";";

    @Override
    public String convertToDatabaseColumn(List<Long> longList) {
        return longList != null ? String.join(SPLIT_CHAR, longList.stream().map(Object::toString).toArray(String[]::new)) : "";
    }

    @Override
    public List<Long> convertToEntityAttribute(String string) {
        if (string != null && !string.isEmpty()) {
            String[] longStrings = string.split(SPLIT_CHAR);
            List<Long> longList = new ArrayList<>();
            for (String longString : longStrings) {
                longList.add(Long.parseLong(longString));
            }
            return longList;
        } else {
            return new ArrayList<>();
        }
    }
}