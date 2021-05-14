package com.example.config;

import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;
import org.testcontainers.containers.PostgreSQLContainer;

import java.util.Map;

import static java.util.Collections.singletonMap;

public class PostgresResource implements QuarkusTestResourceLifecycleManager {

    private static final String POSTGRES_CONTAINER_DATA_PATH = "/var/lib/postgresql/data";
    private static final String TMPFS_OPTIONS = ""; // https://docs.docker.com/storage/tmpfs/#specify-tmpfs-options

    private final static PostgreSQLContainer<?> db = new PostgreSQLContainer<>("postgres:13")
            .withDatabaseName("testdb")
            .withUsername("postgres")
            .withPassword("postgres")
            .withTmpFs(Map.of(POSTGRES_CONTAINER_DATA_PATH, TMPFS_OPTIONS));

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