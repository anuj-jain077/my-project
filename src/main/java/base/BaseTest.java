package base;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import pages.YahooFinanceHomePage;
import pages.YahooFinanceStockPage;
import utilities.TestUtils;

import java.time.Duration;

public class BaseTest {
    protected WebDriver driver;
    protected WebDriverWait wait;
    protected YahooFinanceHomePage homePage;
    protected YahooFinanceStockPage stockPage;
    protected Logger logger = LogManager.getLogger(BaseTest.class);

    @BeforeClass
    public void setUp() {
        logger.info("Initializing WebDriver...");

        String browser = TestUtils.getConfigProperty("browser").toLowerCase();
        int implicitWait = Integer.parseInt(TestUtils.getConfigProperty("implicitWait"));
        int explicitWait = Integer.parseInt(TestUtils.getConfigProperty("explicitWait"));
        String baseUrl = TestUtils.getConfigProperty("baseUrl");

        driver = initializeDriver(browser);
        if (driver == null) {
            logger.error("Failed to initialize WebDriver. Exiting tests.");
            throw new RuntimeException("WebDriver initialization failed.");
        }

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(implicitWait));
        driver.get(baseUrl);
        wait = new WebDriverWait(driver, Duration.ofSeconds(explicitWait));
        logger.info("Navigated to: " + baseUrl);

        // ✅ Initialize Pages
        homePage = new YahooFinanceHomePage(driver, wait);
        stockPage = new YahooFinanceStockPage(driver, wait);
    }

    private WebDriver initializeDriver(String browser) {
        switch (browser) {
            case "firefox":
                try {
                    WebDriverManager.firefoxdriver().setup();
                    FirefoxOptions firefoxOptions = new FirefoxOptions();
                    driver = new FirefoxDriver(firefoxOptions);
                    logger.info("Firefox browser launched successfully.");
                } catch (Exception e) {
                    logger.warn("Firefox not available, switching to Chrome: " + e.getMessage());
                    return initializeChromeDriver();
                }
                break;
            case "chrome":
            default:
                return initializeChromeDriver();
        }
        return driver;
    }

    private WebDriver initializeChromeDriver() {
        try {
            WebDriverManager.chromedriver().setup();
            ChromeOptions chromeOptions = new ChromeOptions();
            driver = new ChromeDriver(chromeOptions);
            logger.info("Chrome browser launched successfully.");
        } catch (Exception e) {
            logger.error("Failed to launch Chrome: " + e.getMessage());
            driver = null;
        }
        return driver;
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            try {
                driver.quit();
                logger.info("Closed WebDriver session");
            } catch (Exception e) {
                logger.error("Error while closing WebDriver: " + e.getMessage());
            }
        }
    }
}
