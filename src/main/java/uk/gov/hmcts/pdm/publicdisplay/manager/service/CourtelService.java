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

        List<XhbConfigPropDao> xhbConfigPropDaos = getXhbConfigPropRepository().findByPropertyName(COURTEL_LIST_AMOUNT);

        if (xhbConfigPropDaos.isEmpty()) {
            XhbConfigPropDao xhbConfigPropDao = new XhbConfigPropDao();
            xhbConfigPropDao.setPropertyName(COURTEL_LIST_AMOUNT);
            xhbConfigPropDao.setPropertyValue(courtelAmendCommand.getCourtelListAmount());
            getXhbConfigPropRepository().saveDao(xhbConfigPropDao);
        } else {
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

        List<XhbConfigPropDao> xhbConfigPropDaos = getXhbConfigPropRepository().findByPropertyName(COURTEL_MAX_RETRY);

        if (xhbConfigPropDaos.isEmpty()) {
            XhbConfigPropDao xhbConfigPropDao = new XhbConfigPropDao();
            xhbConfigPropDao.setPropertyName(COURTEL_MAX_RETRY);
            xhbConfigPropDao.setPropertyValue(courtelAmendCommand.getCourtelMaxRetry());
            getXhbConfigPropRepository().saveDao(xhbConfigPropDao);
        } else {
            XhbConfigPropDao xhbConfigPropDao = xhbConfigPropDaos.get(0);
            xhbConfigPropDao.setPropertyValue(courtelAmendCommand.getCourtelMaxRetry());
            getXhbConfigPropRepository().updateDao(xhbConfigPropDao);
        }

        LOGGER.info(THREE_PARAMS, METHOD, methodName, ENDS);
    }



//    /**
//     * Gets the court sites.
//     *
//     * @return the court sites
//     */
//    @Override
//    public List<XhibitCourtSiteDto> getCourtSites() {
//        final String methodName = "getCourtSites";
//        LOGGER.info(THREE_PARAMS, METHOD, methodName, STARTS);
//        final List<XhibitCourtSiteDto> resultList = new ArrayList<>();
//        final List<XhbCourtSiteDao> xhibitCourtSiteList = getXhbCourtSiteRepository().findAll();
//        LOGGER.debug(FOUR_PARAMS, METHOD, methodName, " - Court sites returned : ",
//                xhibitCourtSiteList.size());
//
//        if (!xhibitCourtSiteList.isEmpty()) {
//            // Transfer each court site to a dto and save in resultList
//            for (XhbCourtSiteDao xhibitCourtSite : xhibitCourtSiteList) {
//                if (YES.equals(xhibitCourtSite.getObsInd())) {
//                    continue;
//                }
//                LOGGER.debug(THREE_PARAMS, METHOD, methodName, " - transferring court site to dto");
//                final XhibitCourtSiteDto dto = createXhibitCourtSiteDto();
//
//                // need the court site details from the main court site in 'xhb_court_site' table
//                dto.setId(xhibitCourtSite.getId().longValue());
//                dto.setCourtSiteName(xhibitCourtSite.getCourtSiteName());
//                dto.setCourtSiteCode(xhibitCourtSite.getCourtSiteCode());
//                dto.setCourtId(xhibitCourtSite.getCourtId());
//                LOGGER.debug("dto id : {}", dto.getId());
//                LOGGER.debug("dto courtSiteName: {}", dto.getCourtSiteName());
//                resultList.add(dto);
//            }
//            // Sort by name
//            Collections.sort(resultList, (obj1, obj2) -> String.CASE_INSENSITIVE_ORDER
//                    .compare(obj1.getCourtSiteName(), obj2.getCourtSiteName()));
//        }
//        LOGGER.info(THREE_PARAMS, METHOD, methodName, ENDS);
//        return resultList;
//    }

    @Secured(UserRole.ROLE_ADMIN_VALUE)
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    @Override
    public void updateMessageLookupDelay(CourtelAmendCommand courtelAmendCommand) {
        final String methodName = "updateMessageLookupDelay";
        LOGGER.info(THREE_PARAMS, METHOD, methodName, STARTS);

        List<XhbConfigPropDao> xhbConfigPropDaos =
                getXhbConfigPropRepository().findByPropertyName(COURTEL_MESSAGE_LOOKUP_DELAY);

        if (xhbConfigPropDaos.isEmpty()) {
            XhbConfigPropDao xhbConfigPropDao = new XhbConfigPropDao();
            xhbConfigPropDao.setPropertyName(COURTEL_MESSAGE_LOOKUP_DELAY);
            xhbConfigPropDao.setPropertyValue(courtelAmendCommand.getMessageLookupDelay());
            getXhbConfigPropRepository().saveDao(xhbConfigPropDao);
        } else {
            XhbConfigPropDao xhbConfigPropDao = xhbConfigPropDaos.get(0);
            xhbConfigPropDao.setPropertyValue(courtelAmendCommand.getMessageLookupDelay());
            getXhbConfigPropRepository().updateDao(xhbConfigPropDao);
        }
        LOGGER.info(THREE_PARAMS, METHOD, methodName, ENDS);
    }

    

    @Secured(UserRole.ROLE_ADMIN_VALUE)
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    @Override
    public CourtelDto getCourtelPropertyValues() {
        final String methodName = "getCourtelProperties";
        LOGGER.info(THREE_PARAMS, METHOD, methodName, STARTS);

        List<XhbConfigPropDao> courtelListAmountDao =
                getXhbConfigPropRepository().findByPropertyName(COURTEL_LIST_AMOUNT);
        List<XhbConfigPropDao> courtelMaxRetryDao=
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
