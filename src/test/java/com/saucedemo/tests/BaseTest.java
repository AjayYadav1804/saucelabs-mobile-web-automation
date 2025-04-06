package com.saucedemo.tests;

import com.saucedemo.pageobjects.CartPage;
import com.saucedemo.pageobjects.CheckoutPage;
import com.saucedemo.pageobjects.InventoryPage;
import com.saucedemo.pageobjects.LoginPage;
import com.saucedemo.pageobjects.LogoutPage;
import com.saucedemo.utils.ExtentReportManager;

import io.appium.java_client.AppiumClientConfig;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.ITestContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

public class BaseTest {
    protected Properties properties;
    public AndroidDriver driver;
    public AppiumDriverLocalService service;
    protected LoginPage loginPage;
    protected InventoryPage inventoryPage;
    protected CartPage cartPage;
    protected CheckoutPage checkoutPage;
    protected LogoutPage logoutPage;

    protected static final Logger logger = LogManager.getLogger(BaseTest.class);

    @BeforeClass
    public void setUp(ITestContext context) throws IOException {
        ExtentReportManager.initializeReport();

        properties = new Properties();
        FileInputStream fileInputStream = new FileInputStream("src/main/java/resources/config.properties");
        properties.load(fileInputStream);
        logger.info("Configuration loaded from properties file.");

        String ipAddress = System.getProperty("ipAddress")!=null ? System.getProperty("ipAddress") : properties.getProperty("ipAddress");
		System.out.println(ipAddress);
		String port = properties.getProperty("port");
		service = startAppiumServer(ipAddress,Integer.parseInt(port));
        
        // Initialize DesiredCapabilities and driver
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("deviceName", properties.getProperty("AndroidDeviceName"));
        capabilities.setCapability("platformName", properties.getProperty("platformName"));
       
        
        String browser = properties.getProperty("browser").toLowerCase();
        switch (browser) {
            case "chrome":
            	capabilities.setCapability("automationName", "UiAutomator2");
            	capabilities.setCapability("chromedriverExecutable", System.getProperty("user.dir") + "/libs/chromedriver.exe");
            	capabilities.setCapability("browserName", "Chrome");
                //Add chromeOptions if you need to run in headless mode or add other chrome specific settings
                //ChromeOptions chromeOptions = new ChromeOptions();
                //chromeOptions.addArguments("--headless=new");
                //caps.setCapability(ChromeOptions.CAPABILITY, chromeOptions);
                break;
            case "firefox":
            	capabilities.setCapability("automationName", "UiAutomator2"); 
            	capabilities.setCapability("app", System.getProperty("user.dir") + "/src/main/java/resources/org.mozilla.firefox_133.0.3.apk");
            	//capabilities.setCapability("browserName", "firefox"); // Essential for Firefox
            	// Optional capabilities (adjust as needed)
            	//caps.setCapability("firefox:geckodriverVersion", "0.33.0"); // Specify GeckoDriver version if needed
            	//caps.setCapability("appPackage", "org.mozilla.firefox"); // If you need to launch Firefox directly
            	//caps.setCapability("appActivity", "org.mozilla.gecko.GeckoViewActivity"); // If you need to launch Firefox directly
                break;
            case "edge":
            	capabilities.setCapability("automationName", "UiAutomator2"); 
            	//capabilities.setCapability("app:edgeChromiumDriverExecutable", System.getProperty("user.dir") + "/libs/msedgedriverr.exe");
            	//capabilities.setCapability("browserName", "Microsoft Edge");
            	//capabilities.setCapability("ms:edgeOptions", edgeOptions);
            	// Optional capabilities (adjust as needed)
            	// caps.setCapability("ms:edgeChromium", true); // Often not needed, Appium handles it
            	// caps.setCapability("chromedriverExecutable", "/path/to/chromedriver"); // If Appium can't find it
                break;
            default:
                throw new IllegalStateException("Unsupported browser type: " + browser);
        }

        System.out.println(service.getUrl());
        AppiumClientConfig clientConfig = AppiumClientConfig.defaultConfig()
                .baseUrl(service.getUrl());

        driver = new AndroidDriver(clientConfig, capabilities);
		 driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        String URL=properties.getProperty("URL");
        driver.get(URL);
        logger.info("Navigated to SauceDemo website.");

        // Initialize the page objects
        loginPage = new LoginPage(driver);
        inventoryPage = new InventoryPage(driver);
        cartPage = new CartPage(driver);
        checkoutPage = new CheckoutPage(driver);
        logoutPage = new LogoutPage(driver);
    }

    @AfterClass
    public void teardown() {
        try {
            if (driver != null) {
                //driver.quit();
            }if (service != null) {
            	service.stop();
            }
            ExtentReportManager.endReport();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    
    public AppiumDriverLocalService startAppiumServer(String ipAddress, int port) {
        // Create an instance of the AppiumServiceBuilder
        AppiumServiceBuilder builder = new AppiumServiceBuilder()
                .withAppiumJS(new File("C:\\Users\\agelloboyina\\AppData\\Roaming\\npm\\node_modules\\appium\\build\\lib\\main.js"))
                .withIPAddress(ipAddress)
                .usingPort(port);

        // Start the service
        AppiumDriverLocalService service = builder.build();
        service.start();

        // Ensure the service started successfully
        if (!service.isRunning()) {
            throw new IllegalStateException("Appium Server did not start!");
        }

        return service;
    }
    public String getUsername() {
        return properties.getProperty("username");
    }

    public String getPassword() {
        return properties.getProperty("password");
    }
    
    
}