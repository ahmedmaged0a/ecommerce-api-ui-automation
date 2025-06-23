package com.shopping.UI.pages;

import com.shopping.utils.ElementActions;
import com.shopping.utils.JsonUtils;
import com.shopping.utils.Validations;
import com.shopping.utils.Waits;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class Products {
    private JsonUtils testData;
    private final WebDriver driver;
    //Locators for the product page
    private final By searchBox = By.cssSelector("div[class='py-2 border-bottom ml-3'] input[placeholder='search']");
    private final By addToCartFromViewPage = By.cssSelector(".btn.btn-primary");
    private final By addToCartFromProductPage = By.cssSelector(".btn.w-10.rounded");
    private final By cardImg = By.cssSelector(".card-img-top");
    private final By viewCart = By.xpath("//button[normalize-space()='View']");
    private final By successMessage = By.xpath("//div[@aria-label='Product Added To Cart']");
    private final By failureMessage = By.xpath("//div[@aria-label='No Products Found']");

    // Constructor to initialize the WebDriver instance
    public Products(WebDriver driver) {
        this.driver = driver;
    }

    // Method to search for a product
    public Products searchProduct(String productName) {
        ElementActions.searchForElements(driver, searchBox, productName);
        return this;
    }

    // Method to add a product to the cart
    public Products addProductToCartFromProduct() {
        Waits.waitForElementToBeVisible(driver, cardImg);
        ElementActions.clickElement(driver, addToCartFromProductPage);
        return this;
    }

    // Method to add a product to the cart from the product view page
    public Products addProductToCartFromView() {
        ElementActions.clickElement(driver, addToCartFromViewPage);
        return this;
    }

    // Method to view the cart
    public Products viewCart() {
        ElementActions.clickElement(driver, viewCart);
        return this;
    }

    // Method to get the success message after adding a product to the cart
    public String getSuccessMessage() {
        return ElementActions.getElementText(driver, successMessage);
    }

    // Method to get the failure message if no products are found
    public String getFailureMessage() {
        return ElementActions.getElementText(driver, failureMessage);
    }

    //Validation method to check if the product is added to the cart
    public void verifyProductAddedToCart() {
        Validations.validateContains(getSuccessMessage(), "Added To Cart", "Product was not added to the cart");
    }

    //Validation method to check if the product is not found
    public void verifyProductNotFound() {
        Validations.validateContains(getFailureMessage(), "No Products Found", "Product was found when it should not have been");
    }
}
