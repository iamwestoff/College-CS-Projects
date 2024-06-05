/**
 * @author Easton H. Herbon
 *
 * @brief This class is used to generate the portfolio report to the HTML file and keep track to calculate said portfolio(s).
 */

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Portfolio {
    private final List<Account> accounts = new ArrayList<>();

    // add accounts
    public void addAccount(Account account) {
        accounts.add(account);
    }

    // generate portfolio report
    public void generateReport(String outputPath) {
        // sort accounts by balance
        accounts.sort(Comparator.comparing(Account::getBalance).reversed());

        try {
            Report report = new Report();

            report.addHeader("Portfolio Report");
            double totalPortfolioValue = 0;

            for (Account account : accounts) {
                account.generateReport(report);
                totalPortfolioValue += account.getBalance();
            }

            // for decimal deletion to .00
            DecimalFormat df = new DecimalFormat("#.00");
            String formattedTotalValue = df.format(totalPortfolioValue);

            report.addHeader("Total Portfolio Value $" + formattedTotalValue);
            report.addFooter();

            report.saveToFile(outputPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // used to find the total portfolio value
    public double getTotalValue() {
        double totalValue = 0;
        for (Account account : accounts) {
            totalValue += account.getBalance();
        }
        return totalValue;
    }
}// end main