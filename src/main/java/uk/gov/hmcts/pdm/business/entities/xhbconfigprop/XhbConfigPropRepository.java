package uk.gov.hmcts.pdm.business.entities.xhbconfigprop;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import uk.gov.hmcts.pdm.business.entities.AbstractRepository;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@Repository
public class XhbConfigPropRepository extends AbstractRepository<XhbConfigPropDao> implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final Logger LOG = LoggerFactory.getLogger(XhbConfigPropRepository.class);
    private static final String METHOD = "Method ";
    private static final String THREE_PARAMS = "{}{}{}";
    private static final String STARTS = " - starts";
    private static final String ENDS = " - ends";

    public XhbConfigPropRepository(EntityManager em) {
        super(em);
    }

    @Override
    public Class<XhbConfigPropDao> getDaoClass() {
        return XhbConfigPropDao.class;
    }

    /**
     * findByPropertyName.
     * @param propertyName String
     * @return List
     */
    @SuppressWarnings("unchecked")
    public List<XhbConfigPropDao> findByPropertyName(String propertyName) {
        LOG.debug("findByPropertyName()");
        Query query = getEntityManager().createNamedQuery("XHB_CONFIG_PROP.findByPropertyName");
        query.setParameter("propertyName", propertyName);
        return query.getResultList();
    }

    /**
     * updateDao.
     *
     * @param dao XhbConfigPropDao
     * @return Optional XhbConfigPropDao
     */
    public Optional<XhbConfigPropDao> updateDao(XhbConfigPropDao dao) {
        final String methodName = "save";
        LOG.debug(THREE_PARAMS, METHOD, methodName, STARTS);

        Optional<XhbConfigPropDao> result = super.update(dao);

        clearEntityManager();

        LOG.debug(THREE_PARAMS, METHOD, methodName, ENDS);
        return result;
    }

    /**
     * saveDao.
     *
     * @param dao XhbConfigPropDao
     */
    public void saveDao(XhbConfigPropDao dao) {
        final String methodName = "save";
        LOG.debug(THREE_PARAMS, METHOD, methodName, STARTS);

        super.save(dao);

        clearEntityManager();

        LOG.debug(THREE_PARAMS, METHOD, methodName, ENDS);
    }
}
