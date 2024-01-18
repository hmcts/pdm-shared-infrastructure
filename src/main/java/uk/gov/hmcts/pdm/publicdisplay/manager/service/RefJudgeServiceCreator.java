package uk.gov.hmcts.pdm.publicdisplay.manager.service;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import uk.gov.hmcts.pdm.publicdisplay.manager.dto.RefJudgeDto;
import uk.gov.hmcts.pdm.publicdisplay.manager.dto.RefSystemCodeDto;
import uk.gov.hmcts.pdm.publicdisplay.manager.dto.XhibitCourtSiteDto;

@Component
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class RefJudgeServiceCreator {

    protected XhibitCourtSiteDto createXhibitCourtSiteDto() {
        return new XhibitCourtSiteDto();
    }
    
    protected RefJudgeDto createRefJudgeDto() {
        return new RefJudgeDto();
    }
    
    protected RefSystemCodeDto createRefSystemCodeDto() {
        return new RefSystemCodeDto();
    }
}
