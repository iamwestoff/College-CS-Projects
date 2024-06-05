/**
 * @author Easton H. Herbon
 * @version 10.9.2023
 *
 * @brief This program is a financial tracker program that outputs the current values of Checking and Equity Accounts by the given to them by the user.
 */

import java.text.DecimalFormat;

public class Driver {
    public static void main(String[] args) {
        Portfolio portfolio = new Portfolio();

        // stock prices read in through file
        RealTimeFeed realTimeFeed = new RealTimeFeed("equities");

        // checking accounts
        Account checkingAccount1 = new CheckingAccount(3293810, "John", "Doe", "123 Main St", 1000.0);
        checkingAccount1.withdraw(200.56);
        checkingAccount1.deposit(80.53);
        Account checkingAccount2 = new CheckingAccount(94810, "Jane", "Smith", "456 Elm St", 2000.0);

        // equity accounts
        EquityAccount equityAccount1 = new EquityAccount(13467, "Alice", "Johnson", "789 Oak St");
        equityAccount1.addStockPurchase("Microsoft Co.", "MSFT", realTimeFeed.getCurrentValue("MSFT"), 10);
        equityAccount1.addStockPurchase("Google", "GOOG", realTimeFeed.getCurrentValue("GOOG"), 5);
        EquityAccount equityAccount2 = new EquityAccount(1903287, "Bob", "Williams", "101 Pine Gr");
        equityAccount2.addStockPurchase("I dont know what GE stands for.", "GE", realTimeFeed.getCurrentValue("GE"), 2);

        // adding accounts to portfolio
        portfolio.addAccount(checkingAccount1);
        portfolio.addAccount(checkingAccount2);
        portfolio.addAccount(equityAccount1);
        portfolio.addAccount(equityAccount2);

        // Generate a report for all accounts
        portfolio.generateReport("output.html");

        // Print the total portfolio value
        DecimalFormat df = new DecimalFormat("#.00");
        System.out.println("Total Portfolio Value: $" + df.format(portfolio.getTotalValue()));
    }
}// end class