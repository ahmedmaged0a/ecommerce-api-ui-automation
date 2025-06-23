package com.shopping.API.requests;

import com.shopping.API.POJO.Deserialization.AddProductResponse;
import com.shopping.API.utils.Builder;
import com.shopping.utils.JsonUtils;
import com.shopping.utils.LogsManager;
import com.shopping.utils.Validations;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;


import java.io.File;

import static io.restassured.RestAssured.*;

public class AddProduct {
    String statusCode;
    RequestSpecification requestSpecification;
    Response response;
    String addProductEndpoint = "/api/ecom/product/add-product";
    AddProductResponse addProductResponse;
    public AddProduct() {

    }

    public AddProduct requestAddProduct(JsonUtils testData, String token,String userId) {
        this.requestSpecification = given().spec(Builder.getAddProductRequestSpecification(token))
                .param("productName", testData.getJsonData("API.Product.productName"))
                .param("productAddedBy", userId)
                .param("productCategory", testData.getJsonData("API.Product.productCategory"))
                .param("productSubCategory", testData.getJsonData("API.Product.productSubCategory"))
                .param("productPrice", testData.getJsonData("API.Product.productPrice"))
                .param("productDescription", testData.getJsonData("API.Product.productDescription"))
                .param("productFor", testData.getJsonData("API.Product.productFor"))
                .multiPart("productImage", new File("src/test/resources/pic.jpg"));
        return this;
    }

    public AddProduct hitEndPoint() {
        response = requestSpecification.post(addProductEndpoint);
        LogsManager.info(requestSpecification.log().all().toString() + "\n" + addProductEndpoint);
        LogsManager.info("Response body : " + response.getBody().asString());
        return this;
    }
    public AddProduct deserializeResponse() {
        addProductResponse = response.then().extract().response().as(AddProductResponse.class);
        statusCode = String.valueOf(response.getStatusCode());
        LogsManager.info("Status code: " + statusCode);
        LogsManager.info("Add product response deserialized successfully");
        LogsManager.info("Add product response Message: " + addProductResponse.getMessage());
        return this;
    }


    //Validation methods
    @Step("Validate status code")
    public AddProduct validateStatusCode(String expectedStatusCode) {
        Validations.validateEquals(statusCode, expectedStatusCode, "Add product request failed");
        LogsManager.info("Status code validation passed: " + statusCode);
        return this;
    }
    public void validateResponseBody() {
        Validations.validateEquals(addProductResponse.getMessage(), "Product Added Successfully", "Add product response message validation failed");
        LogsManager.info("Response body validation passed: " + addProductResponse.getMessage());
    }

    public String getProductId() {
        return  addProductResponse.getProductId();
    }
}
