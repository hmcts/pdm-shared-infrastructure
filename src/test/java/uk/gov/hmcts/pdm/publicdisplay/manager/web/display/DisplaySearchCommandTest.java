package uk.gov.hmcts.pdm.publicdisplay.manager.web.display;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DisplaySearchCommandTest {

    @Test
    void testToString() {
        DisplaySearchCommand displaySearchCommand = new DisplaySearchCommand();
        displaySearchCommand.setXhibitCourtSiteId(6L);

        assertEquals("XhibitCourtSiteId :6", displaySearchCommand.toString(), "Not equal");
    }
}