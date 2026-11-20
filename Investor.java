
public abstract class Investor {

    private String name;
    private double cashBalance;

    public Investor(String name, double cashBalance) {
        this.name = name;
        this.cashBalance = cashBalance;
    }

    public String getName() {
        return name;
    }

    public double getCashBalance() {
        return cashBalance;
    }

    public boolean debit(double amount) {
        if (Double.isFinite(amount) &&
            amount > 0 &&
            amount <= cashBalance) {

            cashBalance -= amount;
            return true;
        }

        return false;
    }

    public void credit(double amount) {
        if (Double.isFinite(amount) && amount > 0) {
            cashBalance += amount;
        }
    }

    public abstract String getStrategyName();

    public void displayProfile() {
        System.out.println("\n--- INVESTOR PROFILE ---");
        System.out.println("Name: " + name);
        System.out.println("Strategy: " + getStrategyName());
        System.out.printf("Cash Balance: Rs. %.2f%n", cashBalance);
    }
}
