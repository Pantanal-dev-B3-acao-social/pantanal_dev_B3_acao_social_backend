package dev.pantanal.b3.krpv.acao_social.utils;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;

import java.util.Optional;

public class Utils {

    public static <D, T> Page<D> mapEntityPageIntoDtoPage(Page<T> entities, Class<D> dtoClass) {
        ModelMapper modelMapper = new ModelMapper();
        return entities.map(objectEntity -> modelMapper.map(objectEntity, dtoClass));
    }
}
