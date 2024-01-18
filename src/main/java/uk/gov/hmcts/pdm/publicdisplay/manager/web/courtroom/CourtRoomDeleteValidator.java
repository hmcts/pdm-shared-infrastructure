package uk.gov.hmcts.pdm.publicdisplay.manager.web.courtroom;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component
public class CourtRoomDeleteValidator extends AbstractCourtRoomValidator {

    /** The logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger(CourtRoomDeleteValidator.class);

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.validation.Validator#supports(java.lang.Class)
     */
    @Override
    public boolean supports(final Class<?> clazz) {
        return CourtRoomDeleteCommand.class.isAssignableFrom(clazz);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.validation.Validator#validate(java.lang.Object,
     * org.springframework.validation.Errors)
     */
    @Override
    public void validate(final Object command, final Errors errors) {
        LOGGER.info("validate method starts");

        final CourtRoomDeleteCommand courtRoomDeleteCommand = (CourtRoomDeleteCommand) command;
        if (courtRoomDeleteCommand.getXhibitCourtSiteId() == null) {
            LOGGER.warn("validate method - Invalid court site selected");
            errors.reject("courtRoomAmendCommand.xhibitCourtSiteId.notNull");
        } else if (courtRoomDeleteCommand.getCourtRoomId() == null) {
            LOGGER.warn("validate method - Invalid court room selected");
            errors.reject("courtRoomAmendCommand.courtRoomId.notNull");
        }

        LOGGER.info("validate method ends");
    }

}
