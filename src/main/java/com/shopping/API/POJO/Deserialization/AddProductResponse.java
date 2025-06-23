package com.shopping.API.POJO.Deserialization;

public class AddProductResponse {
    String productId;
    String message;

    public String getProductId() {
        return productId;
    }
    public String getMessage() {
        return message;
    }
    public void setProductId(String productId) {
        this.productId = productId;
    }
    public void setMessage(String message) {
        this.message = message;
    }
}
