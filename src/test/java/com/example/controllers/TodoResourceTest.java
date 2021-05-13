package com.example.controllers;

import com.example.config.IntegrationTestProfile;
import com.github.database.rider.cdi.api.DBRider;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.TestProfile;
import io.quarkus.test.junit.mockito.InjectMock;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.time.Clock;
import java.time.LocalDateTime;

import static com.example.TestUtil.getResourceAsStream;
import static com.example.TestUtil.mockClockAtTime;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
@TestProfile(IntegrationTestProfile.class)
@DBRider
class TodoResourceTest {
    private static final String BASE_DIR = "com/example/controllers/TodoResourceTest/";

    @InjectMock
    Clock clock;

    @Test
    @DataSet(value = BASE_DIR + "testGetItems_dataset.xml")
    void testGetItems() {
        given()
                .when().get("/todo-items")
                .then()
                .statusCode(200)
                .body(is("[{\"content\":\"buy eggs\",\"dateTimeCreated\":\"2021-05-04T17:41:50\",\"id\":\"8aa23897-89dd-4d08-8638-8fb39d49dcfb\"}]"));
    }

    @Test
    @DataSet(value = BASE_DIR + "testCreateItem_dataset.xml")
    @ExpectedDataSet(value = BASE_DIR + "testCreateItem_expectedDataset.xml")
    void testCreateItem() {
        mockClockAtTime(clock, LocalDateTime.parse("2021-05-11T00:33:53.409656900"));
        InputStream testPayload = getResourceAsStream(BASE_DIR + "testCreateItem_payload.json");
        given()
                .when()
                .contentType("application/json").body(testPayload)
                .post("/todo-items")
                .then()
                .statusCode(201);
    }
}