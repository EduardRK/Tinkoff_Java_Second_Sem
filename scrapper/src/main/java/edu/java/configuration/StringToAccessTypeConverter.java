package edu.java.configuration;

import org.springframework.core.convert.converter.Converter;

public class StringToAccessTypeConverter implements Converter<String, ApplicationConfig.AccessType> {

    @Override
    public ApplicationConfig.AccessType convert(String source) {
        return ApplicationConfig.AccessType.valueOf(source.toUpperCase());
    }
}
