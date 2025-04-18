# Sauce Labs Mobile Web Automation Framework

## 📱 Overview
A hybrid mobile web automation framework for testing Sauce Labs demo application (https://www.saucedemo.com/v1/) across different mobile browsers using Appium. Features automated email reporting with HTML test results.

## 🚀 Tech Stack
- Java 17
- Appium
- Selenium WebDriver
- TestNG
- Maven
- Extent Reports
- Log4j2
- JavaMail API
- Apache POI

## 🌐 Supported Mobile Browsers
- Chrome
- Firefox
- Edge

## 📁 Project Structure
```
saucelabs-mobile-web/
├── src/
│   ├── main/java/
│   │   └── com.saucedemo/
│   │       ├── pageobjects/
│   │       │   ├── Actions.java
│   │       │   ├── CartPage.java
│   │       │   ├── CheckoutPage.java
│   │       │   ├── InventoryPage.java
│   │       │   ├── LoginPage.java
│   │       │   └── LogoutPage.java
│   │       └── utils/
│   │           ├── EmailSender.java
│   │           ├── ExcelUtils.java
│   │           ├── ExtentReportManager.java
│   │           ├── LoggingUtils.java
│   │           └── ScreenshotUtils.java
│   └── test/java/
│       └── com.saucedemo/
│           ├── listeners/
│           │   └── TestListener.java
│           └── tests/
│               ├── BaseTest.java
│               └── EndToEndWorkflowTest.java
├── resources/
│   ├── config.properties
│   ├── log4j2.xml
│   └── org.mozilla.firefox_133.0.3.apk
├── reports/
├── test-output/
└── SaubeLabsMobileWebE2E.xml
```

## 🧪 Test Scenarios

### End-to-End Shopping Flow
1. Browser Launch & Navigation
   - Launch mobile browser (Chrome/Firefox/Edge)
   - Navigate to Sauce Labs demo site
2. Authentication
   - Login with valid credentials
3. Product Selection
   - Choose product
   - Update quantity
   - Add to cart
4. Checkout Process
   - Cart verification
   - Shipping information
   - Payment details
   - Order placement
5. Post-Order Validation
   - Order confirmation
   - Return to home screen

## Tests Automated
The following end-to-end workflow has been automated:
#End-to-End Workflow Test
1. Launch mobile browser (Chrome, Firefox, or Edge)
2. Navigate to the SauceLabs demo application (https://www.saucedemo.com/v1/)
3. Click on Menu
4. Click on Login
5. Login using valid credentials
6. Browse and select a product
7. Increase item quantity
8. Add product to cart
9. Access the cart by clicking on Cart Badge
10. Proceed to checkout
11. Fill out shipping information
12. Complete payment information
13. Place order
14. Click on "Continue Shopping"
15. Verify redirection to Home screen

## ⚙️ Setup Requirements

### Prerequisites
1. Java JDK 17
2. Maven
3. Appium Server
4. Mobile devices/emulators
5. Mobile browsers

### Configuration
1. Update `config.properties`:
```properties
# Browser Configuration
browser=firefox
url=https://www.saucedemo.com/v1/

# Appium Configuration
appium.server.url=http://127.0.0.1:4723
platform.name=Android
device.name=Your_Device_Name
platform.version=Your_Android_Version

# Email Configuration
email.sender=your-email@example.com
email.password=your-app-specific-password
email.recipients=recipient1@example.com,recipient2@example.com
email.subject=Test Execution Report
```

2. Configure `log4j2.xml` for logging

## 🏃‍♂️ Running Tests

### Command Line Execution
```bash
# Run all tests
mvn clean test

# Run with specific browser
mvn test -Dbrowser=firefox

# Run specific test
mvn test -Dtest=EndToEndWorkflowTest
```

### IDE Execution
1. Right-click on `SaubeLabsMobileWebE2E.xml`
2. Select "Run As" > "TestNG Suite"

## 📊 Reporting System

### Extent Reports
- HTML reports generated in `reports/` directory
- Features:
  - Test case details
  - Step-by-step execution
  - Screenshots
  - Browser/platform information
  - Execution time

### Email Reporting
- Automated email sending after test execution
- HTML report attachment
- Execution summary
- Configured recipients
- Customizable email templates

Here is the Extent report overview:
![Image](https://github.com/user-attachments/assets/3c5bc0ef-e83f-4ce2-98a1-147fd825b414)
![Image](https://github.com/user-attachments/assets/447e58de-52ff-40ce-a82c-ecee917060f2)

## Automation Video Output:
https://github.com/user-attachments/assets/00daeb04-d300-47ab-b306-9af77fcc71aa

## 📝 Logging
- Framework: Log4j2
- Configuration: `log4j2.xml`
- Levels:
  - INFO: Test flow
  - ERROR: Failures
  - DEBUG: Detailed information

## 🛠️ Troubleshooting

### Common Issues
1. Browser Launch
```
Solution: Verify browser driver compatibility
```

2. Email Sending
```
Solution: Check email credentials and security settings
```

3. Appium Connection
```
Solution: Verify Appium server status and configuration
```

## 📚 Best Practices
- Page Object Model implementation
- Explicit waits
- Screenshot capture on failures
- Regular code cleanup
- Proper exception handling
- Comprehensive logging

