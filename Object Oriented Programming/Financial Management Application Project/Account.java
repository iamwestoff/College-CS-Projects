/**
 * @author Easton H. Herbon
 *
 * @brief This abstract class is used as the focal point of account data and creation
 * for the Checking and Equity accounts respectively.
 */

import java.util.ArrayList;
import java.util.List;

abstract class Account {
    protected int accountNumber;
    protected String firstName;
    protected String lastName;
    protected String primaryAddress;
    protected double balance;
    protected List<String> transactions;

    // constructor
    public Account(int accountNumber, String firstName, String lastName, String primaryAddress, double initialBalance) {
        this.accountNumber = accountNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.primaryAddress = primaryAddress;
        this.balance = initialBalance;
        this.transactions = new ArrayList<>();
    }

    // method used for depositing money
    public void deposit(double amount) {
        balance += amount;
        transactions.add("Deposit: $" + amount);
    }

    // method used for withdrawing money
    public void withdraw(double amount) {
        if (amount <= balance) {
            balance -= amount;
            transactions.add("Withdrawal: $" + amount);
        } else {
            System.out.println("Insufficient funds for withdrawal.");
        }
    }

    public abstract void generateReport(Report report);

    public double getBalance() {
        return balance;
    }

    public int getAccountNumber() {
        return accountNumber;
    }
}// end class