package pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utilities.TestUtils;

import java.time.Duration;

public class YahooFinanceHomePage {
    private WebDriver driver;
    private WebDriverWait wait;
    private static final Logger logger = LogManager.getLogger(YahooFinanceHomePage.class);

    private By searchBox = By.id("ybar-sbq");
    private By firstSuggestion = By.xpath("//li[contains(@data-test, 'srch-sym')][1]");
    private By noSuggestion = By.xpath("//p[@class='modules-module_tipMessage__ZQFev']"); // No results found message

    public YahooFinanceHomePage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        int timeout = Integer.parseInt(TestUtils.getConfigProperty("explicitWait"));
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
    }


    public void searchStock(String stockSymbol) {
        int attempts = 0;
        while (attempts < 3) {  // Retry mechanism
            try {
                // Re-locate search box every retry to prevent stale element issues
                WebElement searchBoxElement = wait.until(ExpectedConditions.elementToBeClickable(searchBox));

                // Clear input using JavaScript
                JavascriptExecutor js = (JavascriptExecutor) driver;
                js.executeScript("arguments[0].value = '';", searchBoxElement);

                // Explicit wait to ensure input is cleared
                wait.until(ExpectedConditions.attributeToBe(searchBox, "value", ""));

                // Send stock symbol
                searchBoxElement.sendKeys(stockSymbol);
                logger.info("Entered stock symbol: " + stockSymbol);

                return; // 🔹 Exit loop if successful
            } catch (StaleElementReferenceException e) {
                logger.warn("Stale element encountered. Retrying... Attempt: " + (attempts + 1));
            }
            attempts++;
        }
        throw new RuntimeException("Failed to enter stock symbol due to stale element issues.");
    }


    //  Verify the auto-suggest result (valid stock)
    public boolean verifyAutoSuggest(String expectedCompanyName) {
        try {
            WebElement firstSuggestionElement = wait.until(ExpectedConditions.visibilityOfElementLocated(firstSuggestion));
            boolean match = firstSuggestionElement.getText().contains(expectedCompanyName);
            logger.info("Auto-suggest verification for '" + expectedCompanyName + "': " + match);
            return match;
        } catch (TimeoutException e) {
            logger.warn("Auto-suggest dropdown did not appear.");
            return false;
        }
    }

    //  Handle clicking on the first auto-suggested stock
    public void clickFirstSuggestion() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(firstSuggestion)).click();
            logger.info("Clicked on first suggested stock.");
        } catch (TimeoutException e) {
            logger.error("First suggestion not found or clickable.");
            throw new RuntimeException("First suggestion not found", e);
        }
    }

    //  Verify if auto-suggest is displayed (for edge cases)
    public boolean isAutoSuggestDisplayed() {
        try {
            boolean displayed = wait.until(ExpectedConditions.visibilityOfElementLocated(firstSuggestion)).isDisplayed();
            logger.info("Auto-suggest dropdown displayed: " + displayed);
            return displayed;
        } catch (TimeoutException e) {
            logger.warn("Auto-suggest dropdown did not appear.");
            return false;
        }
    }

    //  Verify if no suggestions are found (invalid stock)
    public boolean isNoSuggestionDisplayed() {
        try {
            boolean displayed = wait.until(ExpectedConditions.visibilityOfElementLocated(noSuggestion)).isDisplayed();
            logger.info("No suggestion message displayed: " + displayed);
            return displayed;
        } catch (TimeoutException e) {
            logger.info("No suggestion message NOT found & stock does not exist.");
            return false;
        }
    }
}
