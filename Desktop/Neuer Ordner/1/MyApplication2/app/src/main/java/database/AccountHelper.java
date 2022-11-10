package database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class AccountHelper extends SQLiteOpenHelper {

    String TABLE_NAME = "account";

    //Database name
    private static final String DATABASE_NAME = "app.db";
    //Database version
    private static  int DATABASE_VERSION = 10;

    public AccountHelper(Context context) {
        super(context, DBConfig.DATABASE_NAME, null, DBConfig.DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        String SQL_CREATE_USER_TABLE =  "CREATE TABLE " + "account" + " ("
                + "amount" + " INTEGER, "
                + "name" + " VARCHAR "
                + " );";

        // Execute the SQL statement
        db.execSQL(SQL_CREATE_USER_TABLE);

        // Insert Into Table
        db.execSQL("insert into " + TABLE_NAME + " values(1, 'Bank Austria')");
        db.execSQL("insert into " + TABLE_NAME + " values(2, 'Raif')");
        db.execSQL("insert into " + TABLE_NAME + " values(3, 'Raif')");
        db.execSQL("insert into " + TABLE_NAME + " values(4, 'orf')");
        db.execSQL("insert into " + TABLE_NAME + " values(5, 'ggg')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldT, int newT) {
        if (oldT != newT) {
            // Simplest implementation is to drop all old tables and recreate them

            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db);

        }
    }

    public Cursor readAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from " + TABLE_NAME, null);
        return cursor;
    }
    //Adding Account
    public void addAcc(String name, int amount){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("insert into " + TABLE_NAME + " values("+ amount +",'"+ name + "')");

    }
    //Delete Account
    public void deleteAcc(String name, int amount){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + TABLE_NAME + " where amount like " + amount + " and name like '" + name + "'" );
    }
    //Changing Account
    public void changeAcc(String nameOld, int amountOld, String nameNew, int amountNew){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("update " + TABLE_NAME + " set name=" + "'" + nameNew + "', amount=" + amountNew + " where name like " + "'" + nameOld + "' " + "and " + "amount=" + amountOld);
    }
    // change amount in account, "change"- true plus, false minus
    public void changeValue(boolean change, int amount, int amountOld, String nameOld){
        SQLiteDatabase db = this.getWritableDatabase();

        if(change==true){
            int amountNew = amountOld + amount;
                    db.execSQL("update " + TABLE_NAME + " set amount=" + amountNew + " where name like " + "'" + nameOld + "' " + "and " + "amount=" + amountOld);
        }
        if(change==false){
            int amountNew = amountOld - amount;
            db.execSQL("update " + TABLE_NAME + " set amount=" + amountNew + " where name like " + "'" + nameOld + "' " + "and " + "amount=" + amountOld);
        }

    }

}