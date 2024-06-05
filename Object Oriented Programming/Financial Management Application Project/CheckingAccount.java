/**
 * @author Easton H. Herbon
 *
 * @brief This class is used in the creation of Checking Accounts.
 */

class CheckingAccount extends Account {
    public CheckingAccount(int accountNumber, String firstName, String lastName, String primaryAddress, double initialBalance) {
        super(accountNumber, firstName, lastName, primaryAddress, initialBalance);
    }

    @Override
    public void generateReport(Report report) {
        report.addHeader("Checking Account Report for Account #" + getAccountNumber());
        report.addData("Account Holder", firstName + " " + lastName);
        report.addData("Primary Address", primaryAddress);
        report.addData("Current Balance", "$" + balance);
        report.addTransactionHistory(transactions);
        report.addFooter();
    }
}// end class