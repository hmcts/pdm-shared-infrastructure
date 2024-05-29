package uk.gov.hmcts.pdm.publicdisplay.manager.service;

import com.pdm.hb.jpa.EntityManagerUtil;
import jakarta.persistence.EntityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import uk.gov.hmcts.pdm.business.entities.xhbconfigprop.XhbConfigPropDao;
import uk.gov.hmcts.pdm.business.entities.xhbconfigprop.XhbConfigPropRepository;
import uk.gov.hmcts.pdm.publicdisplay.manager.security.UserRole;
import uk.gov.hmcts.pdm.publicdisplay.manager.service.api.ICourtelService;
import uk.gov.hmcts.pdm.publicdisplay.manager.web.courtel.CourtelAmendCommand;

import java.util.List;

@Transactional
@Component
public class CourtelService implements ICourtelService {

    private XhbConfigPropRepository xhbConfigPropRepository;

    private EntityManager entityManager;
    private static final Logger LOGGER = LoggerFactory.getLogger(CourtelService.class);
    private static final String METHOD = "Method ";
    private static final String THREE_PARAMS = "{}{}{}";
    private static final String STARTS = " - starts";
    private static final String ENDS = " - ends";
    private static final String COURTEL_LIST_AMOUNT = "COURTEL_LIST_AMOUNT";
    private static final String COURTEL_MAX_RETRY = "COURTEL_MAX_RETRY";
    private static final String COURTEL_MESSAGE_LOOKUP_DELAY = "MESSAGE_LOOKUP_DELAY";

    @Secured(UserRole.ROLE_ADMIN_VALUE)
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    @Override
    public void updateCourtelListAmount(CourtelAmendCommand courtelAmendCommand) {

        final String methodName = "updateCourtelListAmount";
        LOGGER.info(THREE_PARAMS, METHOD, methodName, STARTS);

        List<XhbConfigPropDao> xhbConfigPropDaos =
                getXhbConfigPropRepository().findByPropertyName(COURTEL_LIST_AMOUNT);

        if (!xhbConfigPropDaos.isEmpty()) {
            XhbConfigPropDao xhbConfigPropDao = xhbConfigPropDaos.get(0);
            xhbConfigPropDao.setPropertyValue(courtelAmendCommand.getCourtelListAmount());
            getXhbConfigPropRepository().updateDao(xhbConfigPropDao);
        }
        LOGGER.info(THREE_PARAMS, METHOD, methodName, ENDS);
    }

    @Secured(UserRole.ROLE_ADMIN_VALUE)
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    @Override
    public void updateCourtelMaxRetry(CourtelAmendCommand courtelAmendCommand) {
        final String methodName = "updateCourtelMaxRetry";
        LOGGER.info(THREE_PARAMS, METHOD, methodName, STARTS);

        List<XhbConfigPropDao> xhbConfigPropDaos =
                getXhbConfigPropRepository().findByPropertyName(COURTEL_MAX_RETRY);

        if (!xhbConfigPropDaos.isEmpty()) {
            XhbConfigPropDao xhbConfigPropDao = xhbConfigPropDaos.get(0);
            xhbConfigPropDao.setPropertyValue(courtelAmendCommand.getCourtelMaxRetry());
            getXhbConfigPropRepository().updateDao(xhbConfigPropDao);
        }
        LOGGER.info(THREE_PARAMS, METHOD, methodName, ENDS);
    }

    @Secured(UserRole.ROLE_ADMIN_VALUE)
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    @Override
    public void updateMessageLookupDelay(CourtelAmendCommand courtelAmendCommand) {
        final String methodName = "updateMessageLookupDelay";
        LOGGER.info(THREE_PARAMS, METHOD, methodName, STARTS);

        List<XhbConfigPropDao> xhbConfigPropDaos =
                getXhbConfigPropRepository().findByPropertyName(COURTEL_MESSAGE_LOOKUP_DELAY);

        if (!xhbConfigPropDaos.isEmpty()) {
            XhbConfigPropDao xhbConfigPropDao = xhbConfigPropDaos.get(0);
            xhbConfigPropDao.setPropertyValue(courtelAmendCommand.getMessageLookupDelay());
            getXhbConfigPropRepository().updateDao(xhbConfigPropDao);
        }
        LOGGER.info(THREE_PARAMS, METHOD, methodName, ENDS);
    }

    protected EntityManager getEntityManager() {
        if (entityManager == null) {
            entityManager = EntityManagerUtil.getEntityManager();
        }
        return entityManager;
    }

    protected XhbConfigPropRepository getXhbConfigPropRepository() {
        if (xhbConfigPropRepository == null) {
            xhbConfigPropRepository = new XhbConfigPropRepository(getEntityManager());
        }
        return xhbConfigPropRepository;
    }
}
