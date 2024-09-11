package uk.gov.hmcts.pdm.publicdisplay.manager.web.courtroom;

import jakarta.validation.constraints.NotNull;
import uk.gov.hmcts.pdm.publicdisplay.manager.security.EncryptedFormat;

import java.io.Serializable;

public class CourtRoomSearchCommand implements Serializable {

    private static final long serialVersionUID = 807524632879168774L;
    private static final String COURTID = "CourtId :";
    private static final String NOVALUEPRESENT = "no value at present";

    /**
     * The ID of the Court.
     */
    @EncryptedFormat
    @NotNull(message = "{courtRoomSearchCommand.courtId.notNull}")
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
        final StringBuilder sb = new StringBuilder(30);

        // Use spring StringUtils to check strings have values
        sb.append(COURTID)
            .append((this.getCourtId() == null) ? NOVALUEPRESENT
                : this.getCourtId());
        return sb.toString();
    }

}
