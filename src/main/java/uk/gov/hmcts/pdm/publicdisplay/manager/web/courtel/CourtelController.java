package uk.gov.hmcts.pdm.publicdisplay.manager.web.courtel;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import uk.gov.hmcts.pdm.publicdisplay.common.exception.XpdmException;
import uk.gov.hmcts.pdm.publicdisplay.manager.service.api.ICourtelService;

@Controller
@RequestMapping("/courtel")
public class CourtelController {

    /**
     * The Constant LOGGER.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(CourtelController.class);
    private static final String COMMAND = "command";
    private static final String SUCCESS_MESSAGE = "successMessage";
    protected static final String METHOD = "Method ";
    protected static final String THREE_PARAMS = "{}{}{}";
    protected static final String STARTS = " - starts";
    protected static final String ENDS = " - ends";

    /**
     * Our CourtelService class.
     */
    @Autowired
    protected ICourtelService courtelService;

    /**
     * The Constant for the JSP Folder.
     */
    protected static final String FOLDER_COURTEL = "courtel";

    /**
     * Courtel Courtel.
     */
    private static final String MAPPING_VIEW_COURTEL = "/view_courtel";

    /**
     * Amend Courtel.
     */
    private static final String MAPPING_AMEND_COURTEL = "/amend_courtel";

    /**
     * View Courtel View.
     */
    private static final String VIEW_NAME_VIEW_COURTEL = FOLDER_COURTEL + MAPPING_VIEW_COURTEL;

    /**
     * View Courtel.
     *
     * @param model the model
     * @param reset the reset
     * @return the model and view
     */
    @RequestMapping(value = MAPPING_VIEW_COURTEL, method = RequestMethod.GET)
    public ModelAndView viewCourtel(final ModelAndView model,
            @RequestParam(value = "reset", defaultValue = "true") final boolean reset) {
        final String methodName = "viewCourt";
        LOGGER.info(THREE_PARAMS, METHOD, methodName, STARTS);

        CourtelAmendCommand courtelAmendCommand = new CourtelAmendCommand();

        // Add the courtelAmendCommand to the model
        LOGGER.debug("{}{} adding courtelAmendCommand to model", METHOD, methodName);
        model.addObject(COMMAND, courtelAmendCommand);

        // Return the model
        LOGGER.debug("{}{} returning model", METHOD, methodName);
        model.setViewName(VIEW_NAME_VIEW_COURTEL);

        LOGGER.info(THREE_PARAMS, METHOD, methodName, ENDS);
        return model;
    }

    /**
     * Update courtel.
     *
     * @param courtelAmendCommand the courtel amend command
     * @param result              the result
     * @param model               the model
     * @return the model and view
     */
    @RequestMapping(value = MAPPING_AMEND_COURTEL, method = RequestMethod.POST, params = "btnUpdateConfirm")
    public ModelAndView updateCourtel(@Valid final CourtelAmendCommand courtelAmendCommand,
            final BindingResult result, final ModelAndView model) {
        final String methodName = "updateCourtel";
        LOGGER.info(THREE_PARAMS, METHOD, methodName, STARTS);

        if (result.hasErrors()) {
            // Default is to return to the view courtel page to display errors
            model.setViewName(VIEW_NAME_VIEW_COURTEL);

        } else {
            try {
                LOGGER.debug("{}{} - updating Courtel", METHOD, methodName);

                // Update the courtel
                courtelService.updateCourtelListAmount(courtelAmendCommand);
                courtelService.updateCourtelMaxRetry(courtelAmendCommand);
                courtelService.updateMessageLookupDelay(courtelAmendCommand);

                // Add successMessage to model for display on page
                model.addObject(SUCCESS_MESSAGE, "Courtel has been updated successfully.");

                LOGGER.info(THREE_PARAMS, METHOD, methodName, ENDS);

                return viewCourtel(model, true);
            } catch (final DataAccessException | XpdmException ex) {
                // Log the error
                LOGGER.error("{}{} Unable to update Courtel ", METHOD, methodName, ex);
                // Reject
                result.reject("courtelErrors", "Unable to update Courtel: " + ex.getMessage());
            }
        }
        model.addObject(COMMAND, courtelAmendCommand);

        LOGGER.info(THREE_PARAMS, METHOD, methodName, ENDS);
        return model;
    }
}
