package com.example.testutil;

import lombok.SneakyThrows;

import java.io.InputStream;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import static java.lang.Thread.currentThread;
import static org.mockito.Mockito.doReturn;

public class TestUtil {

    public static InputStream getResourceAsStream(String filePath) {
        return getClassLoader().getResourceAsStream(filePath);
    }

    @SneakyThrows
    public static String readFileContent(String filePath) {
        URI uri = getClassLoader().getResource(filePath).toURI();
        return Files.readString(Path.of(uri));
    }

    private static ClassLoader getClassLoader() {
        return currentThread().getContextClassLoader();
    }

    public static void mockClockAtTime(Clock clock, LocalDateTime dateTime) {
        Clock fixedClock = Clock.fixed(ZonedDateTime.of(dateTime, ZoneId.systemDefault()).toInstant(), ZoneId.systemDefault());
        doReturn(fixedClock.instant()).when(clock).instant();
        doReturn(fixedClock.getZone()).when(clock).getZone();
    }

    private TestUtil() {
    }
}
