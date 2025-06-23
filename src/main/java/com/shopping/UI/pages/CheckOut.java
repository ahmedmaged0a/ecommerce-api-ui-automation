package com.shopping.UI.pages;

import com.shopping.utils.ElementActions;
import com.shopping.utils.Validations;
import org.openqa.selenium.*;

public class CheckOut {
    private final WebDriver driver;

    //Locators for the checkout page elements can be added here
    private final By selectCountry = By.cssSelector("input[placeholder='Select Country']");
    private final By placeOrderButton = By.cssSelector(".btnn.action__submit.ng-star-inserted");
    private final By successMessage = By.xpath("//div[@aria-label='Order Placed Successfully']");
    private final By selectCountryDropdown = By.cssSelector("span[class='ng-star-inserted']");

    public CheckOut(WebDriver driver) {
        this.driver = driver;
    }
    // Method to select a country
    public CheckOut selectCountry(String country) {
        ElementActions.sendDataToElement(driver, selectCountry, country);
        ElementActions.selectOptionFromDropdown(driver,selectCountryDropdown);
        return this;
    }
    // Method to place an order
    public CheckOut placeOrder() {
        ElementActions.clickElement(driver, placeOrderButton);
        return this;
    }
    // Method to get the success message after placing an order
    public String getSuccessMessage() {
        return ElementActions.getElementText(driver, successMessage);
    }

    //Assertions can be added here to validate the checkout process
    public void verifyCheckoutSuccess() {
        Validations.validateContains(getSuccessMessage(), "Order Placed Successfully", "Checkout was not successful");
    }
}
