package com.pdm.hb.jpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.FlushModeType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.gov.hmcts.pdm.publicdisplay.initialization.InitializationService;



public class EntityManagerUtil {
    private static final Logger LOG = LoggerFactory.getLogger(EntityManagerUtil.class);
    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY;
    
    static {
        try {
            ENTITY_MANAGER_FACTORY = InitializationService.getInstance().getEntityManagerFactory();

        } catch (RuntimeException ex) {
            LOG.error("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    protected EntityManagerUtil() {
        // Protected constructor
    }

    public static EntityManager getEntityManager() {
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        entityManager.setFlushMode(FlushModeType.AUTO);
        return entityManager;

    }
}
