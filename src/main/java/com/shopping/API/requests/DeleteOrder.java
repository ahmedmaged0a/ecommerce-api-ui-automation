package com.shopping.API.requests;

import com.shopping.API.POJO.Deserialization.DeleteOrderResponse;
import com.shopping.utils.LogsManager;
import com.shopping.utils.Validations;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static com.shopping.API.utils.Builder.OrderRequestSpec;
import static io.restassured.RestAssured.*;

public class DeleteOrder {
    String StatusCode;
    RequestSpecification requestSpecification;
    String deleteOrderEndpoint = "/api/ecom/order/delete-order/";
    DeleteOrderResponse deleteOrderResponse;
    Response response;

    public DeleteOrder() {
        // Constructor
    }

    @Step("Set request specification")
    public DeleteOrder setRequestSpecification(String token) {
        this.requestSpecification = given().spec(OrderRequestSpec(token));
        return this;
    }

    @Step("Send delete order request")
    public DeleteOrder hitEndPoint(String orderId) {
        response = requestSpecification.delete(deleteOrderEndpoint+ orderId);
        LogsManager.info(requestSpecification.log().all().toString() + "\n" + deleteOrderEndpoint);
        LogsManager.info("Response body : " + response.getBody().asString());
        return this;
    }

    @Step("Deserialize delete order response")
    public DeleteOrder deserializeResponse() {
        deleteOrderResponse = response.then().extract().response().as(DeleteOrderResponse.class);
        StatusCode = String.valueOf(response.getStatusCode());
        LogsManager.info("Status code: " + StatusCode);
        LogsManager.info("Delete order response deserialized successfully");
        LogsManager.info("Delete order response Message: " + deleteOrderResponse.getMessage());
        return this;
    }


    // Validation methods
    @Step("Validate status code")
    public DeleteOrder validateStatusCode(String expectedStatusCode) {
        Validations.validateEquals(StatusCode, expectedStatusCode, "Delete order request failed");
        LogsManager.info("Status code validation passed: " + StatusCode);
        return this;
    }

    @Step("Validate response message")
    public void validateResponseMessage() {
        Validations.validateEquals(deleteOrderResponse.getMessage(), "Orders Deleted Successfully", "Delete order response message validation failed");
        LogsManager.info("Response message validation passed: " + deleteOrderResponse.getMessage());
    }
}
