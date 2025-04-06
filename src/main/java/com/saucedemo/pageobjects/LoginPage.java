package com.saucedemo.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends Actions {

    private By usernameField = By.id("user-name");
    private By passwordField = By.id("password");
    private By loginButton = By.className("btn_action");
    
    private By openMenu = By.xpath("//button[text()='Open Menu']");
    private By logoutButton = By.id("logout_sidebar_link");
    public LoginPage(WebDriver driver) {
        super(driver); // Pass both WebDriver and ExtentTest to the superclass constructor
    }
    public void login(String username, String password) {
        sendKeys(usernameField, username,"Username");
        sendKeys(passwordField, password,"Password");
        click(loginButton,"Login");
    }
    
    public void logout() throws InterruptedException {
    	
    	click(openMenu,"Hamburger menu");
    	Thread.sleep(2000);
    	click(logoutButton,"Logout");
    	
    }
}