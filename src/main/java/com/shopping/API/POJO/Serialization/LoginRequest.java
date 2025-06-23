package com.shopping.API.POJO.Serialization;

public class LoginRequest {
    String userEmail;
    String userPassword;

    public String getUserEmail() {
        return userEmail;
    }
    public String getUserPassword() {
        return userPassword;
    }
    public LoginRequest setUserEmail(String userEmail) {
        this.userEmail = userEmail;
        return this;
    }
    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }
}
