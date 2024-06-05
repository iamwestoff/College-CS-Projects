/**
 * @author Easton H. Herbon
 *
 * @brief This class is used in the creation of Equity Accounts.
 */

import java.util.ArrayList;
import java.util.List;

class EquityAccount extends Account {
    private final List<StockPurchase> stockPurchases;

    public EquityAccount(int accountNumber, String firstName, String lastName, String primaryAddress) {
        super(accountNumber, firstName, lastName, primaryAddress, 0);
        stockPurchases = new ArrayList<>();
    }

    public void addStockPurchase(String stockName, String tickerSymbol, double purchasePrice, int numberOfShares) {
        StockPurchase stockPurchase = new StockPurchase(stockName, tickerSymbol, purchasePrice, numberOfShares);
        stockPurchases.add(stockPurchase);
        balance += stockPurchase.getValue(new RealTimeFeed("equities"));
        transactions.add("Stock Purchase: " + stockName + " (" + tickerSymbol + ")");
    }

    @Override
    public void generateReport(Report report) {
        report.addHeader("Equity Account Report for Account #" + getAccountNumber());
        report.addData("Account Holder", firstName + " " + lastName);
        report.addData("Primary Address", primaryAddress);
        report.addData("Current Balance", "$" + balance);
        report.addStockHoldings(stockPurchases);
        report.addFooter();
    }
}// end class