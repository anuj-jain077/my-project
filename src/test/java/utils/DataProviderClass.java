package utils;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.testng.annotations.DataProvider;
import java.io.FileReader;
import java.io.IOException;

public class DataProviderClass {

    private static final String TESTDATA_PATH = "src/test/resources/testdata.json";

    private static JsonObject getTestData() {
        try (FileReader reader = new FileReader(TESTDATA_PATH)) {
            return JsonParser.parseReader(reader).getAsJsonObject();
        } catch (IOException e) {
            throw new RuntimeException("Error reading testdata.json", e);
        }
    }

    @DataProvider(name = "stockDataProvider")
    public static Object[][] getStockData() {
        JsonArray stocks = getTestData().getAsJsonArray("stocks");
        Object[][] testData = new Object[stocks.size()][3];

        for (int i = 0; i < stocks.size(); i++) {
            JsonObject stock = stocks.get(i).getAsJsonObject();

            // Handle null values safely
            String stockSymbol = getSafeString(stock, "stockSymbol");
            String expectedCompanyName = getSafeString(stock, "expectedCompanyName");
            Double expectedPrice = getSafeDouble(stock, "expectedPrice");

            testData[i][0] = stockSymbol;
            testData[i][1] = expectedCompanyName;
            testData[i][2] = expectedPrice;
        }
        return testData;
    }

    private static String getSafeString(JsonObject obj, String key) {
        JsonElement element = obj.get(key);
        return (element != null && !element.isJsonNull()) ? element.getAsString() : null;
    }

    private static Double getSafeDouble(JsonObject obj, String key) {
        JsonElement element = obj.get(key);
        return (element != null && !element.isJsonNull()) ? element.getAsDouble() : null;
    }
}
