package uk.gov.hmcts.pdm.publicdisplay.manager.web.court;

import jakarta.validation.constraints.NotNull;
import uk.gov.hmcts.pdm.publicdisplay.manager.security.EncryptedFormat;
import uk.gov.hmcts.pdm.publicdisplay.manager.util.CommandUtil;

import java.io.Serializable;

public class CourtSearchCommand implements Serializable {

    private static final long serialVersionUID = 5441702968664948169L;

    /**
     * The ID of the Court.
     */
    @EncryptedFormat
    @NotNull(message = "{courtSearchCommand.courtId.notNull}")
    private Integer courtId;

    /**
     * getCourtId.
     * 
     * @return the courtId
     */
    public Integer getCourtId() {
        return courtId;
    }

    /**
     * setCourtId.
     * 
     * @param courtId the courtId to set.
     */
    public void setCourtId(final Integer courtId) {
        this.courtId = courtId;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return CommandUtil.courtCommandToString(this.getCourtId());
    }

}
