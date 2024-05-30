package uk.gov.hmcts.pdm.publicdisplay.manager.web.courtel;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class CourtelAmendValidator implements Validator {
    /** The logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger(CourtelAmendCommand.class);

    /*
     * (non-Javadoc)
     *
     * @see org.springframework.validation.Validator#supports(java.lang.Class)
     */
    @Override
    public boolean supports(final Class<?> clazz) {
        return CourtelAmendCommand.class.isAssignableFrom(clazz);
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

        final CourtelAmendCommand courtRoomAmendCommand = (CourtelAmendCommand) command;

        if (!StringUtils.isNumeric(courtRoomAmendCommand.getCourtelListAmount())
                || courtRoomAmendCommand.getCourtelListAmount().isEmpty()) {
            LOGGER.warn("validate method - Invalid courtel list amount");
            errors.reject("courtelAmendCommand.courtelListAmount.notNumber");
        } else if (!StringUtils.isNumeric(courtRoomAmendCommand.getCourtelMaxRetry())
                || courtRoomAmendCommand.getCourtelMaxRetry().isEmpty()) {
            LOGGER.warn("validate method - Invalid court room selected");
            errors.reject("courtelAmendCommand.courtelMaxRetry.notNumber");
        } else if (!StringUtils.isNumeric(courtRoomAmendCommand.getMessageLookupDelay())
                || courtRoomAmendCommand.getMessageLookupDelay().isEmpty()) {
            LOGGER.warn("validate method - Invalid name");
            errors.reject("courtelAmendCommand.messageLookupDelay.notNumber");
        }

        LOGGER.info("validate method ends");
    }
}
