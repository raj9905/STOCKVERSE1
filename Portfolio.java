
public class Portfolio {

    private int[] quantities;

    // Constructor
    public Portfolio(int numberOfStocks) {
        quantities = new int[numberOfStocks];
    }

    // Get quantity of a stock
    public int getQuantity(int stockIndex) {

        if (stockIndex >= 0 &&
            stockIndex < quantities.length) {

            return quantities[stockIndex];
        }

        return 0;
    }

    // Add shares after purchase
    public void addShares(int stockIndex, int quantity) {

        if (stockIndex >= 0 &&
            stockIndex < quantities.length &&
            quantity > 0) {

            quantities[stockIndex] += quantity;
        }
    }

    // Remove shares after sale
    public boolean removeShares(int stockIndex, int quantity) {

        if (stockIndex < 0 ||
            stockIndex >= quantities.length ||
            quantity <= 0 ||
            quantities[stockIndex] < quantity) {

            return false;
        }

        quantities[stockIndex] -= quantity;
        return true;
    }

    // Display portfolio
    public void displayPortfolio(Stock[] stocks) {

        double totalValue = 0;
        boolean hasShares = false;

        System.out.println("\n--- YOUR PORTFOLIO ---");

        for (int i = 0; i < quantities.length; i++) {

            if (quantities[i] > 0) {

                double value =
                    stocks[i].calculateValue(quantities[i]);

                totalValue += value;
                hasShares = true;

                System.out.printf(
                    "%s | Shares: %d | Value: Rs. %.2f%n",
                    stocks[i].getName(),
                    quantities[i],
                    value
                );
            }
        }

        if (!hasShares) {
            System.out.println("You do not own any shares yet.");
        }

        System.out.printf(
            "Total Portfolio Value: Rs. %.2f%n",
            totalValue
        );
    }
}
