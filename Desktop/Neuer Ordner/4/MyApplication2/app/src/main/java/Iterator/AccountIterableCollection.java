package Iterator;

import java.util.ArrayList;

import DataClasses.Account;

public class AccountIterableCollection implements IterableCollection
{
    ArrayList<Account> accountList = new ArrayList<>();

    public Iterator createIterator() {
        return new AccountIterableCollectionIterator(this);
    }
    public AccountIterableCollection(ArrayList<Account> accountList){
        this.accountList = accountList;
    }
    public int size(){
        return this.accountList.size();
    }
    public Account getObjAtIndex(int i){
        return accountList.get(i);
    }
}
