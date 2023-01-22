package org.example;

import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class OrderClient extends Client{
    public ValidatableResponse createOrder(Order order) {
        return given()
                .spec(getSpec())
                .body(order)
                .when()
                .post("/api/v1/orders")
                .then();
    }

    public ValidatableResponse cancelOrder(int track) {
        return given()
                .spec(getSpec())
                .queryParam("track", track)
                .when()
                .put("/api/v1/orders/cancel")
                .then();
    }

    public ValidatableResponse getOrderList(OrderList orderList) {
        return given()
                .spec(getSpec())
                .body(orderList)
                .when()
                .get("/api/v1/orders")
                .then();
    }
}
