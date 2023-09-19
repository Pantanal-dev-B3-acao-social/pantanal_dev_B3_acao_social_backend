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

    @Bean
    public CommandLineRunner createDatabase(JdbcTemplate jdbcTemplate) {
        return args -> {
            if (!databaseExists(jdbcTemplate, dbName)) {
                jdbcTemplate.execute("CREATE DATABASE " + dbName);
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
}
