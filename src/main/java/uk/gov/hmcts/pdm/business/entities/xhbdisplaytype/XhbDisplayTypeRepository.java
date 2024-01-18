package uk.gov.hmcts.pdm.business.entities.xhbdisplaytype;

import uk.gov.hmcts.pdm.business.entities.AbstractRepository;

import javax.persistence.EntityManager;

public class XhbDisplayTypeRepository extends AbstractRepository<XhbDisplayTypeDao> {

    protected static final String METHOD = "Method ";
    protected static final String THREE_PARAMS = "{}{}{}";
    protected static final String STARTS = " - starts";
    protected static final String ENDS = " - ends";

    public XhbDisplayTypeRepository(EntityManager em) {
        super(em);
    }

    @Override
    public Class<XhbDisplayTypeDao> getDaoClass() {
        return XhbDisplayTypeDao.class;
    }
}
