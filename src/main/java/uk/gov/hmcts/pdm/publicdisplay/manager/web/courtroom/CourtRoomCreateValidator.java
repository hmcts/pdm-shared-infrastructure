package uk.gov.hmcts.pdm.publicdisplay.manager.web.courtroom;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import uk.gov.hmcts.pdm.publicdisplay.manager.dto.CourtRoomDto;
import uk.gov.hmcts.pdm.publicdisplay.manager.service.api.ICourtRoomService;

import java.util.List;
import java.util.Locale;

/**
 * The Class CourtRoomCreateValidator.
 * 
 * @author ghafouria
 */
@Component
public class CourtRoomCreateValidator extends AbstractCourtRoomValidator {
    /** The logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger(CourtRoomCreateValidator.class);
    private static final String ERROR_STRING = "";

    @Autowired
    private MessageSource messageSource;

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.validation.Validator#supports(java.lang.Class)
     */
    @Override
    public boolean supports(final Class<?> clazz) {
        return CourtRoomCreateCommand.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(final Object target, final Errors errors) {
        throw new IllegalArgumentException("Use other method definition");
    }

    public void validate(final Object command, final Errors errors,
        final ICourtRoomService courtRoomService) {
        LOGGER.info("validate method starts");

        final CourtRoomCreateCommand courtRoomCreateCommand = (CourtRoomCreateCommand) command;
        if (courtRoomCreateCommand.getName() == null
            || ERROR_STRING.equals(courtRoomCreateCommand.getName())) {
            LOGGER.warn("validate method - Invalid name");
            errors.reject("courtRoomAmendCommand.name.notBlank");
        } else if (courtRoomCreateCommand.getDescription() == null
            || ERROR_STRING.equals(courtRoomCreateCommand.getDescription())) {
            LOGGER.warn("validate method - Invalid description");
            errors.reject("courtRoomAmendCommand.description.notBlank");
        } else if (courtRoomCreateCommand.getXhibitCourtSiteId() == null) {
            LOGGER.warn("validate method - Invalid court site");
            errors.reject("courtRoomAmendCommand.xhibitCourtSiteId.notNull");
        } else if (alreadyExists(
            courtRoomService.getCourtRooms(courtRoomCreateCommand.getXhibitCourtSiteId()),
            courtRoomCreateCommand.getName())) {
            LOGGER.warn("validate method - Court Site Name already exists for this courtId");
            String message = messageSource.getMessage("courtRoomCreateCommand.name.exists", null,
                Locale.getDefault());
            errors.rejectValue("name", null, message);
        }
        LOGGER.info("validate method ends");
    }

    private boolean alreadyExists(List<CourtRoomDto> courtRoomDtos, String courtRoomName) {
        for (CourtRoomDto courtRoomDto : courtRoomDtos) {
            if (courtRoomDto.getCourtRoomName().equalsIgnoreCase(courtRoomName)) {
                return true;
            }
        }
        return false;
    }
}
