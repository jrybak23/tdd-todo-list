package com.example.config;

import io.quarkus.test.junit.QuarkusTestProfile;

import java.util.List;

import static java.util.Collections.singletonList;

public class IntegrationTestProfile implements QuarkusTestProfile {

    @Override
    public String getConfigProfile() {
        return "integrationTest";
    }

    @Override
    public List<TestResourceEntry> testResources() {
        return singletonList(new TestResourceEntry(PostgresResource.class));
    }
}
