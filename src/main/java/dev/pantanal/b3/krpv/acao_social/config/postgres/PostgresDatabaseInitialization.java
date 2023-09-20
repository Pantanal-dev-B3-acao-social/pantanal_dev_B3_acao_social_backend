package dev.pantanal.b3.krpv.acao_social.config.postgres;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class PostgresDatabaseInitialization {

    @Value("${db-name}")
    private String dbName;

    @Value("${spring-boot-environment}")
    private String environment;

    @Bean
    public CommandLineRunner createDatabase(JdbcTemplate jdbcTemplate, SeedDataService seedDataService) {
        return args -> {
            if (!databaseExists(jdbcTemplate, dbName)) {
                jdbcTemplate.execute("CREATE DATABASE " + dbName);
            }
            /*
             * Verifique o valor de spring.profiles.active é "development" para executar seed
             */
            if ("development".equalsIgnoreCase(environment)) {
                seedDataService.executeAllSeeds(orderExecuteSeeds());
            }
        };
    }

    /**
     * Consulta SQL para verificar se o banco de dados existe
     */
    private boolean databaseExists(JdbcTemplate jdbcTemplate, String dbName) {
        String sql = "SELECT 1 FROM pg_database WHERE datname = ?";
        try {
            Integer result = jdbcTemplate.queryForObject(sql, Integer.class, dbName);
            return result != null && result == 1;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * determina quais seed irão executar e sua respectiva ordem
     */
    private String[] orderExecuteSeeds() {
        String[] seedsForExecute = {
//                "db/seed/company_seed.sql",
//                "db/seed/ong_seed.sql",
                "db/seed/social_action_seed.sql",
//                "db/seed/session_seed.sql",
        };
        return seedsForExecute;
    }
}
