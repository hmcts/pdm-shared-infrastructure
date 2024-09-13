package uk.gov.hmcts.pdm.publicdisplay.manager.web.court;

import uk.gov.hmcts.pdm.publicdisplay.common.util.AppConstants;
import uk.gov.hmcts.pdm.publicdisplay.manager.dto.XhibitCourtSiteDto;

public class CourtSiteValidator {

    /**
     * Checks if is registered court site.
     *
     * @param selectedCourtSite the selected court site
     * @return true, if is registered court site
     */
    public boolean isRegisteredCourtSite(final XhibitCourtSiteDto selectedCourtSite) {
        return selectedCourtSite != null
            && AppConstants.YES_CHAR.equals(selectedCourtSite.getRegisteredIndicator());
    }

}
