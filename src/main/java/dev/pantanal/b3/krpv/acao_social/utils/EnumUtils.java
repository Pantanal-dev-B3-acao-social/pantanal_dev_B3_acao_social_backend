package dev.pantanal.b3.krpv.acao_social.utils;

import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class EnumUtils<T extends Enum<T>> {

    private final Random random = new Random();

    public T getRandomValue(Class<T> enumClass) {
        // Verifique se a classe fornecida é um enum
        if (!enumClass.isEnum()) {
            throw new IllegalArgumentException("A classe não é um enum.");
        }
        // Obtenha os valores do enum
        T[] enumValues = enumClass.getEnumConstants();
        // Gere um índice aleatório com base no número de elementos no enum
        int randomIndex = random.nextInt(enumValues.length);
        // Retorne um caso aleatório do enum
        T randomEnum = enumValues[randomIndex];
        return randomEnum;
    }

    public T getRandomValueDiff(T statusDiff) {
        T item;
        do {
            item = getRandomValue((Class<T>) statusDiff.getClass());
        } while (statusDiff.equals(item));
        return item;
    }

}
