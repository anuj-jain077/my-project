package tests;

import base.BaseTest;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.DataProviderClass;

public class YahooFinanceTest extends BaseTest {

    @Test(dataProvider = "stockDataProvider", dataProviderClass = DataProviderClass.class)
    @Description("Test stock search functionality on Yahoo Finance")
    @Severity(SeverityLevel.CRITICAL)
    @Feature("Stock Search")
    @Story("User searches for a stock and verifies details")
    public void testStockSearch(String stockSymbol, String expectedCompanyName, Double expectedPrice) throws InterruptedException {
        homePage.searchStock(stockSymbol);

        // 🔹 First check if auto-suggest is displayed
        if (homePage.isAutoSuggestDisplayed()) {
            logger.info("Auto-suggest appeared for: " + stockSymbol);

            if (expectedCompanyName != null) {
                Assert.assertTrue(homePage.verifyAutoSuggest(expectedCompanyName), "Auto suggest mismatch");
                homePage.clickFirstSuggestion();
            } else {
                logger.warn("Skipping stock selection for invalid input: " + stockSymbol);
                return;  // 🔹 Stop execution if stock cannot be selected
            }

        } else if (homePage.isNoSuggestionDisplayed()) {
            // 🔹 If no auto-suggest is displayed, then check for "No suggestions" message
            logger.warn("No suggestions displayed for: " + stockSymbol);
            Assert.assertTrue(homePage.isNoSuggestionDisplayed(), "Unexpected auto suggest for: " + stockSymbol);
            return;  // 🔹 Exit test early
        }

        // 🔹 Stock price validation (Only if expectedPrice is provided)
        if (expectedPrice != null) {
            double stockPrice = stockPage.getStockPrice();
            Assert.assertTrue(stockPrice > expectedPrice, "Stock price is lower than expected");
            logger.info("Stock Price Verified: " + stockPrice);
        }

        logger.info("Previous Close: " + stockPage.getPreviousClose());
        logger.info("Volume: " + stockPage.getVolume());
    }


}
