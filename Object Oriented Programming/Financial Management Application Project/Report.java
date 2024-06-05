/**
 * @author Easton H. Herbon
 *
 * @brief This class is used to create the HTML file
 */

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

class Report {
    private final StringBuilder reportContent;

    public Report() {
        reportContent = new StringBuilder();
        reportContent.append("<html><head><title>Financial Report</title></head><body>");
    }

    public void addHeader(String headerText) {
        reportContent.append("<h1>").append(headerText).append("</h1>");
    }

    public void addData(String label, String value) {
        reportContent.append("<p>").append(label).append(": ").append(value).append("</p>");
    }

    public void addTransactionHistory(List<String> transactions) {
        reportContent.append("<p>Transaction History:</p><ul>");
        for (String transaction : transactions) {
            reportContent.append("<li>").append(transaction).append("</li>");
        }
        reportContent.append("</ul>");
    }

    public void addStockHoldings(List<StockPurchase> stockPurchases) {
        reportContent.append("<p>Stock Holdings:</p><ul>");
        for (StockPurchase stockPurchase : stockPurchases) {
            reportContent.append("<li>").append(stockPurchase.stockName()).append(" (")
                    .append(stockPurchase.tickerSymbol()).append(")</li>");
        }
        reportContent.append("</ul>");
    }

    public void addFooter() {
        reportContent.append("</body></html>");
    }

    public void saveToFile(String filePath) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
            writer.write(reportContent.toString());
            System.out.println("Report saved to " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}// end class