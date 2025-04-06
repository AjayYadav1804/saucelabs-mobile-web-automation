package com.saucedemo.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.util.List;

public class InventoryPage extends Actions {

    private By productPrices = By.className("inventory_item_price");
    private By addToCartButtons = By.className("btn_inventory");
    private By shoppingCartLink = By.className("shopping_cart_link");
    private By cartItems = By.xpath("//span[contains(@class,'shopping_cart_badge')]");
    
    public InventoryPage(WebDriver driver) {
        super(driver);
    }
    
    double highestPrice = 0.0;
    public double addMostExpensiveProductToCart() {
    	try {
        List<WebElement> prices = driver.findElements(productPrices);
        List<WebElement> addButtons = driver.findElements(addToCartButtons);

        if (prices.isEmpty() || addButtons.isEmpty() || prices.size() != addButtons.size()) {
            throw new IllegalStateException("Mismatch in product prices and add-to-cart buttons list size or empty lists found.");
           
        }

        
        int index = -1;  // Start with an invalid index to ensure correct selection confirmation

        for (int i = 0; i < prices.size(); i++) {
            String priceText = prices.get(i).getText().substring(1); // Remove the dollar sign
            try {
                double price = Double.parseDouble(priceText);
                if (price > highestPrice) {
                    highestPrice = price;
                    index = i;
                }
            } catch (NumberFormatException e) {
                System.err.println("Price not in expected format: " + priceText);
            }
        }

        if (index != -1) {
        	clickWebElement(addButtons.get(index),"ADD TO CART");
        } else {
            throw new IllegalStateException("No valid product found to add to the cart.");
        }
    }catch(Exception e) {
    	e.printStackTrace();
    }
		return highestPrice;
    } 
    
 //Validate the number of items in the cart
   public int validateCartContents() {
	   String count=driver.findElement(cartItems).getText();
	   return Integer.parseInt(count);
       
       
   }
    
    public void clickOnShoppingCartLink() {
        click(shoppingCartLink, "Shopping Cart");
    }
}