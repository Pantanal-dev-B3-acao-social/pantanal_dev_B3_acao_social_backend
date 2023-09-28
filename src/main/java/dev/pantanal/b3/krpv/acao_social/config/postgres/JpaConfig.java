//package dev.pantanal.b3.krpv.acao_social.config.postgres;
//
//import jakarta.persistence.EntityManager;
//import jakarta.persistence.EntityManagerFactory;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
//import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
//
//import javax.sql.DataSource;
//
//@Configuration
////@DependsOn("masterDataSourceConfig")
////@Order(2)
//public class JpaConfig {
//
//    @Bean
//    public LocalContainerEntityManagerFactoryBean entityManagerFactory(
//            @Qualifier("dataSource")
//            DataSource dataSource
//    ) {
//        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
//        em.setDataSource(dataSource);
//        em.setPackagesToScan("dev.pantanal.b3.krpv.acao_social.modulos"); // pacote das suas entidades
//        /**
//        *  definindo o nome da unidade de persistência JPA.
//        * A unidade de persistência é uma configuração global que contém informações sobre como o JPA deve se comportar em relação ao banco de dados.
//        */
//        em.setPersistenceUnitName("unidadePersistencia1");
//        /**
//         * Configura o Hibernate como o provedor JPA
//        */
//        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
//        em.setJpaVendorAdapter(vendorAdapter);
//
//        return em;
//    }
//
//    @Bean
//    public EntityManager entityManager(EntityManagerFactory entityManagerFactory) {
//        return entityManagerFactory.createEntityManager();
//    }
//}
