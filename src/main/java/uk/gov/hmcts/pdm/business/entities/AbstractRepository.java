package uk.gov.hmcts.pdm.business.entities;

import com.pdm.hb.jpa.EntityManagerUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;





public abstract class AbstractRepository<T extends AbstractDao> {

    private static final Logger LOG = LoggerFactory.getLogger(AbstractRepository.class);
    private static final String ERROR = "Error: {}";

    @PersistenceContext
    private final EntityManager entityManager;

    protected AbstractRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    protected abstract Class<T> getDaoClass();

    /**
     * findById.
     * 
     * @param id Integer
     * @return dao
     */
    public Optional<T> findById(Integer id) {
        T dao = getEntityManager().find(getDaoClass(), id);
        return dao != null ? (Optional<T>) Optional.of(dao) : Optional.empty();
    }

    /**
     * findById.
     * 
     * @param id Long
     * @return dao
     */
    public Optional<T> findById(Long id) {
        T dao = getEntityManager().find(getDaoClass(), id);
        return dao != null ? Optional.of(dao) : Optional.empty();
    }


    /**
     * findAll.
     * 
     * @return List
     */
    @SuppressWarnings("unchecked")
    public List<T> findAll() {
        Query query = getEntityManager().createQuery(getFindAllQuery(getDaoClass().getName()));
        return query.getResultList();
    }
    
    
    private String getFindAllQuery(String className) {
        return "from " + className;
    }



    /**
     * save.
     * 
     * @param dao T
     */
    public void save(T dao) {
        EntityManager localEntityManager = null;
        try {
            LOG.debug("Save()");
            localEntityManager = createEntityManager();
            localEntityManager.getTransaction().begin();
            localEntityManager.persist(dao);
            localEntityManager.getTransaction().commit();
        } catch (Exception e) {
            LOG.error(ERROR, e.getMessage());
            if (localEntityManager != null && localEntityManager.getTransaction().isActive()) {
                localEntityManager.getTransaction().rollback();
            }
            throw e;
        } finally {
            if (localEntityManager != null) {
                localEntityManager.close();
            }
        }
    }

    /**
     * update.
     * 
     * @param dao T
     * @return dao
     */
    public Optional<T> update(T dao) {
        EntityManager localEntityManager = null;
        try {
            LOG.debug("Update()");
            localEntityManager = createEntityManager();
            localEntityManager.getTransaction().begin();
            T mergedDao = localEntityManager.merge(dao);
            localEntityManager.getTransaction().commit();
            mergedDao.setVersion(mergedDao.getVersion() != null ? mergedDao.getVersion() + 1 : 1);
            return Optional.of(mergedDao);
        } catch (Exception e) {
            LOG.error(ERROR, e.getMessage());
            if (localEntityManager != null && localEntityManager.getTransaction().isActive()) {
                localEntityManager.getTransaction().rollback();
            }
        } finally {
            if (localEntityManager != null) {
                localEntityManager.close();
            }
        }
        return Optional.empty();
    }

    /**
     * delete.
     * 
     * @param dao Optional
     */
    public void delete(Optional<T> dao) {
        EntityManager localEntityManager = null;
        try {
            LOG.debug("delete()");
            if (dao.isPresent()) {
                localEntityManager = createEntityManager();
                localEntityManager.getTransaction().begin();
                T mergedDao = localEntityManager.merge(dao.get());
                localEntityManager.remove(mergedDao);
                localEntityManager.getTransaction().commit();
            }
        } catch (Exception e) {
            LOG.error(ERROR, e.getMessage());
            if (localEntityManager != null && localEntityManager.getTransaction().isActive()) {
                localEntityManager.getTransaction().rollback();
            }
        } finally {
            if (localEntityManager != null) {
                localEntityManager.close();
            }
        }
    }

    /*
     * Main EntityManager for reads, etc
     */
    protected EntityManager getEntityManager() {
        return entityManager;
    }

    /*
     * Create local one off EntityManager for save, update, delete
     */
    private EntityManager createEntityManager() {
        return EntityManagerUtil.getEntityManager();
    }

    public void clearEntityManager() {
        EntityManager em = getEntityManager();
        em.clear();
    }
}
