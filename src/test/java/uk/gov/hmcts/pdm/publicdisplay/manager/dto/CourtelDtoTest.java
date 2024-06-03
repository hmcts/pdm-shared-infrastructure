package uk.gov.hmcts.pdm.publicdisplay.manager.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class CourtelDtoTest {

    private static final String NOT_EQUAL = "Not Equal";

    CourtelDto courtelDto;

    @BeforeEach
    void setup() {
        courtelDto = new CourtelDto();
    }

    @Test
    void testCourtelListAmount() {
        courtelDto.setCourtelMaxRetry("1");
        assertEquals("1", courtelDto.getCourtelMaxRetry(), NOT_EQUAL);
    }

    @Test
    void testCourtelMaxRetry() {
        courtelDto.setCourtelListAmount("2");
        assertEquals("2", courtelDto.getCourtelListAmount(), NOT_EQUAL);
    }

    @Test
    void testCourtelMessageLookupDelay() {
        courtelDto.setCourtelMessageLookupDelay("3");
        assertEquals("3", courtelDto.getCourtelMessageLookupDelay(), NOT_EQUAL);
    }
}