/**
 * @author Easton H. Herbon
 *
 * @brief This record is a compact and simple way to store and get variables in the program.
 */

record StockPurchase(String stockName, String tickerSymbol, double purchasePrice, int numberOfShares) {

    public double getValue(RealTimeFeed realTimeFeed) {
        double currentPrice = realTimeFeed.getCurrentValue(tickerSymbol);
        return currentPrice * numberOfShares; // Calculate the value based on the current price
    }
}// end class


/* intelliJ offered to switch this to a record so not entirely my creation.
 I found this as a cool tool to use and carry immutable data after doing some extensive research.

 If you would like to see my original that worked the same, I can send that to you as well.

 */