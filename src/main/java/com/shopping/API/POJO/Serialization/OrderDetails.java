package com.shopping.API.POJO.Serialization;

public class OrderDetails {
    String country;
    String productOrderedId;

    public String getCountry() {
        return country;
    }
    public String getProductOrderedId() {
        return productOrderedId;
    }
    public void setCountry(String country) {
        this.country = country;
    }
    public void setProductOrderedId(String productOrderedId) {
        this.productOrderedId = productOrderedId;
    }
}
