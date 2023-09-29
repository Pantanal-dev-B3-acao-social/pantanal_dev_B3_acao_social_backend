package dev.pantanal.b3.krpv.acao_social.utils;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class FindRegisterRandom<T> {

//    @Autowired
//    @Qualifier("unidadePersistencia1")
//    private EntityManagerFactory entityManagerFactory;

    private final EntityManager entityManager;

//    @Autowired
    public FindRegisterRandom (EntityManager entityManager) {
//        this.entityManager = this.entityManagerFactory();
        this.entityManager = entityManager;
    }

    public List<T> execute(String table, int amount, Class<T> entityType) {
        String sql = "SELECT * FROM " + table + " ORDER BY RANDOM() LIMIT " + amount;
        List<T> registers = entityManager.createNativeQuery(sql, entityType).getResultList();
        return registers;
    }

}
