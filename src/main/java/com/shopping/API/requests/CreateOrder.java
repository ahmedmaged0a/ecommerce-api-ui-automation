package com.shopping.API.requests;

import com.shopping.API.POJO.Deserialization.CreateOrderResponse;
import com.shopping.API.POJO.Serialization.OrderDetails;
import com.shopping.API.POJO.Serialization.Orders;
import com.shopping.API.utils.Builder;
import com.shopping.utils.JsonUtils;
import com.shopping.utils.LogsManager;
import com.shopping.utils.Validations;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.*;

public class CreateOrder {
    String statusCode;
    Response response;
    RequestSpecification requestSpecification;
    String createOrderEndpoint = "/api/ecom/order/create-order";
    OrderDetails orderDetails = new OrderDetails();
    Orders orders = new Orders();
    CreateOrderResponse createOrderResponse;
    List<OrderDetails> orderDetailsList = new ArrayList<>();

    public CreateOrder(JsonUtils testData) {
        // Constructor
        orderDetails.setCountry(testData.getJsonData("API.CreateOrder.country"));
        orderDetailsList.add(orderDetails);
        orders.setOrders(orderDetailsList);
    }

    @Step("Set request specification")
    public CreateOrder setRequestSpecification(String token, String productId) {
        orderDetails.setProductOrderedId(productId);
        this.requestSpecification = given().spec(Builder.OrderRequestSpec(token))
                .body(orders);
        return this;
    }

    @Step("Send create order request")
    public CreateOrder hitEndPoint() {
        response = requestSpecification.post(createOrderEndpoint);
        LogsManager.info(requestSpecification.log().all().toString() + "\n" + createOrderEndpoint);
        LogsManager.info("Response body : " + response.getBody().asString());
        return this;
    }

    @Step("Deserialize create order response")
    public CreateOrder deserializeResponse() {
        createOrderResponse = response.then().extract().response().as(CreateOrderResponse.class);
        statusCode = String.valueOf(response.getStatusCode());
        LogsManager.info("Status code: " + statusCode);
        LogsManager.info("Create order response deserialized successfully");
        LogsManager.info("Create order response Message: " + createOrderResponse.getMessage());
        return this;
    }

    // Validation methods
    @Step("Validate status code")
    public CreateOrder validateStatusCode(String expectedStatusCode) {
        Validations.validateEquals(statusCode, expectedStatusCode, "Create order request failed");
        LogsManager.info("Status code validated successfully: " + statusCode);
        return this;
    }

    @Step("Validate create order response")
    public void validateResponseBody() {
        Validations.validateNotNull(response.getBody().asString(), "Create order response body is null");
        Validations.validateEquals(createOrderResponse.getMessage(), "Order Placed Successfully", "Create order response message does not match");
        LogsManager.info("Create order response validated successfully");
    }

    public String getProductOrderId() {
        return createOrderResponse.getOrders().getFirst();
    }
}
