package uk.gov.hmcts.pdm.publicdisplay.manager.web.court;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component
public class CourtSelectedValidator extends AbstractCourtValidator {

    /** The logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger(CourtSelectedValidator.class);

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.validation.Validator#supports(java.lang.Class)
     */
    @Override
    public boolean supports(final Class<?> clazz) {
        return CourtSelectedValidator.class.isAssignableFrom(clazz);
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

        final CourtSearchCommand courtSearchCommand = (CourtSearchCommand) command;
        if (courtSearchCommand.getCourtId() == null) {
            LOGGER.warn("validate method - Invalid value selected");
            errors.reject("courtSearchCommand.courtId.notNull");
        }

        LOGGER.info("validate method ends");
    }

}