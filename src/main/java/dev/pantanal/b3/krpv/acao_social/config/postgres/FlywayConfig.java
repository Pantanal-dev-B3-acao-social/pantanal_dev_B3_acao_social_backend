package dev.pantanal.b3.krpv.acao_social.config.postgres;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FlywayConfig {

//    db/script/initDbDevelopment.sql
    @Value("${spring.flyway.locations}")
    private String springFlywayLocations;

    @Bean(initMethod = "migrate")
    public Flyway flyway(@Qualifier("masterDataSourceProperties") DataSourceProperties properties) {
        var flyway = Flyway.configure()
                .dataSource(properties.initializeDataSourceBuilder().build())
                .locations(springFlywayLocations)
                .load();
        return flyway;
    }
}
