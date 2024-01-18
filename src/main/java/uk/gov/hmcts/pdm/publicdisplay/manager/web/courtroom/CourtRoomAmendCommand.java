package uk.gov.hmcts.pdm.publicdisplay.manager.web.courtroom;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import uk.gov.hmcts.pdm.publicdisplay.manager.security.EncryptedFormat;

public class CourtRoomAmendCommand {


    private static final String NO = "N";

    /** The ID of the CourtSite. */
    @EncryptedFormat
    @NotNull(message = "{courtRoomAmendCommand.xhibitCourtSiteId.notNull}")
    private Long xhibitCourtSiteId;

    /** The ID of the CourtRoom. */
    @EncryptedFormat
    @NotNull(message = "{courtRoomAmendCommand.courtRoomId.notNull}")
    private Integer courtRoomId;

    /** The name of the CourtRoom. */
    @NotBlank(message = "{courtRoomAmendCommand.name.notBlank}")
    private String name;
    
    /** The description of the CourtRoom. */
    @NotBlank(message = "{courtRoomAmendCommand.description.notBlank}")
    private String description;
    
    public Long getXhibitCourtSiteId() {
        return xhibitCourtSiteId;
    }

    public void setXhibitCourtSiteId(Long xhibitCourtSiteId) {
        this.xhibitCourtSiteId = xhibitCourtSiteId;
    }

    public Integer getCourtRoomId() {
        return courtRoomId;
    }

    public void setCourtRoomId(Integer courtRoomId) {
        this.courtRoomId = courtRoomId;
    }

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
    
    public String getDisplayName() {
        return getDescription();
    }

    public String getObsInd() {
        return NO;
    }
}
