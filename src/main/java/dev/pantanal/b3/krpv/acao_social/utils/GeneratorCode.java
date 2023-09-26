package dev.pantanal.b3.krpv.acao_social.utils;

import org.springframework.stereotype.Component;

import java.text.Normalizer;
import java.util.regex.Pattern;

@Component
public class GeneratorCode {

    public String execute(String input) {
        // Remove caracteres especiais e acentos
        String normalized = Normalizer.normalize(input, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        normalized = pattern.matcher(normalized).replaceAll("");
        // Substitui espaços por hífens e coloca tudo em maiúsculas
        normalized = normalized.replaceAll("\\s+", "-").toUpperCase();
        return normalized;
    }

}
