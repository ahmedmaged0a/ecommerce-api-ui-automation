package com.shopping.API.requests;

import com.shopping.utils.LogsManager;
import com.shopping.utils.Validations;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static com.shopping.API.utils.Builder.OrderRequestSpec;
import static io.restassured.RestAssured.*;

public class GetorderDetails {
    String StatusCode;
    Response response;
    RequestSpecification requestSpecification;
    String getOrderDetailsEndpoint = "api/ecom/order/get-orders-details";

    public GetorderDetails() {
        // Constructor
    }

    @Step("Set request specification")
    public GetorderDetails setRequestSpecification(String token, String orderId) {
        this.requestSpecification = given()
                .spec(OrderRequestSpec(token))
                .queryParam("id", orderId);
        return this;
    }

    @Step("Send get order details request")
    public GetorderDetails hitEndPoint() {
        response = requestSpecification.get(getOrderDetailsEndpoint);
        LogsManager.info(requestSpecification.log().all().toString() + "\n" + getOrderDetailsEndpoint);
        LogsManager.info("Response body : " + response.getBody().asString());
        StatusCode = String.valueOf(response.getStatusCode());
        return this;
    }


    //Validation methods
    @Step("Validate status code")
    public GetorderDetails validateStatusCode(String expectedStatusCode) {
        LogsManager.info("Status code: " + StatusCode);
        Validations.validateEquals(StatusCode, expectedStatusCode, "Get order details request failed");
        LogsManager.info("Status code validation passed: " + StatusCode);
        return this;
    }

    @Step("Validate response body")
    public GetorderDetails validateResponseBody() {
        Validations.validateNotNull(response.getBody().asString(), "Get order details response body is null");
        LogsManager.info("Response body validation passed: " + response.getBody().asString());
        return this;
    }
}
