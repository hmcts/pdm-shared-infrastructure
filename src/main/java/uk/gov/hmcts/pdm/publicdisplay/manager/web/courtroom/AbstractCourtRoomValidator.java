package uk.gov.hmcts.pdm.publicdisplay.manager.web.courtroom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Validator;
import uk.gov.hmcts.pdm.publicdisplay.manager.dto.XhibitCourtSiteDto;
import uk.gov.hmcts.pdm.publicdisplay.manager.web.court.CourtSiteValidator;

import java.util.List;

/**
 * The Class AbstractCourtRoomValidator.
 * 
 * @author ghafouria
 *
 */
public abstract class AbstractCourtRoomValidator extends CourtSiteValidator implements Validator {

    /** The courtroom page state holder. */
    @Autowired
    private CourtRoomPageStateHolder courtRoomPageStateHolder;

    /**
     * Gets the courtroom page state holder.
     *
     * @return the courtroom page state holder
     */
    protected CourtRoomPageStateHolder getCourtRoomPageStateHolder() {
        return courtRoomPageStateHolder;
    }

    /**
     * Xhibit court site id in the valid list of sites.
     *
     * @param xhibitCourtSiteId the xhibit court site id
     * @return true, if successful
     */
    protected boolean isCourtSiteValid(final Long xhibitCourtSiteId) {
        final XhibitCourtSiteDto selectedCourtSite =
            getCourtSiteFromSearchResults(xhibitCourtSiteId);
        return selectedCourtSite != null;
    }

    /**
     * Checks if is registered court site selected.
     *
     * @param xhibitCourtSiteId the xhibit court site id
     * @return true, if is registered court site selected
     */
    protected boolean isRegisteredCourtSiteSelected(final Long xhibitCourtSiteId) {
        final XhibitCourtSiteDto selectedCourtSite =
            getCourtSiteFromSearchResults(xhibitCourtSiteId);
        return isRegisteredCourtSite(selectedCourtSite);
    }


    /**
     * Gets the court site from search results.
     *
     * @param xhibitCourtSiteId the xhibit court site id
     * @return the court site from search results
     */
    private XhibitCourtSiteDto getCourtSiteFromSearchResults(final Long xhibitCourtSiteId) {
        XhibitCourtSiteDto selectedCourtSite = null;
        final List<XhibitCourtSiteDto> courtSiteList = courtRoomPageStateHolder.getSites();
        if (courtSiteList != null) {
            for (XhibitCourtSiteDto courtSite : courtSiteList) {
                if (courtSite.getId().equals(xhibitCourtSiteId)) {
                    selectedCourtSite = courtSite;
                    break;
                }
            }
        }
        return selectedCourtSite;
    }

}
