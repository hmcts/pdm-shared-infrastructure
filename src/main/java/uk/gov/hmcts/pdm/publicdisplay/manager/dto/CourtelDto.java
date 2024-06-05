package uk.gov.hmcts.pdm.publicdisplay.manager.dto;

import java.io.Serial;
import java.io.Serializable;

/**
 * Simple transfer object used to move courtel details around.
 *
 * @author ghafouria
 *
 */
public class CourtelDto implements Serializable {

    /**
     * SerialVersionUID of the CourtelDto class.
     */
    @Serial
    private static final long serialVersionUID = -3592709908530871747L;

    private String courtelListAmount;
    private String courtelMaxRetry;
    private String courtelMessageLookupDelay;

    public String getCourtelListAmount() {
        return courtelListAmount;
    }

    public void setCourtelListAmount(String courtelListAmount) {
        this.courtelListAmount = courtelListAmount;
    }

    public String getCourtelMaxRetry() {
        return courtelMaxRetry;
    }

    public void setCourtelMaxRetry(String courtelMaxRetry) {
        this.courtelMaxRetry = courtelMaxRetry;
    }

    public String getCourtelMessageLookupDelay() {
        return courtelMessageLookupDelay;
    }

    public void setCourtelMessageLookupDelay(String courtelMessageLookupDelay) {
        this.courtelMessageLookupDelay = courtelMessageLookupDelay;
    }
}
