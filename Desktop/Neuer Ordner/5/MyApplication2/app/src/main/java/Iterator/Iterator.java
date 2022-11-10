package Iterator;

import DataClasses.Account;

public interface Iterator {
    Account getNext();

    boolean hasMore();
}
