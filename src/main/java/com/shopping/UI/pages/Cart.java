package com.shopping.UI.pages;

import com.shopping.utils.ElementActions;
import com.shopping.utils.Validations;
import org.openqa.selenium.*;


public class Cart {
    private final WebDriver driver;
    //Locators for the cart page elements can be defined here
    private final By emptyCartMessage = By.cssSelector("div[aria-label='No Product in Your Cart']");
    private final By cartItems = By.xpath("//button[@routerlink='/dashboard/cart']");
    private final By productName = By.cssSelector("div[class='cartSection'] h3");
    private final By buyNowButton = By.xpath("//button[normalize-space()='Buy Now']");
    private final By checkOutButton = By.xpath("//button[normalize-space()='Checkout']");
    private final By removeButton = By.cssSelector(".fa.fa-trash-o");

    public Cart(WebDriver driver) {
        this.driver = driver;
    }

    public Cart navigateToCart() {
        ElementActions.clickElement(driver, cartItems);
        return this;
    }

    public String getEmptyCartMessage() {
        return ElementActions.getElementText(driver, emptyCartMessage);
    }

    public Cart removeProductFromCart() {
        ElementActions.clickElement(driver, removeButton);
        return this;
    }

    public String getProductName() {
        return ElementActions.getElementText(driver, productName);
    }

    public Cart clickBuyNowButton() {
        ElementActions.clickElement(driver, buyNowButton);
        return this;
    }

    public Cart clickCheckOutButton() {
        ElementActions.clickElement(driver, checkOutButton);
        return this;
    }


    //Validation methods can be added here to check if the cart is empty, if items are present, etc.
    public void verifyProductRemovedFromCart() {
        Validations.validateContains(getEmptyCartMessage(), "No Product in Your Cart", "Cart was not empty");
    }
    public Cart verifyCartHasTheCorrectProduct(String expectedProductName) {
        String actualProductName = getProductName();
        Validations.validateEquals(actualProductName, expectedProductName, "Cart does not contain the expected product");
        return this;
    }

}
