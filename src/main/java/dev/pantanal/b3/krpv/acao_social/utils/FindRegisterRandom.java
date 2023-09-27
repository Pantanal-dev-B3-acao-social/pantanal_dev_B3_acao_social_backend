package dev.pantanal.b3.krpv.acao_social.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class FindRegisterRandom {

    private final JdbcTemplate jdbcTemplate;
    @Autowired
    public FindRegisterRandom(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // TODO: converter para UUID
    public UUID execute(String table, Integer amount) {
        String sql = "SELECT * FROM "+ table +" ORDER BY RANDOM() LIMIT " + amount + ";";
        try {
            String id = jdbcTemplate.queryForObject(sql, String.class);
            return UUID.fromString(id);
        } catch (EmptyResultDataAccessException e) {
            // Trate o caso em que a tabela está vazia
            // TODO: lançar exceção
            return null;
        }
    }
}
