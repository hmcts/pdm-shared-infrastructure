package uk.gov.hmcts.pdm.business.entities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.FlushModeType;
import javax.persistence.Persistence;

public class PdmEntityHelper {
    private static final String DATABASENAME = "PDDA"; // Change to pass in from a property somewhere
    private static final Logger LOG = LoggerFactory.getLogger(PdmEntityHelper.class);
    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY;

    static {
        try {
            ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory(DATABASENAME);

        } catch (RuntimeException ex) {
            LOG.error("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    protected PdmEntityHelper() {
        // Protected constructor
    }

    public static EntityManager getEntityManager() {
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        entityManager.setFlushMode(FlushModeType.AUTO);
        return entityManager;

    }

    // ---- Get repositories --- //



    // ---- End get repositories --- //

}
