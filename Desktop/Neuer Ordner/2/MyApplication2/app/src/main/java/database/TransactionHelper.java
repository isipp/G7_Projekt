package database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import DataClasses.Account;
import DataClasses.Transaction;
import DataClasses.TransactionType;

public class TransactionHelper extends SQLiteOpenHelper {

    private final String TABLE_NAME = "transactions";

    public TransactionHelper(Context context) {
        super(context, DBConfig.DATABASE_NAME, null, DBConfig.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_USER_TABLE =  "CREATE TABLE " + TABLE_NAME + " ("
                + "id           INT UNIQUE, "
                + "type         INT," // expanse or income, TransactionType.ordinal()
                + "title        TEXT,"
                + "accountId    INT ,"
                + "amount       DOUBLE ,"
                + "participant  TEXT,"
                + "insertedAt   INT,"  //date when user put it in app in ms
                + "modifiedAt   INT,"  // in ms
                + "doneAt       INT"   //date when transaction was done, in ms
                + " );";
        db.execSQL(SQL_CREATE_USER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }


    public List<Transaction> getAll() {
        Cursor cursor = this.getReadableDatabase().rawQuery("select * from " + TABLE_NAME, null);
        return cursorToList(cursor);
    }

    public List<Transaction> getAllOfType(TransactionType type) {
        Cursor cursor = this.getReadableDatabase().rawQuery("select * from " + TABLE_NAME + " where type==" + type.ordinal(), null);
        return cursorToList(cursor);
    }

    public Transaction getOneById(int id) {
        Cursor cursor = this.getReadableDatabase().rawQuery("select * from " + TABLE_NAME + " where id==" + id, null);
        List<Transaction> list = cursorToList(cursor);
        if(list.size() > 1) throw new RuntimeException("db/TransactionHelper/getOneById to_many_results from db");
        else return list.get(0);
    }

    private List<Transaction> cursorToList(Cursor cursor) {
        List<Transaction> resultList = new ArrayList<Transaction>();

        int idIndex = cursor.getColumnIndex("id");
        int typeIndex = cursor.getColumnIndex("type");
        int titleIndex = cursor.getColumnIndex("title");
        int accountIdIndex = cursor.getColumnIndex("accountId");
        int amountIndex = cursor.getColumnIndex("amount");
        int participantIndex = cursor.getColumnIndex("participant");
        int insertedAtIndex = cursor.getColumnIndex("insertedAt");
        int modifiedAtIndex = cursor.getColumnIndex("modifiedAt");
        int doneAtIndex = cursor.getColumnIndex("doneAt");


        while (cursor.moveToNext()){
            resultList.add(new Transaction(
                    cursor.getInt(idIndex),
                    TransactionType.values()[cursor.getInt(typeIndex)],
                    cursor.getString(titleIndex),
                    cursor.getInt(accountIdIndex),
                    cursor.getDouble(amountIndex),
                    cursor.getString(participantIndex),
                    new java.util.Date((long)cursor.getLong(insertedAtIndex) * 1000),
                    new java.util.Date((long)cursor.getLong(modifiedAtIndex) * 1000),
                    new java.util.Date((long)cursor.getLong(doneAtIndex) * 1000)
            ));
        }

        return resultList;
    }

}
