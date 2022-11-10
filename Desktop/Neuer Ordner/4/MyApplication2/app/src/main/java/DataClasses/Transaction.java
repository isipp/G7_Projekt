package DataClasses;

import java.util.Date;


public abstract class Transaction {

    private int id;
    //private TransactionType type;
    private String title;
    private int accountId;
    private double amount;
    private String categories;
    private final Date insertedAt;
    private Date lastModifiedAt;
    private Date doneAt;

    public Transaction(int id, /*TransactionType type,*/ String title, int accountId, double amount, String categories, Date insertedAt, Date lastModifiedAt, Date doneAt) {
        if(id < 0) throw new IllegalArgumentException("transaction/constructor out_of_bounds id");
        if(insertedAt.after(lastModifiedAt)) throw new IllegalArgumentException("transaction/constructor inconsistency insertedAt_after_modified");

        this.id = id;
        //setType(type);
        setTitle(title);
        setAccountId(accountId);
        setAmount(amount);
        setCategories(categories);
        this.insertedAt = insertedAt;
        setLastModifiedAt(lastModifiedAt);
        setDoneAt(doneAt);
    }

    public int getId() {
        return this.id;
    }
    //not set id, id stays the same

    /*public TransactionType getType() { return type; }
    public void setType(TransactionType type) {
        this.type = type;
    }*/
    public abstract TransactionType getType();

    public String getTitle() { return this.title; }
    public void setTitle(String title) {
        if(title == null) throw new IllegalArgumentException("transaction/getTitle is_null title");
        if(title.length() == 0) throw new IllegalArgumentException("transaction/getTitle empty title");
        this.title = title;
    }

    public int getAccountId() { return accountId; }
    public void setAccountId(int accoundId) {
        if(id < 0) throw new IllegalArgumentException("transaction/setAccountId out_of_bounds id");
        else this.accountId = accountId;
    }

    public double getAmount() { return this.amount; }
    public void setAmount(double amount) {
        if(amount < 0) throw new IllegalArgumentException("transaction/setAmount out_of_bounds amount");
        this.amount = amount;
    }

    public String getCategories() { return categories; }
    public void setCategories(String categories) { this.categories = categories; }

    public Date getInsertedAt() { return insertedAt; }
    //not able to change, so no setter for insertedAt

    public Date getLastModifiedAt() { return lastModifiedAt; }
    public void setLastModifiedAt(Date lastModifiedAt) {
        if(lastModifiedAt.after(new Date())) throw new IllegalArgumentException("transaction/constructor out_of_bounds lastModifiedAt_in_future (" + lastModifiedAt.getTime() + ")");
        else this.lastModifiedAt = lastModifiedAt;
    }

    public Date getDoneAt() { return doneAt; }
    public void setDoneAt(Date doneAt) {
        this.doneAt = doneAt;
    }


}
