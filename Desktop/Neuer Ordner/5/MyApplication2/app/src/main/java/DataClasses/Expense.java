package DataClasses;

import java.util.Date;

public class Expense extends Transaction {
    TaxationStrategyI taxation;

    public Expense(int id, String title, int accountId, double amount, String participant, Date insertedAt, Date lastModifiedAt, Date doneAt, double taxRate) {
        super(id, title, accountId, amount, participant, insertedAt, lastModifiedAt, doneAt);
        this.taxation = new VATaxation(taxRate);
    }


    public double getTaxRate() {
        return taxation.getTaxRate();
    }
    public void setTaxRate(double taxRate) {
        taxation.setTaxRate(taxRate);
    }


    @Override
    public TransactionType getType() {
        return TransactionType.EXPENSE;
    }
}
