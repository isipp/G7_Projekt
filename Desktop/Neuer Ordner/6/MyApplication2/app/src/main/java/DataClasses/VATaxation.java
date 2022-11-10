package DataClasses;

public class VATaxation implements TaxationStrategyI {
    private double taxRate;

    public VATaxation(double taxRate) {
        setTaxRate(taxRate);
    }

    @Override
    public double getTaxRate() {
        return this.taxRate;
    }
    public void setTaxRate(double taxRate) {
        if (taxRate >= 0 && taxRate < 1) this.taxRate = taxRate;
        else
            throw new IllegalArgumentException("DataClasses/Expense/setTaxRate taxrate have to be between 0 and 1");
    }



    @Override
    public double calculateTaxes(double transactionAmount) {
        return transactionAmount * this.taxRate;
    }




}
