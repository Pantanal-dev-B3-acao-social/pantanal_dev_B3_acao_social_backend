package dev.pantanal.b3.krpv.acao_social.config.postgres;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.util.FileCopyUtils;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

@Service
public class SeedDataService {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public SeedDataService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void executeAllSeeds(String... seedFilesPaths) {
        for (String seedFilePath : seedFilesPaths) {
            executeSeedScript(seedFilePath);
        }
    }

    /**
     * Carregue o script SQL a partir do classpath
     * @param seedFilePath
     */
    public void executeSeedScript(String seedFilePath) {
        try {
//            Resource resource = new ClassPathResource(seedFilePath);
            Resource resource = new ClassPathResource(seedFilePath);
            InputStreamReader reader = new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8);
            String sqlScript = FileCopyUtils.copyToString(reader);
            // Execute o script SQL
            jdbcTemplate.execute(sqlScript);
        } catch (Exception e) {
            // Lide com qualquer exceção que possa ocorrer durante a execução do script
            e.printStackTrace();
            throw new RuntimeException("Erro ao executar o script de SEED: " + e.getMessage());
        }
    }
}