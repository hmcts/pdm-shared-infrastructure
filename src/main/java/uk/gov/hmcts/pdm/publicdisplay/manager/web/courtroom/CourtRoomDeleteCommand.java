package uk.gov.hmcts.pdm.publicdisplay.manager.web.courtroom;

public class CourtRoomDeleteCommand extends CourtRoomAmendCommand {

    private static final String NOT_APPLICABLE = "N/A";
    private static final String YES = "Y";

    public CourtRoomDeleteCommand() {
        super();
        setName(NOT_APPLICABLE);
        setDescription(NOT_APPLICABLE);
    }

    @Override
    public String getObsInd() {
        return YES;
    }


}
