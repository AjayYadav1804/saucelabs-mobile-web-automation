package com.saucedemo.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class CheckoutPage extends Actions {

    private By firstNameField = By.id("first-name");
    private By lastNameField = By.id("last-name");
    private By zipCodeField = By.id("postal-code");
    private By continueButton = By.xpath("//input[@value='CONTINUE']");
    private By finishButton = By.xpath("//a[text()='FINISH']");
    private By successMessage = By.className("complete-header");
    private By totalPrice = By.className("summary_total_label");
   

    public CheckoutPage(WebDriver driver) {
        super(driver);
    }

    public void fillDetailsAndProceed(String fName, String lName, String postalCode) {
        sendKeys(firstNameField, fName, "First Name");
        sendKeys(lastNameField, lName,"Last Name");
        sendKeys(zipCodeField, postalCode, "Zip/Postal Code");
        click(continueButton,"Continue");
    }

    public void finishCheckout() {
        click(finishButton,"Finish");
    }
    
    public String getSuccessMessage() {
    	return  driver.findElement(successMessage).getText().trim();
    }
    
    public WebElement getCartItemPrice() {
        return driver.findElement(totalPrice);
    }
}