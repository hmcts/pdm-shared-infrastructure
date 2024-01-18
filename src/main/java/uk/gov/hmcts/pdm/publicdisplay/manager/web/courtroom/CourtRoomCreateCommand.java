package uk.gov.hmcts.pdm.publicdisplay.manager.web.courtroom;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import uk.gov.hmcts.pdm.publicdisplay.manager.security.EncryptedFormat;

public class CourtRoomCreateCommand {

    private static final String NO = "N";
    
    /** The name field. */
    @NotBlank(message = "{courtRoomAmendCommand.name.notBlank}")
    private String name;

    /** The description of the court room. */
    @NotBlank(message = "{courtRoomAmendCommand.description.notBlank}")
    private String description;

    /** The ID of the CourtSite. */
    @EncryptedFormat
    @NotNull(message = "{courtRoomAmendCommand.xhibitCourtSiteId.notNull}")
    private Long xhibitCourtSiteId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getXhibitCourtSiteId() {
        return xhibitCourtSiteId;
    }

    public void setXhibitCourtSiteId(Long xhibitCourtSiteId) {
        this.xhibitCourtSiteId = xhibitCourtSiteId;
    }

    public String getDisplayName() {
        return description;
    }
    
    public String getObsInd() {
        return NO;
    }


}
