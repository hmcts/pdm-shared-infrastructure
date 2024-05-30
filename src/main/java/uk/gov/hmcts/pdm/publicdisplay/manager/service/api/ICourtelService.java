package uk.gov.hmcts.pdm.publicdisplay.manager.service.api;

import uk.gov.hmcts.pdm.publicdisplay.manager.dto.CourtelDto;
import uk.gov.hmcts.pdm.publicdisplay.manager.web.courtel.CourtelAmendCommand;

public interface ICourtelService {
    void updateCourtelListAmount(CourtelAmendCommand courtelAmendCommand);

    void updateCourtelMaxRetry(CourtelAmendCommand courtelAmendCommand);

    void updateMessageLookupDelay(CourtelAmendCommand courtelAmendCommand);

    CourtelDto getCourtelPropertyValues();
}
