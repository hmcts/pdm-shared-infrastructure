package uk.gov.hmcts.pdm.publicdisplay.manager.web.courtroom;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CourtRoomSearchCommandTest {

    @Test
    void testToString() {
        CourtRoomSearchCommand courtRoomSearchCommand = new CourtRoomSearchCommand();
        courtRoomSearchCommand.setCourtId(1);
        assertEquals("CourtId :1", courtRoomSearchCommand.toString(), "Not equal");
    }
}