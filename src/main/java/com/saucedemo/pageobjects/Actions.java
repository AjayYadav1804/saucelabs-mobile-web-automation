package com.saucedemo.pageobjects;

import com.saucedemo.utils.LoggingUtils;

import java.time.Duration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Actions {

    protected WebDriver driver;
    private static final Logger logger = LogManager.getLogger(Actions.class);

    public Actions(WebDriver driver) {
        this.driver = driver;
    }

    protected void sendKeys(By locator, String text, String description) {
        try {
        	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
            element.clear();
            element.sendKeys(text);
            logger.info("Sent keys '{}' to element with locator: {}", text, locator);
            LoggingUtils.logAction(driver, "Entered '" + text + "' into the " + description + " text box.");
        } catch (NoSuchElementException e) {
            logger.error("Element not found for locator: {}", locator, e);
            LoggingUtils.logAction(driver, "Element not found for sendKeys operation at locator: " + locator);
            throw new RuntimeException("Element not found for sendKeys operation");
        }
    }

    protected void click(By locator, String description) {
        try {
        	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        	WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
            element.click();
            logger.info("Clicked element with locator: {}", locator);
            LoggingUtils.logAction(driver, "Clicked on element: " + description);
        } catch (NoSuchElementException e) {
            logger.error("Element not found for locator: {}", locator, e);
            LoggingUtils.logAction(driver, "Element not found for click operation at locator: " + locator);
            throw new RuntimeException("Element not found for click operation");
        }
    }

    protected void clickWebElement(WebElement element, String description) {
        try {
        	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.elementToBeClickable(element)); // Wait for element to be clickable
            element.click();
            logger.info("Clicked element: {}", element);
            LoggingUtils.logAction(driver, "Clicked on element: " + description);
        } catch (NoSuchElementException e) {
            logger.error("Element not available for click operation", e);
            LoggingUtils.logAction(driver, "Element not available for click operation");
            throw new RuntimeException("Element not available for click operation");
        } catch (Exception e) {
            logger.error("An error occurred while attempting to click on the element", e);
            LoggingUtils.logAction(driver, "An error occurred during click operation");
            throw new RuntimeException("Failed to click element: " + e.getMessage());
        }
    }

    protected void selectByVisibleText(By locator, String text) {
        try {
            Select dropdown = new Select(driver.findElement(locator));
            dropdown.selectByVisibleText(text);
            logger.info("Selected '{}' from dropdown with locator: {}", text, locator);
            LoggingUtils.logAction(driver, "Selected '" + text + "' from dropdown with locator: " + locator);
        } catch (NoSuchElementException e) {
            logger.error("Element not found for locator: {}", locator, e);
            LoggingUtils.logAction(driver, "Element not found for selectByVisibleText operation at locator: " + locator);
            throw new RuntimeException("Element not found for select operation");
        } catch (IllegalArgumentException e) {
            logger.error("Cannot select value '{}' from dropdown", text, e);
            LoggingUtils.logAction(driver, "Invalid value for select operation: " + text);
            throw new RuntimeException("Invalid value for select operation");
        }
    }
}