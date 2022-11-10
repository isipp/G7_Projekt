package database;

import java.util.Date;

public final class ID_Helper {
    public static int getNewId() {
        return (int) new Date().getTime();
    }
}
