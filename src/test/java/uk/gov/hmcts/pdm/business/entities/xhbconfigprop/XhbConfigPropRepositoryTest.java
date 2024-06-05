package uk.gov.hmcts.pdm.business.entities.xhbconfigprop;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.hmcts.pdm.business.entities.AbstractRepositoryTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.isA;

@ExtendWith(MockitoExtension.class)
class XhbConfigPropRepositoryTest extends AbstractRepositoryTest<XhbConfigPropDao> {
    protected static final String NOT_NULL = "Not null";
    protected static final String NULL = "Null";
    protected static final String NOT_EQUAL = "Not equal";
    protected static final String FALSE = "false";
    protected static final String TRUE = "Not equal";
    @Mock
    private EntityManager mockEntityManager;

    @InjectMocks
    private XhbConfigPropRepository classUnderTest;

    @Override
    protected EntityManager getEntityManager() {
        return mockEntityManager;
    }

    @Override
    protected XhbConfigPropRepository getClassUnderTest() {
        if (classUnderTest == null) {
            classUnderTest = new XhbConfigPropRepository(getEntityManager());
        }
        return classUnderTest;
    }

    @Test
    void testfindByPropertyNameSuccess() {
        boolean result = testfindByPropertyName(getDummyDao());
        assertTrue(result, TRUE);
    }

    @Test
    void testfindByPropertyNameFailure() {
        boolean result = testfindByPropertyName(null);
        assertTrue(result, TRUE);
    }

    private boolean testfindByPropertyName(XhbConfigPropDao dao) {
        List<XhbConfigPropDao> list = new ArrayList<>();
        if (dao != null) {
            list.add(dao);
        }
        Mockito.when(getEntityManager().createNamedQuery(isA(String.class))).thenReturn(mockQuery);
        Mockito.when(mockQuery.getResultList()).thenReturn(list);
        List<XhbConfigPropDao> result =
                (List<XhbConfigPropDao>) getClassUnderTest().findByPropertyName(getDummyDao().getPropertyName());
        assertNotNull(result, "Result is Null");
        if (dao != null) {
            assertSame(dao, result.get(0), "Result is not Same");
        } else {
            assertSame(0, result.size(), "Result is not Same");
        }
        return true;
    }

    @Override
    protected XhbConfigPropDao getDummyDao() {
        Integer configPropId = getDummyId();
        String propertyName = "propertyName";
        String propertyValue = "propertyValue";

        XhbConfigPropDao result = new XhbConfigPropDao(configPropId, propertyName, propertyValue);
        configPropId = result.getPrimaryKey();
        assertNotNull(configPropId, NOTNULL);
        return new XhbConfigPropDao(result);
    }

}