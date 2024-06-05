/**
 * @author Easton H. Herbon
 *
 * @brief This class is used to load all the files from the equities.txt
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

class RealTimeFeed {
    private final Map<String, Double> stockPrices;

    public RealTimeFeed(String filePath) {
        stockPrices = new HashMap<>();
        loadStockPricesFromFile(filePath);
    }

    // used for parsing file to find stock prices
    private void loadStockPricesFromFile(String filePath) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" ");
                if (parts.length == 2) {
                    String tickerSymbol = parts[0];
                    double price = Double.parseDouble(parts[1]);
                    stockPrices.put(tickerSymbol, price);
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public double getCurrentValue(String tickerSymbol) {
        return stockPrices.getOrDefault(tickerSymbol, 0.0);
    }
}// end class