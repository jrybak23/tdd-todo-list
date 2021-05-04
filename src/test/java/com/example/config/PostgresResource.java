package com.example.config;

import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;
import org.testcontainers.containers.PostgreSQLContainer;

import java.util.Map;

import static java.util.Collections.singletonMap;

public class PostgresResource implements QuarkusTestResourceLifecycleManager {

    private final static PostgreSQLContainer<?> db = new PostgreSQLContainer<>("postgres:13")
            .withDatabaseName("testdb")
            .withUsername("postgres")
            .withPassword("postgres");

    @Override
    public Map<String, String> start() {
        db.start();
        return singletonMap("quarkus.datasource.jdbc.url", db.getJdbcUrl());
    }

    @Override
    public void stop() {
        db.stop();
    }
}