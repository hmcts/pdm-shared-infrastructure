package uk.gov.hmcts.pdm.publicdisplay.manager.service;

import com.pdm.hb.jpa.EntityManagerUtil;
import com.pdm.hb.jpa.RepositoryUtil;
import jakarta.persistence.EntityManager;
import uk.gov.hmcts.pdm.business.entities.xhbdispmgrcdu.XhbDispMgrCduRepository;
import uk.gov.hmcts.pdm.business.entities.xhbdispmgrcourtsite.XhbDispMgrCourtSiteRepository;
import uk.gov.hmcts.pdm.business.entities.xhbdispmgrlocalproxy.XhbDispMgrLocalProxyRepository;

public class RagStatusRepository {

    private EntityManager entityManager;

    private XhbDispMgrCduRepository xhbDispMgrCduRepository;

    private XhbDispMgrCourtSiteRepository xhbDispMgrCourtSiteRepository;

    private XhbDispMgrLocalProxyRepository xhbDispMgrLocalProxyRepository;

    protected EntityManager getEntityManager() {
        if (!EntityManagerUtil.isEntityManagerActive(entityManager)) {
            clearRepositories();
            entityManager = EntityManagerUtil.getEntityManager();
        }
        return entityManager;
    }
    
    protected void clearRepositories() {
        xhbDispMgrCduRepository = null;
        xhbDispMgrCourtSiteRepository = null;
        xhbDispMgrLocalProxyRepository = null;
    }

    protected XhbDispMgrCourtSiteRepository getXhbDispMgrCourtSiteRepository() {
        if (!RepositoryUtil.isRepositoryActive(xhbDispMgrCourtSiteRepository)) {
            xhbDispMgrCourtSiteRepository = new XhbDispMgrCourtSiteRepository(getEntityManager());
        }
        return xhbDispMgrCourtSiteRepository;
    }

    protected XhbDispMgrCduRepository getXhbDispMgrCduRepository() {
        if (!RepositoryUtil.isRepositoryActive(xhbDispMgrCduRepository)) {
            xhbDispMgrCduRepository = new XhbDispMgrCduRepository(getEntityManager());
        }
        return xhbDispMgrCduRepository;
    }

    protected XhbDispMgrLocalProxyRepository getXhbDispMgrLocalProxyRepository() {
        if (!RepositoryUtil.isRepositoryActive(xhbDispMgrLocalProxyRepository)) {
            xhbDispMgrLocalProxyRepository = new XhbDispMgrLocalProxyRepository(getEntityManager());
        }
        return xhbDispMgrLocalProxyRepository;
    }

}
