package DataClasses;

public interface TaxationStrategyI {
    double getTaxRate();
    void setTaxRate(double taxRate);

    double calculateTaxes(double transactionAmount);

}
