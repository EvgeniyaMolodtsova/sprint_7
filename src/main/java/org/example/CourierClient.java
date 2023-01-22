package org.example;

import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class CourierClient extends Client {

    public ValidatableResponse create(Courier courier) {
        return given()
                .spec(getSpec())
                .body(courier)
                .when()
                .post("api/v1/courier")
                .then();
    }

    public ValidatableResponse login(CourierCredentials courierCredentials) {
        return given()
                .spec(getSpec())
                .body(courierCredentials)
                .when()
                .post("/api/v1/courier/login")
                .then();
    }

    public ValidatableResponse delete(int id) {
        return given()
                .spec(getSpec())
                .when()
                .delete("/api/v1/courier/{id}", id)
                .then();
    }
}
