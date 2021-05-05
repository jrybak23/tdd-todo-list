package com.example.controllers;

import com.example.config.IntegrationTestProfile;
import com.github.database.rider.cdi.api.DBRider;
import com.github.database.rider.core.api.dataset.DataSet;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.TestProfile;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
@TestProfile(IntegrationTestProfile.class)
@DBRider
class TodoResourceTest {
    @Test
    @DataSet(value = "todo-items.xml")
    void testGetItems() {
        given()
                .when().get("/todo-items")
                .then()
                .statusCode(200)
                .body(is("[{\"content\":\"buy eggs\",\"dateTimeCreated\":\"2021-05-04T17:41:50\",\"id\":\"8aa23897-89dd-4d08-8638-8fb39d49dcfb\"}]"));
    }
}