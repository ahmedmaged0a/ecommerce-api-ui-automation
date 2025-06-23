package com.shopping.utils;


import io.qameta.allure.Step;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static com.shopping.utils.Scrolling.scrollUp;
import static com.shopping.utils.Waits.*;

public class ElementActions {
    private ElementActions() {
        // Private constructor to prevent instantiation
    }

    // Add methods for common element actions like click, sendKeys, etc.

    // Method to click on an element
    @Step("Clicking on element: {locator}")
    public static void clickElement(WebDriver driver, By locator) {
        waitPresenceOfElementLocated(driver, locator);
        handleStaleElementReferenceException(driver, locator);
        waitForElementToBeClickable(driver, locator);
        Scrolling.scrollToElement(driver, locator);
        try {
            findElement(driver, locator).click();
        } catch (StaleElementReferenceException e) {
            // If the element is stale, find it again and click
            findElement(driver, locator).click();
        }
    }

    // Method to send keys to an element
    @Step("Sending data to element: {locator} with data: {Data}")
    public static void sendDataToElement(WebDriver driver, By locator, String Data) {
        waitPresenceOfElementLocated(driver, locator);
        handleStaleElementReferenceException(driver, locator);
        Waits.waitForElementToBeVisible(driver, locator);
        Scrolling.scrollToElement(driver, locator);

        try {
            findElement(driver, locator).sendKeys(Keys.CONTROL + "a");
            findElement(driver, locator).sendKeys(Keys.DELETE);
            findElement(driver, locator).sendKeys(Data);
        } catch (StaleElementReferenceException e) {
            findElement(driver, locator).sendKeys(Keys.CONTROL + "a");
            findElement(driver, locator).sendKeys(Keys.DELETE);
            findElement(driver, locator).sendKeys(Data);
            ;
        }
        // Log the action
        LogsManager.info("Sending data to element: " + locator + " with data: " + Data);
    }

    // Method to send keys to an element
    @Step("Sending data to element: {locator} with data: {Data}")
    public static void searchForElements(WebDriver driver, By locator, String Data) {
        waitPresenceOfElementLocated(driver, locator);
        handleStaleElementReferenceException(driver, locator);
        Waits.waitForElementToBeVisible(driver, locator);
        Scrolling.scrollToElement(driver, locator);

        try {
            findElement(driver, locator).sendKeys(Keys.CONTROL + "a");
            findElement(driver, locator).sendKeys(Keys.DELETE);
            findElement(driver, locator).sendKeys(Data);
            findElement(driver, locator).click();
            findElement(driver, locator).sendKeys(Keys.ENTER);
        } catch (StaleElementReferenceException e) {
            findElement(driver, locator).sendKeys(Keys.CONTROL + "a");
            findElement(driver, locator).sendKeys(Keys.DELETE);
            findElement(driver, locator).sendKeys(Data);
            findElement(driver, locator).click();
            findElement(driver, locator).sendKeys(Keys.ENTER);
        }

        // Log the action
        LogsManager.info("Searching for element: " + locator + " with data: " + Data);
    }

    // Method to get text from an element
    @Step("Getting text from element: {locator}")
    public static String getElementText(WebDriver driver, By locator) {
        String text;
        waitPresenceOfElementLocated(driver, locator);
        handleStaleElementReferenceException(driver, locator);
        Waits.waitForElementToBeVisible(driver, locator);
        Scrolling.scrollToElement(driver, locator);
        // Log the action
        LogsManager.info("Getting text from element: " + locator, " with data: " + findElement(driver, locator).getText());

        try {
            text = findElement(driver, locator).getText();
        } catch (StaleElementReferenceException e) {
            text = findElement(driver, locator).getText();
        }
        return text;
    }

    // Method to find an element
    @Step("Finding element: {locator}")
    public static WebElement findElement(WebDriver driver, By locator) {
        waitPresenceOfElementLocated(driver, locator);
        handleStaleElementReferenceException(driver, locator);
        // Log the action
        LogsManager.info("Finding element: " + locator.toString());
        // Wait for the element to be present and return it
        return driver.findElement(locator);
    }

    @Step("Select option from dropdown: {locator} with value: {value}")
    public static void selectOptionFromDropdown(WebDriver driver, By locator) {
        waitPresenceOfElementLocated(driver, locator);
        handleStaleElementReferenceException(driver, locator);
        Scrolling.scrollToElement(driver, locator);
        findElement(driver, locator).click();
        LogsManager.info("Selected option from dropdown: " + locator.toString());
    }
}
