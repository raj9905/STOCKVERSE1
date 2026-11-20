
public class Stock {

    private String name;
    private String symbol;
    private double price;

    public Stock(String name, String symbol, double price) {
        this.name = name;
        this.symbol = symbol;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public String getSymbol() {
        return symbol;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        if (price > 0) {
            this.price = price;
        }
    }

    // Calculate value of owned shares
    public double calculateValue(int quantity) {

        if (quantity < 0) {
            return 0;
        }

        return price * quantity;
    }

    // First version: no parameter
    public void displayStock() {
        System.out.printf(
            "%-15s (%s) Rs. %.2f%n",
            name, symbol, price
        );
    }

    // Second version: one String parameter
    public void displayStock(String heading) {
        System.out.println(heading);
        displayStock();
    }
}
