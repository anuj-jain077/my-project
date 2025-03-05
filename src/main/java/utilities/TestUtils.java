package utilities;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class TestUtils {
    private static Properties configProperties;
    private static JsonArray testDataArray;

    static {
        loadConfig();
        loadTestData();
    }

    private static void loadConfig() {
        configProperties = new Properties();
        try (FileReader reader = new FileReader("src/main/resources/config.properties")) {
            configProperties.load(reader);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load config.properties", e);
        }
    }

    private static void loadTestData() {
        try (FileReader reader = new FileReader("src/test/resources/testdata.json")) {
            testDataArray = JsonParser.parseReader(reader).getAsJsonObject().getAsJsonArray("stocks");
        } catch (IOException e) {
            throw new RuntimeException("Failed to load testdata.json", e);
        }
    }

    public static String getConfigProperty(String key) {
        return configProperties.getProperty(key);
    }

    public static JsonObject getStockData(String symbol) {
        for (var element : testDataArray) {
            JsonObject stock = element.getAsJsonObject();
            if (stock.get("symbol").getAsString().equalsIgnoreCase(symbol)) {
                return stock;
            }
        }
        return null;
    }
}


