//package dev.pantanal.b3.krpv.acao_social.config.postgres;
//
//import org.flywaydb.core.Flyway;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.DependsOn;
//import org.springframework.core.annotation.Order;
//import org.springframework.core.io.Resource;
//import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
//import org.springframework.core.io.support.ResourcePatternResolver;
//import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
//import javax.sql.DataSource;
//import java.io.IOException;
//
//@Configuration
////@DependsOn("jpaConfig")
////@Order(3)
//public class FlywayConfig {
//
////    db/script/initDbDevelopment.sql
//    @Value("${spring.flyway.locations}")
//    private String springFlywayLocations;
//
//    @Bean(initMethod = "migrate")
//    public Flyway flyway(
////            @Qualifier("masterDataSourceProperties")
//            DataSourceProperties properties
//    ) {
//        var flyway = Flyway.configure()
//                .dataSource(properties.initializeDataSourceBuilder().build())
//                .locations(springFlywayLocations)
//                .load();
//        return flyway;
//    }
//
//}
