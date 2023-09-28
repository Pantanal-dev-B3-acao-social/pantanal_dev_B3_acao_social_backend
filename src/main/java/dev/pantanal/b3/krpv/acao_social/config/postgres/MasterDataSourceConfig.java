//package dev.pantanal.b3.krpv.acao_social.config.postgres;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//import org.springframework.core.annotation.Order;
//
//@Configuration
////@Order(1)
//public class MasterDataSourceConfig {
//
//    @Value("${spring.datasource.url}")
//    private String datasourceUrl;
//
//    @Value("${spring.datasource.username}")
//    private String datasourceUsername;
//
//    @Value("${spring.datasource.password}")
//    private String datasourcePassword;
//
//    @Value("${spring.datasource.driver-class-name}")
//    private String datasourceDriverClassName;
//
//    @Primary
//    @Bean
//    public DataSourceProperties masterDataSourceProperties() {
//        DataSourceProperties dataSource = new DataSourceProperties();
//        dataSource.setUrl(datasourceUrl);
//        dataSource.setUsername(datasourceUsername);
//        dataSource.setPassword(datasourcePassword);
//        dataSource.setDriverClassName(datasourceDriverClassName);
//        return dataSource;
//    }
//}
