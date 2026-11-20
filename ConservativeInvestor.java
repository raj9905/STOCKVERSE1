
public class ConservativeInvestor extends Investor {

    public ConservativeInvestor(String name, double cashBalance) {
        super(name, cashBalance);
    }

    @Override
    public String getStrategyName() {
        return "Conservative - Lower Risk";
    }
}
