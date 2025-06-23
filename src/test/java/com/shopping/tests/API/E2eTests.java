package com.shopping.tests.API;

import com.shopping.API.requests.*;
import com.shopping.UI.listeners.TestNGListeners;
import com.shopping.utils.JsonUtils;
import com.shopping.utils.LogsManager;
import io.qameta.allure.Feature;
import io.qameta.allure.testng.Tag;
import org.testng.annotations.*;


@Listeners(TestNGListeners.class)
public class E2eTests {
    private JsonUtils testData;
    Login login;
    AddProduct addProduct;
    CreateOrder createOrder;
    DeleteOrder deleteOrder;
    DeleteProduct deleteProduct;
    GetorderDetails getOrderDetails;
    String token;
    String userId;
    String productOrderId;

    @BeforeClass
    public void setUp() {
        testData = new JsonUtils("test-data");
        login = new Login();
        addProduct = new AddProduct();
        createOrder = new CreateOrder(testData);
        deleteOrder = new DeleteOrder();
        deleteProduct = new DeleteProduct();
        getOrderDetails = new GetorderDetails();
    }

    @Tag(value = "API")
    @Feature(value = "Login")
    @Test(priority = 1
            , description = "Login to the application and retrieve token and userId"
            , groups = {"API", "E2E"}
    )
    public void loginRequestTest() {
        login.enterLoginData(testData.getJsonData("API.Login.userEmail"),
                        testData.getJsonData("API.Login.userPassword"))
                .requestLogin()
                .hitEndPoint()
                .deserializeResponse()
                .validateStatusCode()
                .validateResponseBody();
        token = login.getToken();
        userId = login.getUserId();

    }

    @Tag(value = "API")
    @Feature(value = "AddProduct")
    @Test(priority = 2
            , description = "Add product to the cart and retrieve productOrderId"
            , groups = {"API", "E2E"},
            dependsOnMethods = "loginRequestTest"
    )
    public void addProductRequestTest() {
        addProduct.requestAddProduct(testData, token, userId)
                .hitEndPoint()
                .deserializeResponse()
                .validateStatusCode("201")
                .validateResponseBody();
        productOrderId = addProduct.getProductId();
    }

    @Tag(value = "API")
    @Feature(value = "createOrder")
    @Test(priority = 3
            , description = "Create an order with the added product"
            , groups = {"API", "E2E"},
            dependsOnMethods = {"loginRequestTest","addProductRequestTest"}
    )
    public void createOrderRequestTest() {
        createOrder.setRequestSpecification(token, productOrderId)
                .hitEndPoint()
                .deserializeResponse()
                .validateStatusCode("201")
                .validateResponseBody();
    }

    @Tag(value = "API")
    @Feature(value = "GetOrderDetails")
    @Test(priority = 4
            , description = "Get the details of the created order"
            , groups = {"API", "E2E"},
            dependsOnMethods =  {"loginRequestTest","createOrderRequestTest"}
    )
    public void GetOrderDetailsRequestTest() {
        getOrderDetails.setRequestSpecification(token, createOrder.getProductOrderId())
                .hitEndPoint()
                .validateStatusCode("200")
                .validateResponseBody();
    }

    @Tag(value = "API")
    @Feature(value = "DeleteOrder")
    @Test(priority = 5
            , description = "Delete the created order"
            , groups = {"API", "E2E"},
            dependsOnMethods =  {"loginRequestTest","createOrderRequestTest"}
    )
    public void DeleteOrderRequestTest() {
        deleteOrder.setRequestSpecification(token)
                .hitEndPoint(createOrder.getProductOrderId())
                .deserializeResponse()
                .validateStatusCode("200")
                .validateResponseMessage();
    }

    @Tag(value = "API")
    @Feature(value = "Deleteproduct")
    @Test(priority = 6
            , description = "Delete the created order"
            , groups = {"API", "E2E"},
            dependsOnMethods =  {"loginRequestTest","addProductRequestTest", "DeleteOrderRequestTest"}
    )
    public void DeleteProductRequestTest() {
        deleteProduct.setRequestSpecification(token)
                .hitEndPoint(productOrderId)
                .deserializeResponse()
                .validateStatusCode("200")
                .validateResponseMessage();
    }


    @AfterMethod
    public void tearDown() {
        LogsManager.info("Test completed. Cleaning up resources.");
    }
}
