package com.saucedemo.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class CartPage extends Actions {

   
    private By checkoutButton = By.xpath("//a[text()='CHECKOUT']");
    private By cartItemPrice = By.className("inventory_item_price");

    public CartPage(WebDriver driver) {
        super(driver);
    }

    public void proceedToCheckout() {
        click(checkoutButton,"CHECKOUT");
    }
    
    public WebElement getCartItemPrice() {
        return driver.findElement(cartItemPrice);
    }
    
    
    
    
}