package uk.gov.hmcts.pdm.publicdisplay.manager.service;

import org.easymock.EasyMockExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.util.ReflectionTestUtils;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * The Class RagStatusOverallTest.
 *
 * @author boparaij
 */
@ExtendWith(EasyMockExtension.class)
abstract class RagStatusOverallTest extends RagStatusServiceTestBase {

    /**
     * Test get rag status green upper limit.
     */
    @Test
    void testRagStatusOverallTotalsGreenUpperLimit() {
        // Add the mock calls to child classes
        expect(mockCduRepo.getCduWeightingTotal()).andReturn(80L);
        expect(mockCduRepo.getCduWeightingOperational()).andReturn(80L);
        replay(mockCduRepo);

        ReflectionTestUtils.setField(classUnderTest, RAG_STATUS_AMBER, 80);
        ReflectionTestUtils.setField(classUnderTest, RAG_STATUS_RED, 60);

        // Perform the test
        final String results = classUnderTest.getRagStatusOverall();

        // Assert that the objects are as expected
        assertNotNull(results, NULL);
        assertEquals(GREEN_CHAR.toString(), results, NOT_EQUAL);

        // Verify the expected mocks were called
        verify(mockCduRepo);
    }

    /**
     * Test get rag status green lower limit.
     */
    @Test
    void testRagStatusOverallTotalsGreenLowerLimit() {
        // Add the mock calls to child classes
        expect(mockCduRepo.getCduWeightingTotal()).andReturn(80L);
        expect(mockCduRepo.getCduWeightingOperational()).andReturn(64L);
        replay(mockCduRepo);

        ReflectionTestUtils.setField(classUnderTest, RAG_STATUS_AMBER, 80);
        ReflectionTestUtils.setField(classUnderTest, RAG_STATUS_RED, 60);

        // Perform the test
        final String results = classUnderTest.getRagStatusOverall();

        // Assert that the objects are as expected
        assertNotNull(results, NULL);
        assertEquals(GREEN_CHAR.toString(), results, NOT_EQUAL);

        // Verify the expected mocks were called
        verify(mockCduRepo);
    }

    /**
     * Test get rag status amber upper limit.
     */
    @Test
    void testRagStatusOverallTotalsAmberUpperLimit() {
        // Add the mock calls to child classes
        expect(mockCduRepo.getCduWeightingTotal()).andReturn(800L);
        expect(mockCduRepo.getCduWeightingOperational()).andReturn(632L);
        replay(mockCduRepo);

        ReflectionTestUtils.setField(classUnderTest, RAG_STATUS_AMBER, 80);
        ReflectionTestUtils.setField(classUnderTest, RAG_STATUS_RED, 60);

        // Perform the test
        final String results = classUnderTest.getRagStatusOverall();

        // Assert that the objects are as expected
        assertNotNull(results, NULL);
        assertEquals(AMBER_CHAR.toString(), results, NOT_EQUAL);

        // Verify the expected mocks were called
        verify(mockCduRepo);
    }

    /**
     * Test get rag status amber lower limit.
     */
    @Test
    void testRagStatusOverallTotalsAmberLowerLimit() {
        // Add the mock calls to child classes
        expect(mockCduRepo.getCduWeightingTotal()).andReturn(80L);
        expect(mockCduRepo.getCduWeightingOperational()).andReturn(48L);
        replay(mockCduRepo);

        ReflectionTestUtils.setField(classUnderTest, RAG_STATUS_AMBER, 80);
        ReflectionTestUtils.setField(classUnderTest, RAG_STATUS_RED, 60);

        // Perform the test
        final String results = classUnderTest.getRagStatusOverall();

        // Assert that the objects are as expected
        assertNotNull(results, NULL);
        assertEquals(AMBER_CHAR.toString(), results, NOT_EQUAL);

        // Verify the expected mocks were called
        verify(mockCduRepo);
    }

    /**
     * Test get rag status red upper limit.
     */
    @Test
    void testRagStatusOverallTotalsRedUpperLimit() {
        // Add the mock calls to child classes
        expect(mockCduRepo.getCduWeightingTotal()).andReturn(800L);
        expect(mockCduRepo.getCduWeightingOperational()).andReturn(472L);
        replay(mockCduRepo);

        ReflectionTestUtils.setField(classUnderTest, RAG_STATUS_AMBER, 80);
        ReflectionTestUtils.setField(classUnderTest, RAG_STATUS_RED, 60);

        // Perform the test
        final String results = classUnderTest.getRagStatusOverall();

        // Assert that the objects are as expected
        assertNotNull(results, NULL);
        assertEquals(RED_CHAR.toString(), results, NOT_EQUAL);

        // Verify the expected mocks were called
        verify(mockCduRepo);
    }

    /**
     * Test get rag status red lower limit.
     *
     */
    @Test
    void testRagStatusOverallTotalsRedLowerLimit() {
        // Add the mock calls to child classes
        expect(mockCduRepo.getCduWeightingTotal()).andReturn(800L);
        expect(mockCduRepo.getCduWeightingOperational()).andReturn(0L);
        replay(mockCduRepo);

        ReflectionTestUtils.setField(classUnderTest, RAG_STATUS_AMBER, 80);
        ReflectionTestUtils.setField(classUnderTest, RAG_STATUS_RED, 60);

        // Perform the test
        final String results = classUnderTest.getRagStatusOverall();

        // Assert that the objects are as expected
        assertNotNull(results, NULL);
        assertEquals(RED_CHAR.toString(), results, NOT_EQUAL);

        // Verify the expected mocks were called
        verify(mockCduRepo);
    }

    /**
     * Test get rag status zero.
     */
    @Test
    void testRagStatusOverallTotalsZero() {
        // Add the mock calls to child classes
        expect(mockCduRepo.getCduWeightingTotal()).andReturn(0L);
        expect(mockCduRepo.getCduWeightingOperational()).andReturn(0L);
        replay(mockCduRepo);

        ReflectionTestUtils.setField(classUnderTest, RAG_STATUS_AMBER, 80);
        ReflectionTestUtils.setField(classUnderTest, RAG_STATUS_RED, 60);

        // Perform the test
        final String results = classUnderTest.getRagStatusOverall();

        // Assert that the objects are as expected
        assertNotNull(results, NULL);
        assertEquals(GREEN_CHAR.toString(), results, NOT_EQUAL);

        // Verify the expected mocks were called
        verify(mockCduRepo);
    }

}
