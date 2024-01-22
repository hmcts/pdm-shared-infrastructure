package uk.gov.hmcts.pdm.publicdisplay.manager.dto;

import org.easymock.EasyMockExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import uk.gov.hmcts.pdm.publicdisplay.common.test.AbstractJUnit;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(EasyMockExtension.class)
abstract class RefSystemCodeAuthorDtoTest extends AbstractJUnit {

    /** The class under test. */
    protected RefSystemCodeDto classUnderTest;

    protected static final String NOT_EQUAL = "Not equal";

    /**
     * Setup.
     */
    @BeforeEach
    public void setup() {
        classUnderTest = new RefSystemCodeDto();

    }

    @Test
    void lastUpdateDate() {

        classUnderTest.setLastUpdateDate(LocalDateTime.of(2024, 01, 11, 8, 24, 2));
        assertEquals(LocalDateTime.of(2024, 01, 11, 8, 24, 2), classUnderTest.getLastUpdateDate(),
            NOT_EQUAL);

    }

    @Test
    void creationDate() {

        classUnderTest.setCreationDate(LocalDateTime.of(2024, 01, 11, 8, 24, 2));
        assertEquals(LocalDateTime.of(2024, 01, 11, 8, 24, 2), classUnderTest.getCreationDate(),
            NOT_EQUAL);

    }

    @Test
    void createdBy() {

        classUnderTest.setCreatedBy("author");
        assertEquals("author", classUnderTest.getCreatedBy(), NOT_EQUAL);

    }

    @Test
    void lastUpdatedBy() {

        classUnderTest.setLastUpdatedBy("authorB");
        assertEquals("authorB", classUnderTest.getLastUpdatedBy(), NOT_EQUAL);

    }
}
