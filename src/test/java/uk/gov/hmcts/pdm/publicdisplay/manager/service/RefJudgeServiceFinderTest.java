package uk.gov.hmcts.pdm.publicdisplay.manager.service;

import com.pdm.hb.jpa.EntityManagerUtil;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.test.util.ReflectionTestUtils;
import uk.gov.hmcts.pdm.business.entities.xhbcourtsite.XhbCourtSiteRepository;
import uk.gov.hmcts.pdm.business.entities.xhbrefjudge.XhbRefJudgeRepository;
import uk.gov.hmcts.pdm.business.entities.xhbrefsystemcode.XhbRefSystemCodeRepository;
import uk.gov.hmcts.pdm.publicdisplay.common.test.AbstractJUnit;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * The Class RefJudgeServiceFinderTest.
 *
 * @author Luke Gittins
 */
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
@SuppressWarnings("PMD.LawOfDemeter")
class RefJudgeServiceFinderTest extends AbstractJUnit {

    private static final String NOTNULL = "Result is Null";

    @Mock
    private EntityManager mockEntityManager;

    @Mock
    private XhbCourtSiteRepository mockXhbCourtSiteRepository;

    @Mock
    private XhbRefJudgeRepository mockXhbRefJudgeRepository;
    
    @Mock
    private XhbRefSystemCodeRepository mockXhbRefSystemCodeRepository;
    
    private RefJudgeServiceFinder classUnderTest;

    @BeforeEach
    public void setup() {
        classUnderTest = new RefJudgeServiceFinder();
    }

    @AfterEach
    public void teardown() {
        Mockito.clearAllCaches();
    }

    @Test
    void testGetXhbCourtSiteRepository() {
        expectEntityManager();
        XhbCourtSiteRepository result = classUnderTest.getXhbCourtSiteRepository();
        assertNotNull(result, NOTNULL);

        ReflectionTestUtils.setField(classUnderTest, "xhbCourtSiteRepository",
            mockXhbCourtSiteRepository);
        result = classUnderTest.getXhbCourtSiteRepository();
        assertNotNull(result, NOTNULL);
    }

    @Test
    void testGetXhbRefJudgeRepository() {
        expectEntityManager();
        XhbRefJudgeRepository result = classUnderTest.getXhbRefJudgeRepository();
        assertNotNull(result, NOTNULL);

        ReflectionTestUtils.setField(classUnderTest, "xhbRefJudgeRepository",
            mockXhbRefJudgeRepository);
        result = classUnderTest.getXhbRefJudgeRepository();
        assertNotNull(result, NOTNULL);
    }
    
    @Test
    void testGetXhbRefSystemCodeRepository() {
        expectEntityManager();
        XhbRefSystemCodeRepository result = classUnderTest.getXhbRefSystemCodeRepository();
        assertNotNull(result, NOTNULL);

        ReflectionTestUtils.setField(classUnderTest, "xhbRefSystemCodeRepository",
            mockXhbRefSystemCodeRepository);
        result = classUnderTest.getXhbRefSystemCodeRepository();
        assertNotNull(result, NOTNULL);
    }

    @Test
    void testNullEntityManager() {
        Mockito.mockStatic(EntityManagerUtil.class);
        Mockito.when(EntityManagerUtil.getEntityManager()).thenReturn(mockEntityManager);
        XhbCourtSiteRepository result = classUnderTest.getXhbCourtSiteRepository();
        assertNotNull(result, NOTNULL);
    }

    private void expectEntityManager() {
        ReflectionTestUtils.setField(classUnderTest, "entityManager", mockEntityManager);
        Mockito.when(mockEntityManager.isOpen()).thenReturn(true);
    }
}