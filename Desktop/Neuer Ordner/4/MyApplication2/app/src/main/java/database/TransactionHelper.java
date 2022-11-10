package database;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import DataClasses.Expense;
import DataClasses.Income;
import DataClasses.Transaction;
import DataClasses.TransactionType;

public class TransactionHelper extends SQLiteOpenHelper {

    private final String TABLE_NAME = "transactions";

    public TransactionHelper(Context context) {
        super(context, DBConfig.DATABASE_NAME, null, DBConfig.DATABASE_VERSION);
        System.out.println("[APP] TransactionHelper/constr context=" + context.toString()); //TODO
    }

    @Override
    public void onCreate(SQLiteDatabase db) {


        System.out.println("[APP] db/TransactionHelper onCreate");//TODO
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        String SQL_CREATE_USER_TABLE = "CREATE TABLE " + TABLE_NAME + " ("
                + "id           INT UNIQUE, "
                + "type         INT," // expanse or income, TransactionType.ordinal()
                + "title        TEXT,"
                + "accountId    INT ,"
                + "amount       DOUBLE ,"
                + "categories  TEXT,"
                + "insertedAt   INT,"  //date when user put it in app in ms
                + "modifiedAt   INT,"  // in ms
                + "doneAt       INT"   //date when transaction was done, in ms
                + " );";
        db.execSQL(SQL_CREATE_USER_TABLE);


        String insert1sql = "INSERT INTO " + TABLE_NAME + " VALUES(1, " + TransactionType.INCOME.ordinal() + ", \"IncomeOne\", 100, 123.5, \"Peter\", 1637487674, 1637487674, 0)";
        String insert2sql = "INSERT INTO " + TABLE_NAME + " VALUES(2, " + TransactionType.EXPENSE.ordinal() + ", \"ExpenseOne\", 100, 121.2, \"Peter\", 1637487678, 1637487678, 0)";
        db.execSQL(insert1sql);
        db.execSQL(insert2sql);
        //TODO
        //insertOne(new Income(1, "IncomeOne", 100, 123.5, "Peter", new Date(), new Date(), new Date(0)));
        //insertOne(new Income(1, "IncomeTwo", 100, 212.1, "Peter", new Date(), new Date(), new Date(0)));
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldT, int newT) {
        if (oldT != newT) {
            // Simplest implementation is to drop all old tables and recreate them

            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db);

        }
    }


    public List<Transaction> getAll() {
        System.out.println("[APP] TransactionHelper getAll");
        Cursor cursor = query("select * from " + TABLE_NAME);
        return cursorToList(cursor);
    }

    public List<Transaction> getAllOfType(TransactionType type) {
        Cursor cursor = query("select * from " + TABLE_NAME + " where type==" + type.ordinal());
        return cursorToList(cursor);
    }

    public Transaction getOneById(int id) {
        Cursor cursor = query("select * from " + TABLE_NAME + " where id==" + id);
        List<Transaction> list = cursorToList(cursor);
        if (list.size() > 1)
            throw new RuntimeException("db/TransactionHelper/getOneById to_many_results from db");
        else return list.get(0);
    }

    @SuppressLint("DefaultLocale")
    public void insertOne(Transaction t) {
        final Date now = new Date();
        query(String.format("INSERT INTO " + TABLE_NAME + " VALUES(%d, %d, %s, %d, %f, %s, %d, %d, %d)",
                t.getId(),
                t.getType().ordinal(),
                t.getTitle(),
                t.getAccountId(),
                t.getAmount(),
                t.getCategories(),
                now.getTime(),
                now.getTime(),
                t.getDoneAt().getTime()));
    }

    private List<Transaction> cursorToList(Cursor cursor) {
        if(cursor == null) return new ArrayList<>();

        List<Transaction> resultList = new ArrayList<Transaction>();

        int idIndex = cursor.getColumnIndex("id");
        int typeIndex = cursor.getColumnIndex("type");
        int titleIndex = cursor.getColumnIndex("title");
        int accountIdIndex = cursor.getColumnIndex("accountId");
        int amountIndex = cursor.getColumnIndex("amount");
        int categoriesIndex = cursor.getColumnIndex("participant");
        int insertedAtIndex = cursor.getColumnIndex("insertedAt");
        int modifiedAtIndex = cursor.getColumnIndex("modifiedAt");
        int doneAtIndex = cursor.getColumnIndex("doneAt");


        while (cursor.moveToNext()) {
            TransactionType type = TransactionType.values()[cursor.getInt(typeIndex)];
            switch (type) {
                case EXPENSE:
                    int taxRateIndex = cursor.getColumnIndex("taxRate");
                    resultList.add(new Expense(
                            cursor.getInt(idIndex),
                            cursor.getString(titleIndex),
                            cursor.getInt(accountIdIndex),
                            cursor.getDouble(amountIndex),
                            cursor.getString(categoriesIndex),
                            new java.util.Date((long) cursor.getLong(insertedAtIndex) * 1000),
                            new java.util.Date((long) cursor.getLong(modifiedAtIndex) * 1000),
                            new java.util.Date((long) cursor.getLong(doneAtIndex) * 1000),
                            0.0 //cursor.getDouble(taxRateIndex) //expense specific
                    ));
                    break;
                case INCOME:
                    resultList.add(new Income(
                            cursor.getInt(idIndex),
                            cursor.getString(titleIndex),
                            cursor.getInt(accountIdIndex),
                            cursor.getDouble(amountIndex),
                            cursor.getString(categoriesIndex),
                            new java.util.Date((long) cursor.getLong(insertedAtIndex) ),
                            new java.util.Date((long) cursor.getLong(modifiedAtIndex) ),
                            new java.util.Date((long) cursor.getLong(doneAtIndex) )
                    ));
                    break;
                default:
                    throw new IllegalStateException("TransactionHelper/cursorToList: invalid_type in db ("
                            + cursor.getInt(cursor.getColumnIndex("id")) + ")");
            }
        }

        return resultList;
    }



    private Cursor query(String sql) {
        return this.getWritableDatabase().rawQuery(sql, null);
    }




}
