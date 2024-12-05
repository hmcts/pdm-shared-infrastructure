package uk.gov.hmcts.pdm.publicdisplay.manager.service;

import com.pdm.hb.jpa.EntityManagerUtil;
import com.pdm.hb.jpa.RepositoryUtil;
import jakarta.persistence.EntityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import uk.gov.hmcts.pdm.business.entities.xhbcourtsite.XhbCourtSiteRepository;
import uk.gov.hmcts.pdm.business.entities.xhbrefsystemcode.XhbRefSystemCodeRepository;

@Component
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class RefJudgeTypeServiceFinder extends RefJudgeTypeServiceCreator {

    private EntityManager entityManager;

    private XhbCourtSiteRepository xhbCourtSiteRepository;
    private XhbRefSystemCodeRepository xhbRefSystemCodeRepository;

    /**
     * Set up our logger.
     */
    protected static final Logger LOGGER = LoggerFactory.getLogger(RefJudgeTypeServiceFinder.class);
    
    protected void clearRepositories() {
        xhbCourtSiteRepository = null;
        xhbRefSystemCodeRepository = null;
    }

    protected EntityManager getEntityManager() {
        if (!EntityManagerUtil.isEntityManagerActive(entityManager)) {
            clearRepositories();
            entityManager = EntityManagerUtil.getEntityManager();
        }
        return entityManager;
    }

    protected XhbCourtSiteRepository getXhbCourtSiteRepository() {
        if (!RepositoryUtil.isRepositoryActive(xhbCourtSiteRepository)) {
            xhbCourtSiteRepository = new XhbCourtSiteRepository(getEntityManager());
        }
        return xhbCourtSiteRepository;
    }
    
    protected XhbRefSystemCodeRepository getXhbRefSystemCodeRepository() {
        if (!RepositoryUtil.isRepositoryActive(xhbRefSystemCodeRepository)) {
            xhbRefSystemCodeRepository = new XhbRefSystemCodeRepository(getEntityManager());
        }
        return xhbRefSystemCodeRepository;
    }
}
