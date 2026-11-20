
public class AggressiveInvestor extends Investor {

    public AggressiveInvestor(String name, double cashBalance) {
        super(name, cashBalance);
    }

    @Override
    public String getStrategyName() {
        return "Aggressive - Higher Risk";
    }
}
