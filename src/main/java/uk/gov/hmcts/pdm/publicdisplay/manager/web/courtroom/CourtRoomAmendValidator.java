package uk.gov.hmcts.pdm.publicdisplay.manager.web.courtroom;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component
public class CourtRoomAmendValidator extends AbstractCourtRoomValidator {

    /** The logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger(CourtRoomAmendValidator.class);
    private static final String ERROR_STRING = "";
    
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

        final CourtRoomAmendCommand courtRoomAmendCommand = (CourtRoomAmendCommand) command;
        if (courtRoomAmendCommand.getXhibitCourtSiteId() == null) {
            LOGGER.warn("validate method - Invalid court site selected");
            errors.reject("courtRoomAmendCommand.xhibitCourtSiteId.notNull");
        } else if (courtRoomAmendCommand.getCourtRoomId() == null) {
            LOGGER.warn("validate method - Invalid court room selected");
            errors.reject("courtRoomAmendCommand.courtRoomId.notNull");
        } else if (courtRoomAmendCommand.getName() == null
            || ERROR_STRING.equals(courtRoomAmendCommand.getName())) {
            LOGGER.warn("validate method - Invalid name");
            errors.reject("courtRoomAmendCommand.name.notBlank");
        } else if (courtRoomAmendCommand.getDescription() == null
            || ERROR_STRING.equals(courtRoomAmendCommand.getDescription())) {
            LOGGER.warn("validate method - Invalid description");
            errors.reject("courtRoomAmendCommand.description.notBlank");
        }

        LOGGER.info("validate method ends");
    }

}
