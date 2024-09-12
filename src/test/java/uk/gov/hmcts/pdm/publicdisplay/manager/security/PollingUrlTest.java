package uk.gov.hmcts.pdm.publicdisplay.manager.security;

import org.easymock.EasyMockExtension;
import org.joda.time.DateTime;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

/**
 * The class PollingUrlTest.
 *
 */
@ExtendWith(EasyMockExtension.class)
@SuppressWarnings("PMD.LawOfDemeter")
class PollingUrlTest extends PollingSessionTimeoutFilterTest {

    /**
     * Test first call to polling url.
     *
     * @throws Exception the exception
     */
    @Test
    void testPollingUrlFirstCall() throws Exception {
        // Perform the test
        final MvcResult results =
            mockMvc.perform(get(POLLING_URL).session(mockSession)).andReturn();

        // Assert new last accessed time matches now
        assertPollingLastAccessedTimeIsNow(results);
    }

    /**
     * Test subsequent call to polling url.
     *
     * @throws Exception the exception
     */
    @Test
    void testPollingUrlSubsequentCall() throws Exception {
        // Setup test objects
        mockSession.setAttribute(POLLING_LAST_ACCESSED_TIME, TEST_DATE.getMillis());

        // Perform the test
        final MvcResult results =
            mockMvc.perform(get(POLLING_URL).session(mockSession)).andReturn();

        // Assert the last accessed time matches test date
        assertPollingLastAccessedTimeIsTestDate(results);
    }

    /**
     * Test polling url after session timeout.
     *
     * @throws Exception the exception
     */
    @Test
    void testPollingUrlTimeout() throws Exception {
        // Setup test objects
        mockSession.setAttribute(POLLING_LAST_ACCESSED_TIME, TEST_DATE.minusHours(1).getMillis());

        // Perform the test
        final MvcResult results =
            mockMvc.perform(get(POLLING_URL).session(mockSession)).andReturn();

        // Assert there is no session
        assertNull(results.getRequest().getSession(false), NOT_NULL);
    }

    /**
     * Assert polling last accessed time is now.
     *
     * @param results the results
     */
    private void assertPollingLastAccessedTimeIsNow(final MvcResult results) {
        // Retrieve date time from session
        final DateTime lastAccess = getPollingLastAccessedTime(results);

        // Assert last access which can only be a range check because the
        // last access time is set to be "now" and so we can only test the
        // date is near "now" in that case
        final DateTime dateTimeMax = new DateTime();
        final DateTime dateTimeMin = dateTimeMax.minusMinutes(1);
        assertTrue(lastAccess.compareTo(dateTimeMax) <= 0, FALSE);
        assertTrue(lastAccess.compareTo(dateTimeMin) >= 0, FALSE);
    }

    /**
     * Assert polling last accessed time equals test date.
     *
     * @param results the results
     */
    private void assertPollingLastAccessedTimeIsTestDate(final MvcResult results) {
        // Retrieve date time from session
        final DateTime lastAccess = getPollingLastAccessedTime(results);

        // Assert last access time matches expected
        assertEquals(TEST_DATE, lastAccess, NOT_EQUAL);
    }
}
