package uk.gov.hmcts.pdm.publicdisplay.manager.service;

import com.pdm.hb.jpa.EntityManagerUtil;
import uk.gov.hmcts.pdm.business.entities.xhbdispmgrhousekeeping.XhbDispMgrHousekeepingRepository;

import javax.persistence.EntityManager;

public class HousekeepingRepository {

    private EntityManager entityManager;
    
    private XhbDispMgrHousekeepingRepository xhbDispMgrHousekeepingRepository;

    private EntityManager getEntityManager() {
        if (entityManager == null) {
            entityManager = EntityManagerUtil.getEntityManager();
        }
        return entityManager;
    }
    
    protected XhbDispMgrHousekeepingRepository getXhbDispMgrHousekeepingRepository() {
        if (xhbDispMgrHousekeepingRepository == null) {
            xhbDispMgrHousekeepingRepository = new XhbDispMgrHousekeepingRepository(getEntityManager());
        }
        return xhbDispMgrHousekeepingRepository;
    }

}
