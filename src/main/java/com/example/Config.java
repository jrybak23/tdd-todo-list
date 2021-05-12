package com.example;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import java.time.Clock;

@ApplicationScoped
public class Config {
    /**
     * @return clock to pass to LocalDateTime#now() to be able to mock it.
     */
    @Produces
    @ApplicationScoped
    public Clock clock() {
        return Clock.systemDefaultZone();
    }
}
