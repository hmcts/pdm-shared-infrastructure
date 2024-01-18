package uk.gov.hmcts.pdm.business.entities.xhbcourt;

import uk.gov.hmcts.pdm.business.entities.AbstractRepository;

import javax.persistence.EntityManager;

public class XhbCourtRepository extends AbstractRepository<XhbCourtDao> {

    public XhbCourtRepository(EntityManager em) {
        super(em);
    }

    @Override
    public Class<XhbCourtDao> getDaoClass() {
        return XhbCourtDao.class;
    }
}
