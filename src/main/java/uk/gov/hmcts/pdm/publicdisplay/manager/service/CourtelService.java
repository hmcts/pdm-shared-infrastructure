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
import uk.gov.hmcts.pdm.publicdisplay.manager.dto.CourtelDto;
import uk.gov.hmcts.pdm.publicdisplay.manager.security.UserRole;
import uk.gov.hmcts.pdm.publicdisplay.manager.service.api.ICourtelService;
import uk.gov.hmcts.pdm.publicdisplay.manager.web.courtel.CourtelAmendCommand;

import java.util.List;

@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
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
    public CourtelDto getCourtelPropertyValues() {
        final String methodName = "getCourtelProperties";
        LOGGER.info(THREE_PARAMS, METHOD, methodName, STARTS);

        List<XhbConfigPropDao> courtelListAmountDao =
                getXhbConfigPropRepository().findByPropertyName(COURTEL_LIST_AMOUNT);
        List<XhbConfigPropDao> courtelMaxRetryDao =
                getXhbConfigPropRepository().findByPropertyName(COURTEL_MAX_RETRY);
        List<XhbConfigPropDao> courtelMessageLookupDelayDao =
                getXhbConfigPropRepository().findByPropertyName(COURTEL_MESSAGE_LOOKUP_DELAY);

        final CourtelDto courtelDto = new CourtelDto();

        courtelDto.setCourtelListAmount(courtelListAmountDao.get(0).getPropertyValue());
        courtelDto.setCourtelMaxRetry(courtelMaxRetryDao.get(0).getPropertyValue());
        courtelDto.setCourtelMessageLookupDelay(courtelMessageLookupDelayDao.get(0).getPropertyValue());

        LOGGER.info(THREE_PARAMS, METHOD, methodName, ENDS);
        return courtelDto;
    }

    @Secured(UserRole.ROLE_ADMIN_VALUE)
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    @Override
    public void updateCourtelProperties(CourtelAmendCommand courtelAmendCommand) {
        updateProperty(COURTEL_LIST_AMOUNT, courtelAmendCommand.getCourtelListAmount());
        updateProperty(COURTEL_MAX_RETRY, courtelAmendCommand.getCourtelMaxRetry());
        updateProperty(COURTEL_MESSAGE_LOOKUP_DELAY, courtelAmendCommand.getMessageLookupDelay());
    }

    public void updateProperty(String propertyName, String propertyValue) {
        final String methodName = "updateProperty";
        LOGGER.info(THREE_PARAMS, METHOD, methodName, STARTS);

        List<XhbConfigPropDao> xhbConfigPropDaos =
                getXhbConfigPropRepository().findByPropertyName(propertyName);

        if (xhbConfigPropDaos.isEmpty()) {
            XhbConfigPropDao xhbConfigPropDao = new XhbConfigPropDao();
            xhbConfigPropDao.setPropertyName(propertyName);
            xhbConfigPropDao.setPropertyValue(propertyValue);
            getXhbConfigPropRepository().saveDao(xhbConfigPropDao);
        } else {
            XhbConfigPropDao xhbConfigPropDao = xhbConfigPropDaos.get(0);
            xhbConfigPropDao.setPropertyValue(propertyValue);
            getXhbConfigPropRepository().updateDao(xhbConfigPropDao);
        }
        LOGGER.info(THREE_PARAMS, METHOD, methodName, ENDS);

    }

    private EntityManager getEntityManager() {
        if (entityManager == null) {
            entityManager = EntityManagerUtil.getEntityManager();
        }
        return entityManager;
    }

    private XhbConfigPropRepository getXhbConfigPropRepository() {
        if (xhbConfigPropRepository == null) {
            xhbConfigPropRepository = new XhbConfigPropRepository(getEntityManager());
        }
        return xhbConfigPropRepository;
    }
}
