## Assignment Description

Programming Assignment 2 asks you to create a financial management application. Our financial management client needs some software to keep track of different types of accounts. Checking accounts have an account number, a first and last name for the account holder, a primary mailing address of the account holder, and a balance. One can make deposits and withdrawals from a checking account. Our client needs to generate reports that include the account number, the name and primary address of the account holder, the current balance, and information about all the transactions.

In addition to checking accounts our client manages equity accounts. Equity accounts have an account number, a first and last name for the account holder, a primary mailing address of the account holder, and a list of stock purchases. Each stock purchase has a stock name, ticker symbol, purchase price per share, and number of shares at the purchase price. Our client needs to generate reports that include the account number, the name and primary address of the account holder, a list of all stock information, and a total value for the equities in the account.

You will create an interface that you can use to query for current stock prices (the current prices may be different than the purchase prices in the accounts). It should be called RealTimeFeed. The interface can be used to retrieve stock prices from some outside source (API) or it can be used to create a mock set of stock prices that can be used for testing.

You only need to use the interface to create a mock source of stock prices. The prices will come from a file called equities.txt. The path to this fill will be passed in to the main() function through the args. The 'fake' current values for stocks will be in these files. Your implementing class will read these files and make the data in them available by equity. For example, the two files might look like this:

equities.txt
``` txt
MSFT 123.45
GOOG 234.56
GE 12.34
```

Someone with a reference to an RealTimeFeed object can request the current price like this:

``` C++
double currentVal = realTimeFeed.getCurrentValue("GOOG"); //returns the value 234.56
```

Finally, create a Portfolio class that is used to hold one or more accounts. The Portfolio will have methods to add a new account, generate a report for all of the accounts, and to get the value of all of the accounts. 

Create a driver with at least three accounts. Store them in a Portfolio and generate a report for all of the accounts. The reports should be written to a single html file that can be opened in a browser. Print the reports from the most valuable account to the least valuable account (use either the Comparator or the Comparable interface and the Collections.sort() method).

Inheritance and Polymorphism
- use a hierarchy for accounts
- create abstract class(es)

Checking
- can record all deposits and withdraws

Equity
- can record stock purchases

RealTimeFeed
- can read in a file and expose values by their name

Report
- shows all transactions
- generate html file
- in order of value

Portfolio
- holds multiple accounts

Driver
- hard codes the creation of the accounts and generates a report file when I run the app
- accepts an arg with a file path to the mock stock file data
