package uk.gov.hmcts.pdm.publicdisplay.manager.web.hearing;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HearingTypeSearchCommandTest {

    @Test
    void testToString() {
        HearingTypeSearchCommand hearingTypeSearchCommand = new HearingTypeSearchCommand();
        hearingTypeSearchCommand.setXhibitCourtSiteId(4L);
        assertEquals("XhibitCourtSiteId :4", hearingTypeSearchCommand.toString(), "Not equal");
    }

}