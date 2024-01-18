package uk.gov.hmcts.pdm.publicdisplay.manager.web.courtroom;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component
public class CourtRoomSelectedValidator extends AbstractCourtRoomValidator {

    /** The logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger(CourtRoomSelectedValidator.class);

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.validation.Validator#supports(java.lang.Class)
     */
    @Override
    public boolean supports(final Class<?> clazz) {
        return CourtRoomSearchCommand.class.isAssignableFrom(clazz);
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

        final CourtRoomSearchCommand courtRoomSearchCommand = (CourtRoomSearchCommand) command;
        if (courtRoomSearchCommand.getCourtId() == null) {
            LOGGER.warn("validate method - Invalid value selected");
            errors.reject("courtRoomSearchCommand.courtId.notNull");
        }

        LOGGER.info("validate method ends");
    }

}
