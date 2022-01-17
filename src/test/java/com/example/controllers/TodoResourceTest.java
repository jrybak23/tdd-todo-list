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

import static com.example.testutil.JSONMatchers.matchesJSONInFile;
import static com.example.testutil.TestUtil.getResourceAsStream;
import static com.example.testutil.TestUtil.mockClockToReturnDateTime;
import static io.restassured.RestAssured.given;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.Response.Status.CREATED;
import static javax.ws.rs.core.Response.Status.OK;

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
                .statusCode(OK.getStatusCode())
                .log().ifValidationFails()
                .body(matchesJSONInFile(BASE_DIR + "testGetItems_expectedResponse.json"));
    }

    @Test
    @DataSet(value = BASE_DIR + "testCreateItem_dataset.xml")
    @ExpectedDataSet(value = BASE_DIR + "testCreateItem_expectedDataset.xml", orderBy = "content")
    void testCreateItem() {
        mockClockToReturnDateTime(clock, LocalDateTime.parse("2021-05-11T00:33:53.409656900"));
        InputStream testPayload = getResourceAsStream(BASE_DIR + "testCreateItem_payload.json");
        given()
                .when()
                .contentType(APPLICATION_JSON).body(testPayload)
                .post("/todo-items")
                .then()
                .log().ifValidationFails()
                .statusCode(CREATED.getStatusCode());
    }

    /**
     * test for {@link TodoResource#updateItem(com.example.dto.UpdateTodoItemRequest, java.util.UUID)}
     */
    @Test
    @DataSet(value = BASE_DIR + "testUpdateItem_dataset.xml")
    @ExpectedDataSet(value = BASE_DIR + "testUpdateItem_expectedDataset.xml", orderBy = "content")
    void testUpdateItem() {
        String id = "6f94d587-e3c6-4faf-b9a7-334a69fdf0e9";
        InputStream testPayload = getResourceAsStream(BASE_DIR + "testUpdateItem_payload.json");
        given()
                .when()
                .contentType(APPLICATION_JSON).body(testPayload)
                .put("/todo-items/" + id)
                .then()
                .log().ifValidationFails()
                .statusCode(OK.getStatusCode());
    }
}