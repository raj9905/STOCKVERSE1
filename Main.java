
import java.util.Scanner;

public class Main {

    static Scanner sc = new Scanner(System.in);

    // Available stocks
    static Stock[] stocks = {
        new Stock("ALPHA TECH", "ALT", 250.0),
        new Stock("NOVA ENERGY", "NVE", 180.0),
        new Stock("ORBIT BANK", "ORB", 420.0)
    };

    // Investor and portfolio
    static Investor investor;
    static Portfolio portfolio = new Portfolio(stocks.length);

    // Transaction records
    static Transaction[] history = new Transaction[100];
    static int transactionCount = 0;

    public static void main(String[] args) {

        selectInvestor();

        int choice;

        do {
            System.out.println("\n================================");
            System.out.println("       STOCKVERSE v1.0");
            System.out.println("  Virtual Stock Market Simulator");
            System.out.println("================================");

            System.out.println("1. View Stock Market");
            System.out.println("2. View Cash Balance");
            System.out.println("3. Buy Shares");
            System.out.println("4. Sell Shares");
            System.out.println("5. View Portfolio");
            System.out.println("6. Update Stock Price");
            System.out.println("7. Transaction History");
            System.out.println("8. Investor Profile");
            System.out.println("9. Portfolio Valuation");
            System.out.println("10. Exit");

            choice = readInt("Enter your choice: ");

            switch (choice) {

                case 1:
                    showStocks();
                    break;

                case 2:
                    showBalance();
                    break;

                case 3:
                    buyShares();
                    break;

                case 4:
                    sellShares();
                    break;

                case 5:
                    portfolio.displayPortfolio(stocks);
                    break;

                case 6:
                    updateStockPrice();
                    break;

                case 7:
                    showHistory();
                    break;

                case 8:
                    investor.displayProfile();
                    break;

                case 9:
                    showPortfolioValuation();
                    break;

                case 10:
                    System.out.println(
                        "Thank you for using STOCKVERSE!"
                    );
                    break;

                default:
                    System.out.println("Invalid choice!");
            }

        } while (choice != 10);

        sc.close();
    }

    // Select investor type
    static void selectInvestor() {

        System.out.println("\n===== WELCOME TO STOCKVERSE =====");
        System.out.println("1. Conservative Investor");
        System.out.println("2. Aggressive Investor");

        int choice;

        do {
            choice = readInt("Select investor type: ");

            if (choice != 1 && choice != 2) {
                System.out.println("Please enter 1 or 2.");
            }

        } while (choice != 1 && choice != 2);

        System.out.print("Enter your name: ");
        String name = sc.nextLine().trim();

        if (name.isEmpty()) {
            name = "Player 1";
        }

        if (choice == 1) {
            investor = new ConservativeInvestor(name, 100000.0);
        } else {
            investor = new AggressiveInvestor(name, 100000.0);
        }

        investor.displayProfile();
    }

    // Read integer safely
    static int readInt(String message) {

        while (true) {

            System.out.print(message);

            try {
                return Integer.parseInt(sc.nextLine().trim());

            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid whole number.");
            }
        }
    }

    // Read decimal safely
    static double readDouble(String message) {

        while (true) {

            System.out.print(message);

            try {
                double value =
                    Double.parseDouble(sc.nextLine().trim());

                if (Double.isFinite(value)) {
                    return value;
                }

            } catch (NumberFormatException e) {
                // Invalid input is handled below.
            }

            System.out.println("Please enter a valid number.");
        }
    }

    // Display stock market
    static void showStocks() {

        System.out.println("\n--- STOCK MARKET ---");

        for (int i = 0; i < stocks.length; i++) {

            System.out.print((i + 1) + ". ");
            stocks[i].displayStock();
        }
    }

    // Display cash balance
    static void showBalance() {

        System.out.printf(
            "Available Cash: Rs. %.2f%n",
            investor.getCashBalance()
        );
    }

    // Calculate transaction amount
    static double calculateTotal(double price, int quantity) {
        return price * quantity;
    }

    // Validate stock selection
    static boolean validStock(int index) {
        return index >= 0 && index < stocks.length;
    }

    // Buy shares
    static void buyShares() {

        showStocks();

        int index = readInt("Select stock number: ") - 1;

        if (!validStock(index)) {
            System.out.println("Invalid stock selection!");
            return;
        }

        int quantity = readInt("Enter quantity: ");

        if (quantity <= 0) {
            System.out.println("Quantity must be greater than zero!");
            return;
        }

        if (transactionCount >= history.length) {
            System.out.println("Transaction history is full!");
            return;
        }

        double price = stocks[index].getPrice();

        double total = calculateTotal(price, quantity);

        if (!Double.isFinite(total) ||
            total > investor.getCashBalance()) {

            System.out.println("Insufficient balance!");
            return;
        }

        if (investor.debit(total)) {

            portfolio.addShares(index, quantity);

            history[transactionCount++] = new Transaction(
                "BUY",
                stocks[index].getName(),
                quantity,
                price
            );

            System.out.printf(
                "Purchase successful! Total: Rs. %.2f%n",
                total
            );

            showBalance();

        } else {
            System.out.println("Purchase failed!");
        }
    }

    // Sell shares
    static void sellShares() {

        portfolio.displayPortfolio(stocks);

        int index = readInt("Select stock number: ") - 1;

        if (!validStock(index)) {
            System.out.println("Invalid stock selection!");
            return;
        }

        int quantity = readInt("Enter quantity to sell: ");

        if (quantity <= 0 ||
            quantity > portfolio.getQuantity(index)) {

            System.out.println(
                "Invalid quantity or insufficient shares!"
            );
            return;
        }

        if (transactionCount >= history.length) {
            System.out.println("Transaction history is full!");
            return;
        }

        double price = stocks[index].getPrice();

        double total = calculateTotal(price, quantity);

        if (!Double.isFinite(total)) {
            System.out.println("Invalid sale amount!");
            return;
        }

        if (portfolio.removeShares(index, quantity)) {

            investor.credit(total);

            history[transactionCount++] = new Transaction(
                "SELL",
                stocks[index].getName(),
                quantity,
                price
            );

            System.out.printf(
                "Sale successful! Received: Rs. %.2f%n",
                total
            );

            showBalance();

        } else {
            System.out.println("Sale unsuccessful!");
        }
    }

    // Update stock price
    static void updateStockPrice() {

        showStocks();

        int index = readInt("Select stock number: ") - 1;

        if (!validStock(index)) {
            System.out.println("Invalid stock selection!");
            return;
        }

        double newPrice = readDouble("Enter new stock price: ");

        if (newPrice <= 0) {
            System.out.println("Price must be greater than zero!");
            return;
        }

        stocks[index].setPrice(newPrice);

        System.out.println("Stock price updated successfully!");

        stocks[index].displayStock(
            "Updated Stock Details:"
        );
    }

    // Display transaction history
    static void showHistory() {

        System.out.println("\n--- TRANSACTION HISTORY ---");

        if (transactionCount == 0) {
            System.out.println("No transactions recorded yet.");
            return;
        }

        for (int i = 0; i < transactionCount; i++) {

            System.out.print((i + 1) + ". ");
            history[i].displayTransaction();
        }
    }

    // Calculate portfolio and total account value
    static void showPortfolioValuation() {

        double totalValue = 0;

        System.out.println("\n--- PORTFOLIO VALUATION ---");

        for (int i = 0; i < stocks.length; i++) {

            int quantity = portfolio.getQuantity(i);

            double value = stocks[i].calculateValue(quantity);

            totalValue += value;

            if (quantity > 0) {

                System.out.printf(
                    "%s | Shares: %d | Value: Rs. %.2f%n",
                    stocks[i].getName(),
                    quantity,
                    value
                );
            }
        }

        double cash = investor.getCashBalance();

        double accountValue = totalValue + cash;

        System.out.printf(
            "Portfolio Market Value: Rs. %.2f%n",
            totalValue
        );

        System.out.printf(
            "Available Cash: Rs. %.2f%n",
            cash
        );

        System.out.printf(
            "TOTAL ACCOUNT VALUE: Rs. %.2f%n",
            accountValue
        );
    }
}
