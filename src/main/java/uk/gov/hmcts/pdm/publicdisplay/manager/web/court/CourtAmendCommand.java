package uk.gov.hmcts.pdm.publicdisplay.manager.web.court;

import jakarta.validation.constraints.NotNull;
import uk.gov.hmcts.pdm.publicdisplay.manager.security.EncryptedFormat;

public class CourtAmendCommand extends AbstractCourtCommand {

    /** The courtSiteId field. */
    @EncryptedFormat
    @NotNull(message = "{courtAmendCommand.xhibitCourtSiteId.notNull}")
    private Long xhibitCourtSiteId;
    
    /**
     * Gets the xhibitCourtSiteId.
     *
     * @return the xhibitCourtSiteId
     */
    public Long getXhibitCourtSiteId() {
        return xhibitCourtSiteId;
    }

    /**
     * Sets the xhibitCourtSiteId.
     *
     * @param xhibitCourtSiteId the xhibitCourtSiteId to set
     */
    public void setXhibitCourtSiteId(Long xhibitCourtSiteId) {
        this.xhibitCourtSiteId = xhibitCourtSiteId;
    }
}
