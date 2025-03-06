Yahoo Finance Automation Framework

📌 Project Overview

This is a Selenium Web UI Automation Framework for testing Yahoo Finance functionalities. The framework is designed with TestNG, Selenium WebDriver, and Java, and follows a Hybrid Automation Framework approach.

🚀 Features

Cross-browser testing (Firefox as default, Chrome as fallback).

TestNG Data Providers for parameterized testing.

Page Object Model (POM) for maintainable test structure.

Config-driven execution (reads configurations from a properties file).

JSON-based test data storage for flexible test case management.

Log4j logging for better debugging and execution tracking.

🛠️ Setup & Installation

1️⃣ Prerequisites

Java JDK 11+ (Ensure JAVA_HOME is set)

Maven (for dependency management)

Git (for version control)

Firefox & Chrome Browsers

GeckoDriver & ChromeDriver (Managed via WebDriverManager)

2️⃣ Clone the Repository

git clone https://github.com/anuj-jain077/my-project.git
cd my-project

3️⃣ Configure Test Properties

Edit config.properties inside src/main/resources/config/:

browser=firefox # Default browser is Firefox; options: firefox, chrome
implicitWait=10
explicitWait=20
baseUrl=https://finance.yahoo.com/

4️⃣ Setup Dependencies

Ensure all dependencies are installed via Maven:

mvn clean install

🏃 Running the Tests

1️⃣ Run All Tests

mvn test 

Note - If maven is not installed Please run - brew install maven

2️⃣ Run Specific Test Suite

mvn test -Dsurefire.suiteXmlFiles=suites/teslaTestCases.xml

3️⃣ View Test Reports

After execution, reports are available at:

target/surefire-reports/emailable-report.html
target/allure-results/


📁 Project Structure

my-project/
│── src/main/java/
│   ├── pages/          # Page Object Model (POM) classes
│
│── src/main/resources/
│   ├── config/         # Configuration files
│
│── src/test/java/
│   ├── base/           # BaseTest class for WebDriver setup
│   ├── tests/          # Test classes
│   ├── utilities/      # Utility classes (TestUtils, Config Readers, etc.)
│
│── src/test/resources/
│   ├── testdata.json   # Test data stored in JSON format
│   ├── log4j2.xml      # Log4j logging configuration
│
│── suites/
│   ├── teslaTestCases.xml # TestNG suite file
│
│── pom.xml             # Maven dependencies & build configuration
│── .gitignore          # Ignored files (target, logs, etc.)
│── README.md           # Project documentation

👨‍💻 Contributing

Fork the repository.

Create a new branch (feature-branch).

Commit your changes.

Push to your fork.

Open a Pull Request.

📞 Support

For any issues, please raise a GitHub issue or contact the repository owner.

Happy Testing! 🚀
