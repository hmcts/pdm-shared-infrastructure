package uk.gov.hmcts.pdm.publicdisplay.manager.web.court;

import org.hibernate.validator.constraints.NotBlank;

public class AbstractCourtCommand {
    
    private static final String NO = "N";

    /** The courtSiteName field. */
    @NotBlank(message = "{courtAmendCommand.courtSiteName.notBlank}")
    private String courtSiteName;

    /** The courtSiteCode field. */
    @NotBlank(message = "{courtAmendCommand.courtSiteCode.notBlank}")
    private String courtSiteCode;

    /**
     * Gets the courtSiteName.
     *
     * @return the courtSiteName
     */
    public String getCourtSiteName() {
        return courtSiteName;
    }

    /**
     * Sets the courtSiteName.
     *
     * @param courtSiteName the courtSiteName to set
     */
    public void setCourtSiteName(String courtSiteName) {
        this.courtSiteName = courtSiteName;
    }

    /**
     * Gets the courtSiteCode.
     *
     * @return the courtSiteCode
     */
    public String getCourtSiteCode() {
        return courtSiteCode;
    }

    /**
     * Sets the courtSiteCode.
     *
     * @param courtSiteCode the courtSiteCode to set
     */
    public void setCourtSiteCode(String courtSiteCode) {
        this.courtSiteCode = courtSiteCode;
    }

    public String getObsInd() {
        return NO;
    }
}
