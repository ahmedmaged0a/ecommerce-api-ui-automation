package com.shopping.tests.UI;

import com.shopping.UI.drivers.DriverManager;
import com.shopping.UI.listeners.TestNGListeners;
import com.shopping.UI.pages.Cart;
import com.shopping.UI.pages.CheckOut;
import com.shopping.UI.pages.Login;
import com.shopping.UI.pages.Products;
import com.shopping.utils.BrowserActions;
import com.shopping.utils.JsonUtils;
import io.qameta.allure.*;
import io.qameta.allure.testng.Tag;
import org.testng.ITestResult;
import org.testng.annotations.*;


import static com.shopping.utils.PropertiesUtils.getPropertyValue;

@Listeners(TestNGListeners.class)
@Epic("UI Tests")
@Tag("UI")
public class E2eTests {
    private JsonUtils testData;

    @DataProvider(name = "productDataProvider")
    public Object[][] dataProviderMethod() {
        return new Object[][]{
                {testData.getJsonData("ProductData.validProduct[0].productName")},
                {testData.getJsonData("ProductData.validProduct[1].productName")},
                {testData.getJsonData("ProductData.validProduct[2].productName")}
        };
    }

    @BeforeClass
    public void setup() {
        // Initialize the JsonUtils instance with the path to the test data file
        testData = new JsonUtils("test-data");
    }

    @BeforeMethod
    public void preConditions() {
        String browserName = getPropertyValue("browserType");
        DriverManager.createDriverInstance(browserName);
        new Login(DriverManager.getDriver()).navigateToLoginPage();
    }

    @Tag("Smoke")
    @Epic("UI Tests")
    @Severity(SeverityLevel.CRITICAL)
    @Feature("Login")
    @Test(
            groups = {"Smoke", "Regression"},
            description = "Verify that the user can log in successfully with valid credentials",
            priority = 1
    )
    public void loginSuccessfullyTC01() {
        new Login(DriverManager.getDriver())
                .enterUserEmail(testData.getJsonData("LoginData.validCredentials.userEmail"))
                .enterUserPassword(testData.getJsonData("LoginData.validCredentials.userPassword"))
                .clickLoginButton()
                .verifyLoginSuccess();
    }

    @Tag("Regression")
    @Epic("UI Tests")
    @Feature("Login")
    @Test(
            groups = {"Regression"},
            description = "Verify that the user cannot log in with invalid credentials",
            priority = 2
    )
    public void loginWithInvalidCredentialsTC02() {
        new Login(DriverManager.getDriver())
                .enterUserEmail(testData.getJsonData("LoginData.validCredentials.userEmail"))
                .enterUserPassword(testData.getJsonData("LoginData.invalidCredentials.userPassword"))
                .clickLoginButton()
                .verifyLoginFailure();
    }

    @Tag("Regression")
    @Epic("UI Tests")
    @Feature("Login")
    @Test(
            groups = {"Regression"},
            description = "Verify that the user cannot log in with empty Email",
            priority = 3
    )
    public void loginWithEmptyEmailTC03() {
        new Login(DriverManager.getDriver())
                .enterUserEmail("")
                .enterUserPassword(testData.getJsonData("LoginData.validCredentials.userPassword"))
                .clickLoginButton()
                .verifyEmptyEmailError();
    }

    @Tag("Regression")
    @Epic("UI Tests")
    @Feature("Login")
    @Test(
            groups = {"Regression"},
            description = "Verify that the user cannot log in with empty Password",
            priority = 4
    )
    public void loginWithEmptyPasswordTC04() {
        new Login(DriverManager.getDriver())
                .enterUserEmail(testData.getJsonData("LoginData.validCredentials.userEmail"))
                .enterUserPassword("")
                .clickLoginButton()
                .verifyEmptyPasswordError();
    }

    @Tag("Smoke")
    @Epic("UI Tests")
    @Severity(SeverityLevel.CRITICAL)
    @Feature("Add to Cart")
    @Test(
            groups = {"Smoke", "Regression"},
            description = "Verify that the user can add an item to the cart",
            priority = 2,
            dataProvider = "productDataProvider"
    )
    public void addItemToCartFromProductTC05(String productName) {
        //Login
        new Login(DriverManager.getDriver())
                .login(testData.getJsonData("LoginData.validCredentials.userEmail"),
                        testData.getJsonData("LoginData.validCredentials.userPassword"));

        new Products(DriverManager.getDriver())
                .searchProduct(productName)
                .addProductToCartFromProduct()
                .verifyProductAddedToCart();

    }

    @Tag("Smoke")
    @Epic("UI Tests")
    @Severity(SeverityLevel.CRITICAL)
    @Feature("Add to Cart")
    @Test(
            groups = {"Smoke", "Regression"},
            description = "Verify that the user can add an item to the cart from view product page",
            priority = 2,
            dataProvider = "productDataProvider"
    )
    public void addItemToCartFromViewProductTC06(String productName) {
        //Login
        new Login(DriverManager.getDriver())
                .login(testData.getJsonData("LoginData.validCredentials.userEmail"),
                        testData.getJsonData("LoginData.validCredentials.userPassword"));

        new Products(DriverManager.getDriver())
                .searchProduct(productName)
                .viewCart()
                .addProductToCartFromView()
                .verifyProductAddedToCart();
    }

    //verify items in the cart
    @Tag("Regression")
    @Epic("UI Tests")
    @Severity(SeverityLevel.NORMAL)
    @Feature("Add to Cart")
    @Test(
            groups = {"Regression"},
            description = "Verify that the user can view items in the cart",
            priority = 4,
            dataProvider = "productDataProvider"
    )
    public void viewCartItemsTC08(String productName) {
        new Login(DriverManager.getDriver())
                .login(testData.getJsonData("LoginData.validCredentials.userEmail"),
                        testData.getJsonData("LoginData.validCredentials.userPassword"));

        new Products(DriverManager.getDriver())
                .searchProduct(productName)
                .addProductToCartFromProduct()
                .verifyProductAddedToCart();

        new Cart(DriverManager.getDriver())
                .navigateToCart()
                .verifyCartHasTheCorrectProduct(productName);

    }

    //verify items in the cart after removing an item
    @Tag("Regression")
    @Epic("UI Tests")
    @Severity(SeverityLevel.NORMAL)
    @Feature("Add to Cart")
    @Test(
            groups = {"Regression"},
            description = "Verify that the user can remove an item from the cart",
            priority = 5)
    public void removeItemFromCartTC09() {
        new Login(DriverManager.getDriver())
                .login(testData.getJsonData("LoginData.validCredentials.userEmail"),
                        testData.getJsonData("LoginData.validCredentials.userPassword"));

        new Products(DriverManager.getDriver())
                .searchProduct(testData.getJsonData("ProductData.validProduct[0].productName"))
                .addProductToCartFromProduct()
                .verifyProductAddedToCart();

        new Cart(DriverManager.getDriver())
                .navigateToCart()
                .removeProductFromCart()
                .verifyProductRemovedFromCart();

    }

    //Buy items in the cart
    @Tag("Smoke")
    @Epic("UI Tests")
    @Severity(SeverityLevel.CRITICAL)
    @Feature("Checkout")
    @Test(
            groups = {"Smoke", "Regression"},
            description = "Verify that the user can buy items in the cart",
            priority = 6,
            dataProvider = "productDataProvider"
    )
    public void buyItemsInCartTC10(String productName) {
        new Login(DriverManager.getDriver())
                .login(testData.getJsonData("LoginData.validCredentials.userEmail"),
                        testData.getJsonData("LoginData.validCredentials.userPassword"));

        new Products(DriverManager.getDriver())
                .searchProduct(productName)
                .addProductToCartFromProduct()
                .verifyProductAddedToCart();

        new Cart(DriverManager.getDriver())
                .navigateToCart()
                .verifyCartHasTheCorrectProduct(productName)
                .clickCheckOutButton();

        new CheckOut(DriverManager.getDriver())
                .selectCountry("Egypt")
                .placeOrder()
                .verifyCheckoutSuccess();
    }
    @AfterMethod
    public void tearDown(ITestResult result) {
        if (DriverManager.getDriver() != null) {
            BrowserActions.closeBrowser(DriverManager.getDriver());
        }
    }


}
