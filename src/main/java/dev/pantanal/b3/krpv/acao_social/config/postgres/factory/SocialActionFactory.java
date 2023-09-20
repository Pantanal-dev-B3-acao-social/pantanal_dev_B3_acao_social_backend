package dev.pantanal.b3.krpv.acao_social.config.postgres.factory;

import java.util.Random;
import java.util.UUID;

public class SocialActionFactory {
    private static final Random random = new Random();

    public static String generateRandomUsername() {
        return UUID.randomUUID().toString().substring(0, 8); // Gera um username aleatório de 8 caracteres
    }

    public static String generateRandomEmail() {
        return "user" + random.nextInt(1000) + "@example.com"; // Gera um email aleatório
    }

    public static String generateRandomPassword() {
        return UUID.randomUUID().toString().substring(0, 12); // Gera uma senha aleatória de 12 caracteres
    }
}
