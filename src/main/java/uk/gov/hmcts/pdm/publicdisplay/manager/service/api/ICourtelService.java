package uk.gov.hmcts.pdm.publicdisplay.manager.service.api;

import uk.gov.hmcts.pdm.publicdisplay.manager.dto.CourtelDto;
import uk.gov.hmcts.pdm.publicdisplay.manager.web.courtel.CourtelAmendCommand;

public interface ICourtelService {

    void updateCourtelProperties(CourtelAmendCommand courtelAmendCommand);

    CourtelDto getCourtelPropertyValues();
}
