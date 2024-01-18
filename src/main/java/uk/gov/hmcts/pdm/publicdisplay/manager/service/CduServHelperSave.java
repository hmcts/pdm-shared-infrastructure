package uk.gov.hmcts.pdm.publicdisplay.manager.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.gov.hmcts.pdm.publicdisplay.manager.domain.api.ICduModel;
import uk.gov.hmcts.pdm.publicdisplay.manager.domain.api.IUrlModel;

public class CduServHelperSave extends CduServHelperFind {

    /**
     * Set up our logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(CduServHelperSave.class);

    /**
     * Save new cdu.
     *
     * @param cdu the cdu
     */
    protected void saveNewCdu(final ICduModel cdu) {
        final String methodName = "saveNewCdu";
        LOGGER.info(THREE_PARAMS, METHOD, methodName, STARTS);

        // Save the CDU in xhibit database
        getXhbDispMgrCduRepository().saveDaoFromBasicValue(cdu);

        // Save the CDU on localproxy
        LOGGER.debug(THREE_PARAMS, METHOD, methodName, " About to save cdu on localProxy");

        // TODO we need ipdmanager running for the below to work
        // Rest call to send cdu data
        if (localProxyCommunicationEnabled) {
            localProxyRestClient.saveCdu(cdu);
        }

        LOGGER.info(THREE_PARAMS, METHOD, methodName, ENDS);
    }

    /**
     * Save cdu.
     *
     * @param cdu the cdu
     */
    protected void saveCdu(final ICduModel cdu) {
        final String methodName = "saveCdu";
        LOGGER.info(THREE_PARAMS, METHOD, methodName, STARTS);

        // Save the CDU in xhibit database
        getXhbDispMgrCduRepository().updateDaoFromBasicValue(cdu);

        // Save the CDU on localproxy
        LOGGER.debug(THREE_PARAMS, METHOD, methodName, " About to save cdu on localProxy");

        // TODO we need ipdmanager running for the below to work
        // Rest call to send cdu data
        if (localProxyCommunicationEnabled) {
            localProxyRestClient.saveCdu(cdu);
        }

        LOGGER.info(THREE_PARAMS, METHOD, methodName, ENDS);
    }

    /**
     * Checks if is mapped.
     *
     * @param cdu the cdu
     * @param url the url
     * @return true, if is mapped
     */
    protected boolean isMapped(final ICduModel cdu, final IUrlModel url) {
        boolean isMapped = false;
        for (IUrlModel cduUrl : cdu.getUrls()) {
            if (cduUrl.getId().equals(url.getId())) {
                isMapped = true;
                break;
            }
        }
        return isMapped;
    }
}
