package DataClasses;

import java.util.Date;

public class Income extends Transaction {
    public Income(int id, String title, int accountId, double amount, String categories, Date insertedAt, Date lastModifiedAt, Date doneAt) {
        super(id, title, accountId, amount, categories, insertedAt, lastModifiedAt, doneAt);
    }

    @Override
    public TransactionType getType() {
        return TransactionType.INCOME;
    }

    //refund
    public Expense createRefund() {
        final int id = database.ID_Helper.getNewId();
        final Date now = new Date();
        return new Expense(id, "refund of " + getTitle(), getAccountId(), getAmount(), getCategories(), now, now, null, 0.0);
    }
}
