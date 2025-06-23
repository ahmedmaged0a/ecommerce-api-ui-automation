# ECommerce Automation

A robust Java-based automation framework for e-commerce applications, supporting both API and UI testing with Selenium, RestAssured, and TestNG. Features include Page Object Model, data-driven tests, and Allure reporting for scalable, maintainable test coverage.

---

## Features

- **Hybrid Automation:** API and UI test support
- **Page Object Model (POM):** Clean, maintainable UI automation
- **Data-Driven Testing:** Easily configurable test data
- **Reusable Utilities:** Actions, waits, validations, logging
- **Allure Reporting:** Rich, interactive test reports
- **TestNG Integration:** Flexible test execution and grouping
- **Custom Listeners:** Enhanced reporting and logging
- **POJO-based API Models:** Strongly-typed API requests/responses
- **Builder Pattern:** For complex API payloads
- **Environment Management:** Easy environment switching

---

## Getting Started

### Prerequisites
- Java 8 or higher
- Maven 3.6+
- Chrome/Firefox browser (for UI tests)
- Allure Commandline (for report viewing)

### Setup
1. **Clone the repository:**
   ```bash
   git clone <repo-url>
   cd ecommerce-automation
   ```
2. **Install dependencies:**
   ```bash
   mvn clean install
   ```
3. **Configure properties:**
   - Update `src/main/resources/environment.properties` and `web.properties` as needed.

---

## Usage

### Run All Tests
```bash
mvn clean test
```

### Run Specific Suite
- **UI Tests:**
  ```bash
  mvn test -DsuiteXmlFile=UIRunner.xml
  ```
- **API Tests:**
  ```bash
  mvn test -DsuiteXmlFile=APIRunner.xml
  ```
- **Regression/Smoke:**
  ```bash
  mvn test -DsuiteXmlFile=RegressionRunner.xml
  mvn test -DsuiteXmlFile=SmokeRunner.xml
  ```

---

## Reporting

- Allure reports are generated in `test-outputs/allure-results/`.
- To view the report:
  ```bash
  allure serve test-outputs/allure-results
  ```

---

## Project Structure

```
ECommerceAutomation/
  ├── src/
  │   ├── main/java/com/shopping/
  │   ├── test/java/com/shopping/tests/
  │   └── resources/
  ├── test-outputs/
  ├── pom.xml
  └── testng.xml
```

---

## Contributing

1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Open a pull request

---

## License

This project is licensed under the MIT License. 
