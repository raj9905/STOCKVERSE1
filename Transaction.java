
public class Transaction {

    private String type;
    private String stockName;
    private int quantity;
    private double price;
    private double totalAmount;

    // Constructor
    public Transaction(
            String type,
            String stockName,
            int quantity,
            double price) {

        this.type = type;
        this.stockName = stockName;
        this.quantity = quantity;
        this.price = price;
        this.totalAmount = quantity * price;
    }

    // Display transaction details
    public void displayTransaction() {

        System.out.printf(
            "%-5s | %-15s | Qty: %d | Price: Rs. %.2f | Total: Rs. %.2f%n",
            type,
            stockName,
            quantity,
            price,
            totalAmount
        );
    }
}
