package DataClasses;

public class Account {

    private String name;
    private int amount;

    public Account(int amount, String name){
        this.amount = amount;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getAmount() {
        return amount;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
