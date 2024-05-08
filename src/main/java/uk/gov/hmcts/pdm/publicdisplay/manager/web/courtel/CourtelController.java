package uk.gov.hmcts.pdm.publicdisplay.manager.web.courtel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import uk.gov.hmcts.pdm.publicdisplay.manager.web.court.CourtAmendValidator;
import uk.gov.hmcts.pdm.publicdisplay.manager.web.court.CourtCreateValidator;
import uk.gov.hmcts.pdm.publicdisplay.manager.web.court.CourtSelectedValidator;

@Controller
@RequestMapping("/courtel")
public class CourtelController {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(CourtelController.class);
    private static final String ADDING_COURT_TO_MODEL = "{}{} adding court data to model";
    private static final String ADDING_COURTSITE_TO_MODEL = "{}{} adding court site data to model";
    private static final String COURT_LIST = "courtList";
    private static final String COURTSITE_LIST = "courtSiteList";
    private static final String COMMAND = "command";
    private static final String COURT = "court";
    private static final String SUCCESS_MESSAGE = "successMessage";

    /** The Constant for the JSP Folder. */
    protected static final String FOLDER_COURTEL = "courtel";

    /**
     * Court Court.
     */
    private static final String MAPPING_VIEW_COURTEL = "/view_courtel";

    /**
     * Create Court.
     */
    private static final String MAPPING_CREATE_COURT = "/create_court";

    /**
     * Amend Display.
     */
    private static final String MAPPING_AMEND_COURT = "/amend_court";

    /**
     * View Court View.
     */
    private static final String VIEW_NAME_VIEW_COURTEL = FOLDER_COURTEL + MAPPING_VIEW_COURTEL;

    /**
     * Create Court View.
     */
    private static final String VIEW_NAME_CREATE_COURT = FOLDER_COURTEL + MAPPING_CREATE_COURT;

    /**
     * Amend court View.
     */
    private static final String VIEW_NAME_AMEND_COURT = FOLDER_COURTEL + MAPPING_AMEND_COURT;

    /**
     * The display selected validator.
     */
    @Autowired
    private CourtSelectedValidator courtSelectedValidator;

    /**
     * The court create validator.
     */
    @Autowired
    private CourtCreateValidator courtCreateValidator;

    /**
     * The court amend validator.
     */
    @Autowired
    private CourtAmendValidator courtAmendValidator;

    /**
     * View Court.
     *
     * @param model the model
     * @param reset the reset
     * @return the model and view
     */
    @RequestMapping(value = MAPPING_VIEW_COURTEL, method = RequestMethod.GET)
    public ModelAndView viewCourt(final ModelAndView model,
            @RequestParam(value = "reset", defaultValue = "true") final boolean reset) {
        final String methodName = "viewCourt";
//        LOGGER.info(THREE_PARAMS, METHOD, methodName, STARTS);
//
//        CourtSearchCommand courtSearchCommand = new CourtSearchCommand();
//        if (reset || courtPageStateHolder.getCourtSearchCommand() == null) {
//            LOGGER.debug("{}{} - reset court search results", METHOD, methodName);
//            courtPageStateHolder.reset();
//
//            // retrieve and add the selection lists to the pageStateHolder
//            setViewPageStateSelectionLists();
//        } else {
//            courtSearchCommand = courtPageStateHolder.getCourtSearchCommand();
//        }

        // Add the courtSearchCommand to the model
//        LOGGER.debug("{}{} adding courtSearchCommand to model", METHOD, methodName);
//        model.addObject(COMMAND, courtSearchCommand);
//
//        // Add the court data to model
//        LOGGER.debug(ADDING_COURT_TO_MODEL, METHOD, methodName);
//        model.addObject(COURT_LIST, courtPageStateHolder.getCourts());
//
//        // Return the model
//        LOGGER.debug("{}{} returning model", METHOD, methodName);
        model.setViewName(VIEW_NAME_VIEW_COURTEL);

//        LOGGER.info(THREE_PARAMS, METHOD, methodName, ENDS);
        return model;
    }
}
