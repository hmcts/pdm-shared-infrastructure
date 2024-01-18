package uk.gov.hmcts.pdm.publicdisplay.manager.service;

import com.pdm.hb.jpa.EntityManagerUtil;
import uk.gov.hmcts.pdm.business.entities.xhbcourtsite.XhbCourtSiteRepository;
import uk.gov.hmcts.pdm.business.entities.xhbdispmgrcdu.XhbDispMgrCduRepository;
import uk.gov.hmcts.pdm.business.entities.xhbdispmgrlocalproxy.XhbDispMgrLocalProxyRepository;

import javax.persistence.EntityManager;

public class RagStatusRepository {

    private EntityManager entityManager;

    private XhbDispMgrCduRepository xhbDispMgrCduRepository;

    private XhbCourtSiteRepository xhbCourtSiteRepository;

    private XhbDispMgrLocalProxyRepository xhbDispMgrLocalProxyRepository;

    private EntityManager getEntityManager() {
        if (entityManager == null) {
            entityManager = EntityManagerUtil.getEntityManager();
        }
        return entityManager;
    }

    protected XhbCourtSiteRepository getXhbCourtSiteRepository() {
        if (xhbCourtSiteRepository == null) {
            xhbCourtSiteRepository = new XhbCourtSiteRepository(getEntityManager());
        }
        return xhbCourtSiteRepository;
    }

    protected XhbDispMgrCduRepository getXhbDispMgrCduRepository() {
        if (xhbDispMgrCduRepository == null) {
            xhbDispMgrCduRepository = new XhbDispMgrCduRepository(getEntityManager());
        }
        return xhbDispMgrCduRepository;
    }

    protected XhbDispMgrLocalProxyRepository getXhbDispMgrLocalProxyRepository() {
        if (xhbDispMgrLocalProxyRepository == null) {
            xhbDispMgrLocalProxyRepository = new XhbDispMgrLocalProxyRepository(getEntityManager());
        }
        return xhbDispMgrLocalProxyRepository;
    }

}
