package uk.gov.hmcts.pdm.business.entities.xhbconfigprop;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.hmcts.pdm.publicdisplay.common.test.AbstractJUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(MockitoExtension.class)
class XhbConfigPropDaoTest extends AbstractJUnit {

    private static final String NOT_EQUAL = "Not Equal";

    XhbConfigPropDao xhbConfigPropDao;

    @BeforeEach
    void setup() {
        xhbConfigPropDao = new XhbConfigPropDao();
    }

    @Test
    void testGetPrimaryKey() {
        xhbConfigPropDao.setPropertyValue("Value");
        assertEquals("Value", xhbConfigPropDao.getPropertyValue(), NOT_EQUAL);
    }

    @Test
    void testGetConfigPropId() {
        assertNull(xhbConfigPropDao.getConfigPropId(), NOT_EQUAL);
    }

    @Test
    void testGetPropertyName() {
        xhbConfigPropDao.setPropertyName("Name");
        assertEquals("Name", xhbConfigPropDao.getPropertyName(), NOT_EQUAL);
    }

    @Test
    void testGetVersion() {
        xhbConfigPropDao.setVersion(1);
        assertNull(xhbConfigPropDao.getVersion(), NOT_EQUAL);
    }

}