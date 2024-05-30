package uk.gov.hmcts.pdm.publicdisplay.manager.web.courtel;

import jakarta.validation.constraints.Pattern;

public class CourtelAmendCommand {

    /** The courtelListAmount of the xhbConfigProp. */
    @Pattern(regexp = "^\\d+$", message = "{courtelAmendCommand.courtelListAmount.notNumber}")
    private String courtelListAmount;

    /** The courtelMaxRetry of the xhbConfigProp. */
    @Pattern(regexp = "^\\d+$", message = "{courtelAmendCommand.courtelMaxRetry.notNumber}")
    private String courtelMaxRetry;

    /** The courtelMaxRetry of the xhbConfigProp. */
    @Pattern(regexp = "^\\d+$", message = "{courtelAmendCommand.messageLookupDelay.notNumber}")
    private String messageLookupDelay;

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

    public String getMessageLookupDelay() {
        return messageLookupDelay;
    }

    public void setMessageLookupDelay(String messageLookupDelay) {
        this.messageLookupDelay = messageLookupDelay;
    }
}