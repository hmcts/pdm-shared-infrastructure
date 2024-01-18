package com.pdm.hb.jpa;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.FlushModeType;
import javax.persistence.Persistence;



public class EntityManagerUtil {
    private static final Logger LOG = LoggerFactory.getLogger(EntityManagerUtil.class);
    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY;
    
    static {
        try {
            ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory("PDM");

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
