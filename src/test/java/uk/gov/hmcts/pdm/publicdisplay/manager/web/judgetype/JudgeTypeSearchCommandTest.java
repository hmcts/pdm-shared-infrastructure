package uk.gov.hmcts.pdm.publicdisplay.manager.web.judgetype;

import org.easymock.EasyMockExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import uk.gov.hmcts.pdm.publicdisplay.common.test.AbstractJUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(EasyMockExtension.class)
class JudgeTypeSearchCommandTest extends AbstractJUnit {
    @Test
    void testToString() {
        JudgeTypeSearchCommand judgeTypeSearchCommand = new JudgeTypeSearchCommand();
        judgeTypeSearchCommand.setXhibitCourtSiteId(1L);
        assertEquals("XhibitCourtSiteId :1", judgeTypeSearchCommand.toString(), "Not equal");
    }
}