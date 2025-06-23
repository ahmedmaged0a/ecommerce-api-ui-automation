package com.shopping.UI.pages;

import com.shopping.utils.ElementActions;
import com.shopping.utils.PropertiesUtils;
import com.shopping.utils.Validations;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class Login {
    private final WebDriver driver;

    // Locators for the login page elements
    private final By emailInput = By.id("userEmail");
    private final By passwordInput = By.id("userPassword");
    private final By loginButton = By.id("login");

    private final By successMessage = By.cssSelector("div[aria-label='Login Successfully']");
    private final By failureMessage = By.cssSelector("div[aria-label='Incorrect email or password.']");
    private final By emailRequired = By.xpath("//div[contains(text(),'Email is required')]");
    private final By passwordRequired = By.xpath("//div[contains(text(),'Password is required')]");

    public Login(WebDriver driver) {
        this.driver = driver;
    }
    // Method to navigate to the login page
    @Step("Navigate to login page")
    public void navigateToLoginPage() {
        driver.get(PropertiesUtils.getPropertyValue("baseURI")+PropertiesUtils.getPropertyValue("LoginPath"));
    }
    // Methods to perform actions on the login page
    @Step("Enter user email: {email}")
    public Login enterUserEmail(String email) {
        ElementActions.sendDataToElement(driver, emailInput, email);
        return this;
    }
    @Step("Enter user password")
    public Login enterUserPassword(String password) {
        ElementActions.sendDataToElement(driver, passwordInput, password);
        return this;
    }
    @Step("Click the login button")
    public Login clickLoginButton() {
        ElementActions.clickElement(driver, loginButton);
        return this;
    }
    public void login(String email, String password) {
                enterUserEmail(email)
                .enterUserPassword(password)
                .clickLoginButton();
    }
    @Step("Get success message")
    public String getSuccessMessage() {
        return ElementActions.getElementText(driver, successMessage);
    }
    @Step("Get failure message")
    public String getFailureMessage() {
        return ElementActions.getElementText(driver, failureMessage);
    }
    @Step("Get email required message")
    public String getEmailRequiredMessage() {
        return ElementActions.getElementText(driver, emailRequired);
    }
    @Step("Get password required message")
    public String getPasswordRequiredMessage() {
        return ElementActions.getElementText(driver, passwordRequired);
    }

    //Validation methods
    @Step("Validate successful login")
    public void verifyLoginSuccess() {
        Validations.validateContains(getSuccessMessage(), "Login Successfully", "Login was not successful");
    }
    @Step("Validate failed login")
    public void verifyLoginFailure() {
        Validations.validateContains(getFailureMessage(), "Incorrect email or password.", "Login was successful, but it should have failed");
    }
    @Step("Validate email required message")
    public void verifyEmptyEmailError() {
        Validations.validateContains(getEmailRequiredMessage(), "*Email is required", "Email required message did not match");
    }
    @Step("Validate password required message")
    public void verifyEmptyPasswordError() {
        Validations.validateContains(getPasswordRequiredMessage(), "*Password is required", "Password required message did not match");
    }


}
