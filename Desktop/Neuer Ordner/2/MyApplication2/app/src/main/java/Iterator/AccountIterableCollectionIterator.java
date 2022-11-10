package Iterator;

import DataClasses.Account;

public class AccountIterableCollectionIterator implements Iterator{

    private AccountIterableCollection  collection;
    private int collectionSize;
    private int currentIndex = -1;

    public AccountIterableCollectionIterator(AccountIterableCollection collection){
        this.collection = collection;
        this.collectionSize = collection.size();
    }

    @Override
    public Account getNext() {
        currentIndex++;
        return this.collection.getObjAtIndex(currentIndex);
    }

    @Override
    public boolean hasMore() {
        return currentIndex + 1 < collectionSize;
    }
}
