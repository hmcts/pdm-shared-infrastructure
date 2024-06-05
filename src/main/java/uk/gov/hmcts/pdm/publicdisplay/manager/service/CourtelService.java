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
    private static final String FIVE = "5";
    private static final String SIXTY = "60";


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

        if (courtelListAmountDao.isEmpty()) {
            writeDefaultXhbConfigPropValues(COURTEL_LIST_AMOUNT, FIVE);
            courtelDto.setCourtelListAmount(FIVE);
        } else {
            courtelDto.setCourtelListAmount(courtelListAmountDao.get(0).getPropertyValue());
        }

        if (courtelMaxRetryDao.isEmpty()) {
            writeDefaultXhbConfigPropValues(COURTEL_MAX_RETRY, FIVE);
            courtelDto.setCourtelMaxRetry(FIVE);
        } else {
            courtelDto.setCourtelMaxRetry(courtelMaxRetryDao.get(0).getPropertyValue());
        }

        if (courtelMessageLookupDelayDao.isEmpty()) {
            writeDefaultXhbConfigPropValues(COURTEL_MESSAGE_LOOKUP_DELAY, SIXTY);
            courtelDto.setCourtelMessageLookupDelay(SIXTY);
        } else {
            courtelDto.setCourtelMessageLookupDelay(courtelMessageLookupDelayDao.get(0).getPropertyValue());
        }

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

        XhbConfigPropDao xhbConfigPropDao = xhbConfigPropDaos.get(0);
        xhbConfigPropDao.setPropertyValue(propertyValue);
        getXhbConfigPropRepository().updateDao(xhbConfigPropDao);

        LOGGER.info(THREE_PARAMS, METHOD, methodName, ENDS);

    }

    public void writeDefaultXhbConfigPropValues(String propName, String defaultValue) {
        XhbConfigPropDao xhbConfigPropDaoListAmount = new XhbConfigPropDao();
        xhbConfigPropDaoListAmount.setPropertyName(propName);
        xhbConfigPropDaoListAmount.setPropertyValue(defaultValue);
        xhbConfigPropRepository.saveDao(xhbConfigPropDaoListAmount);
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
