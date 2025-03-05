package pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utilities.TestUtils;
import java.time.Duration;

public class YahooFinanceStockPage {
    private WebDriver driver;
    private WebDriverWait wait;
    private static final Logger logger = LogManager.getLogger(YahooFinanceStockPage.class);

    // Locators
    private By stockPrice = By.xpath("//span[@data-testid='qsp-price']");
    private By prevClose = By.xpath("//fin-streamer[@data-field='regularMarketPreviousClose']");
    private By volume = By.xpath("//fin-streamer[@data-field='regularMarketVolume']");

    // Constructor
    public YahooFinanceStockPage(WebDriver driver, WebDriverWait explicitWait) {
        this.driver = driver;
        int timeout = Integer.parseInt(TestUtils.getConfigProperty("explicitWait"));
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
    }

    // Method to get stock price
    public double getStockPrice() {
        try {
            WebElement stockPriceElement = wait.until(ExpectedConditions.visibilityOfElementLocated(stockPrice));
            double price = Double.parseDouble(stockPriceElement.getText().replace(",", ""));
            logger.info("Stock Price Retrieved: $" + price);
            return price;
        } catch (TimeoutException e) {
            logger.error("Stock price element not found", e);
            throw new RuntimeException("Stock price not found", e);
        }
    }

    // Method to get previous close price
    public String getPreviousClose() {
        try {
            WebElement prevCloseElement = wait.until(ExpectedConditions.visibilityOfElementLocated(prevClose));
            String closePrice = prevCloseElement.getText();
            logger.info("Previous Close Price Retrieved: $" + closePrice);
            return closePrice;
        } catch (TimeoutException e) {
            logger.error("Previous close price element not found", e);
            return "N/A";
        }
    }

    // Method to get volume
    public String getVolume() {
        try {
            WebElement volumeElement = wait.until(ExpectedConditions.visibilityOfElementLocated(volume));
            String stockVolume = volumeElement.getText();
            logger.info("Stock Volume Retrieved: " + stockVolume);
            return stockVolume;
        } catch (TimeoutException e) {
            logger.error("Stock volume element not found", e);
            return "N/A";
        }
    }
}
