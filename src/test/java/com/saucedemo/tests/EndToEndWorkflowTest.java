package com.saucedemo.tests;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentTest;
import com.saucedemo.utils.ExcelUtils;
import com.saucedemo.utils.ExtentReportManager;

public class EndToEndWorkflowTest extends BaseTest {
    //private static final Logger logger = LogManager.getLogger(EndToEndWorkflowTest.class);
    
    @Test(dataProvider = "checkoutData")
    public void testSauceDemo(String firstName, String lastName, String postalCode) throws Exception {
        ExtentTest test = ExtentReportManager.createTest("testSauceDemo", "End To End Test");
        logger.info("Starting test with data: {}, {}, {}", firstName, lastName, postalCode);
        try {
            test.info("Starting Sauce Demo Test");

            // Step 1: Login
            loginPage.login(getUsername(), getPassword());

            // Step 2: Add the most expensive product
            double maxPrice = inventoryPage.addMostExpensiveProductToCart();
            test.pass("Most expensive product added to cart");

            // Step 3: Verify cart contents
            Assert.assertEquals(inventoryPage.validateCartContents(), 1);
            test.pass("Cart contents validated successfully.");
            inventoryPage.clickOnShoppingCartLink();
            test.pass("Cart contents verified");

            String price = cartPage.getCartItemPrice().getText();
            Assert.assertEquals(Double.parseDouble(price), maxPrice);
            test.pass("Price in cart matches expected value.");

            // Step 4: Proceed to checkout
            cartPage.proceedToCheckout();

            // Step 5: Fill details
            checkoutPage.fillDetailsAndProceed(firstName, lastName, postalCode);
            test.info("Filled checkout details.");

            // Step 6: Validate price
            String totalPricestr = checkoutPage.getCartItemPrice().getText().replace("Total: $", "");
            double totalPrices = Double.parseDouble(totalPricestr);
            double maxPriceandTax = maxPrice + 4.00;
            Assert.assertEquals(totalPrices, maxPriceandTax);
            test.pass("Price validated");

            // Step 7: Place order
            checkoutPage.finishCheckout();
            test.pass("Checkout process completed.");

            Assert.assertEquals(checkoutPage.getSuccessMessage(), "THANK YOU FOR YOUR ORDER");
            test.pass("Order Placed Successfully");

            // Step 8: Logout
            logoutPage.logout();
            test.pass("User Successfully Logged Out of application");

        } catch (Exception e) {
            logger.error("Exception during test execution: ", e);
            test.fail("Test execution failed: " + e.getMessage());
            throw e; // Ensure test is marked as failed
        }
    }
    
    @DataProvider(name = "checkoutData")
    public Iterator<Object[]> checkoutDataProvider(Method testMethod) throws IOException, NoSuchMethodException, SecurityException {
        ExcelUtils excelUtils = new ExcelUtils("src/test/java/resources/TestData.xlsx");
     // Get the test method name at runtime using reflection
        String testMethodName = testMethod.getName();
        List<Object[]> testData = excelUtils.getTestData("TestData", testMethodName);
     
        logger.info("Loaded test data from Excel for method testLoginAndPurchase");
        return testData.iterator();
    }
    
}