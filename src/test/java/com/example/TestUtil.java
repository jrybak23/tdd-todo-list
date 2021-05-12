package com.example;

import java.io.InputStream;
import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import static org.mockito.Mockito.doReturn;

public class TestUtil {

    public static InputStream getResourceAsStream(String input) {
        return Thread.currentThread().getContextClassLoader()
                .getResourceAsStream(input);
    }

    public static void mockClockAtTime(Clock clock, LocalDateTime dateTime) {
        Clock fixedClock = Clock.fixed(ZonedDateTime.of(dateTime, ZoneId.systemDefault()).toInstant(), ZoneId.systemDefault());
        doReturn(fixedClock.instant()).when(clock).instant();
        doReturn(fixedClock.getZone()).when(clock).getZone();
    }

    private TestUtil() {
    }
}
