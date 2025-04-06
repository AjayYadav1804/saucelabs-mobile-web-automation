package com.saucedemo.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LogoutPage extends Actions {

 
    private By openMenu = By.xpath("//button[text()='Open Menu']");
    private By logoutButton = By.id("logout_sidebar_link");
    
    public LogoutPage(WebDriver driver) {
        super(driver); // Pass both WebDriver and ExtentTest to the superclass constructor
    }

    public void logout() throws InterruptedException {
    	
    	click(openMenu,"Hamburger menu");
    	Thread.sleep(2000);
    	click(logoutButton,"Logout");
    	
    }
}