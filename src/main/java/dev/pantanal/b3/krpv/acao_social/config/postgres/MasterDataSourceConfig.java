package dev.pantanal.b3.krpv.acao_social.config.postgres;

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class MasterDataSourceConfig {

    @Primary
    @Bean
    public DataSourceProperties masterDataSourceProperties() {
        return new DataSourceProperties();
    }
}
