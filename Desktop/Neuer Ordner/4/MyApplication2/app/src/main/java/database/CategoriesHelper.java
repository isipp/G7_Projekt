package database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CategoriesHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "app.db";

    private static final int DATABASE_VERSION = 11;

    private static final String TABLE_NAME = "categories";

    public CategoriesHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

        String SQL_CREATE_CATEGORIES_TABLE =  "CREATE TABLE " + "categories" + " ("
                + "name" + " VARCHAR, "
                + "id" + " INTEGER);";

        db.execSQL(SQL_CREATE_CATEGORIES_TABLE);

        db.execSQL("insert into " + TABLE_NAME + " values('FOOD', 1)");
        db.execSQL("insert into " + TABLE_NAME + " values('CAR', 2)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldV, int newV) {
        if (oldV != newV) {

            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db);
        }
    }

    public Cursor readAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from " + TABLE_NAME, null);
        return cursor;
    }
    //Adding Categories
    public void addCat(String name, int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("insert into " + TABLE_NAME + " values('"+ name +"',"+ id + ")");

    }
    //Deleting Categories
    public void deleteCat(String name, int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + TABLE_NAME + " where id like " + id + " and name like '" + name + "'" );
    }

    //Changing Categories
    public void changeCat(String nameOld, int idOld, String nameNew, int idNew){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("update " + TABLE_NAME + " set name=" + "'" + nameNew + "', id=" + idNew + " where name like " + "'" + nameOld + "' " + "and " + "id=" + idOld);
    }
}
