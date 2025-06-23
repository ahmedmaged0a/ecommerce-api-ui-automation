package com.shopping.API.requests;

import com.shopping.API.POJO.Deserialization.LoginResponse;
import com.shopping.API.POJO.Serialization.LoginRequest;
import com.shopping.API.utils.Builder;
import com.shopping.utils.*;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.*;

public class Login {
    //Variables
    String statusCode;
    LoginRequest loginRequest = new LoginRequest();
    String loginEndpoint = "/api/ecom/auth/login";
    RequestSpecification requestSpecification;
    Response response;
    LoginResponse loginResponse;

    public Login() {

    }

    @Step("Enter login data: {userEmail}, {userPassword}")
    public Login enterLoginData(String userEmail, String userPassword) {
        loginRequest.setUserEmail(userEmail)
                .setUserPassword(userPassword);
        return this;
    }

    @Step("Send login request")
    public Login requestLogin() {
        requestSpecification = given()
                .spec(Builder.getLoginRequestSpecification())
                .body(loginRequest);
        LogsManager.info("Login request sent with body: " + loginRequest.toString());
        return this;
    }

    @Step("Hit login endpoint")
    public Login hitEndPoint() {
        LogsManager.info(requestSpecification.log().all().toString() + "\n" + loginEndpoint);
        response = requestSpecification.post(loginEndpoint);
        return this;

    }

    @Step("Deserialize login response")
    public Login deserializeResponse() {
        loginResponse = response.then().extract()
                .response()
                .as(LoginResponse.class);

        LogsManager.info(
                response
                        .then()
                        .extract()
                        .response()
                        .asString());

        statusCode = String.valueOf(response.getStatusCode());
        LogsManager.info("Login response deserialized successfully");
        LogsManager.info("Login response status code: " + statusCode);
        LogsManager.info("Login response Message: " + loginResponse.getMessage());
        return this;
    }

    public String getToken() {
        LogsManager.info("Login token: " + loginResponse.getToken());
        return loginResponse.getToken();
    }
    public String getUserId() {
        LogsManager.info("Login userId: " + loginResponse.getUserId());
        return loginResponse.getUserId();
    }

    //Validation methods
    // Validate if the response status code is 200
    @Step("Validate login response status code")
    public Login validateStatusCode() {
        Validations.validateEquals(statusCode, "200", "Login request failed");
        return this;
    }

    @Step("Validate login response body")
    public void validateResponseBody() {
        LogsManager.info(loginResponse.getUserId());
        Validations.validateNotNull(response.getBody().asString(), "Login response body is null");
        Validations.validateEquals(loginResponse.getMessage(), "Login Successfully", "Login response body does not match");
        LogsManager.info("Login response body validated successfully");
    }

}
