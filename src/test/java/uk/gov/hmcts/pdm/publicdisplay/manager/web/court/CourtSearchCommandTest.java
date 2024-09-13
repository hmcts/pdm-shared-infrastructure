package uk.gov.hmcts.pdm.publicdisplay.manager.web.court;

import org.junit.jupiter.api.Test;
import uk.gov.hmcts.pdm.publicdisplay.common.test.AbstractJUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CourtSearchCommandTest extends AbstractJUnit {

    @Test
    void testToString() {
        CourtSearchCommand courtSearchCommand = new CourtSearchCommand();
        assertEquals("CourtId :no value at present", courtSearchCommand.toString(), "Not equal");
        courtSearchCommand.setCourtId(2);

        assertEquals("CourtId :2", courtSearchCommand.toString(), "Not equal");
    }
}