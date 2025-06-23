package com.shopping.API.requests;

import com.shopping.API.POJO.Deserialization.DeleteProductResponse;
import com.shopping.utils.LogsManager;
import com.shopping.utils.Validations;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static com.shopping.API.utils.Builder.OrderRequestSpec;
import static io.restassured.RestAssured.*;

public class DeleteProduct {
    String StatusCode;
    RequestSpecification requestSpecification;
    String deleteOrderEndpoint = "/api/ecom/product/delete-product/";
    DeleteProductResponse deleteProductResponse;
    Response response;

    public DeleteProduct() {
        // Constructor
    }

    @Step("Set request specification")
    public DeleteProduct setRequestSpecification(String token) {
            this.requestSpecification = given().spec(OrderRequestSpec(token));
        return this;
    }

    @Step("Send delete order request")
    public DeleteProduct hitEndPoint(String productId) {
        response = requestSpecification.delete(deleteOrderEndpoint + productId);
        LogsManager.info(requestSpecification.log().all().toString() + "\n" + deleteOrderEndpoint);
        LogsManager.info("Response body : " + response.getBody().asString());
        return this;
    }

    @Step("Deserialize delete order response")
    public DeleteProduct deserializeResponse() {
        deleteProductResponse = response.then().extract().response().as(DeleteProductResponse.class);
        StatusCode = String.valueOf(response.getStatusCode());
        LogsManager.info("Status code: " + StatusCode);
        LogsManager.info("Delete order response deserialized successfully");
        LogsManager.info("Delete order response Message: " + deleteProductResponse.getMessage());
        return this;
    }


    // Validation methods
    @Step("Validate status code")
    public DeleteProduct validateStatusCode(String expectedStatusCode) {
        Validations.validateEquals(StatusCode, expectedStatusCode, "Delete order request failed");
        LogsManager.info("Status code validation passed: " + StatusCode);
        return this;
    }

    @Step("Validate response message")
    public void validateResponseMessage() {
        Validations.validateEquals(deleteProductResponse.getMessage(), "Product Deleted Successfully", "Delete order response message validation failed");
        LogsManager.info("Response message validation passed: " + deleteProductResponse.getMessage());
    }
}
