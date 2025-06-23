package com.shopping.API.utils;
import com.shopping.utils.PropertiesUtils;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.*;
import io.restassured.specification.*;


public class Builder {
    static String baseURI = PropertiesUtils.getPropertyValue("baseURI");
    private Builder() {
        // Private constructor to prevent instantiation
    }

    public static RequestSpecification getLoginRequestSpecification() {
        return new RequestSpecBuilder().setBaseUri(baseURI)
                .setContentType(ContentType.JSON)
                .build();
    }
    public static RequestSpecification getAddProductRequestSpecification(String token) {
        return new RequestSpecBuilder().setBaseUri(baseURI)
                .addHeader("Authorization",token)
                .build();
    }
    public static RequestSpecification OrderRequestSpec(String token) {
        return new RequestSpecBuilder().setBaseUri(baseURI)
                .addHeader("Authorization",token)
                .setContentType(ContentType.JSON)
                .build();
    }

}

