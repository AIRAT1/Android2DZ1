package data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
    public static final String TABLE_MYTABLE = "myTable";
    private static final String COLUMN_ID = "_id";
    public static final String COLUMN_COMPANY_NAME = "companyName";
    private static final String DATABASE_MYTABLE = "CREATE TABLE "
            + TABLE_MYTABLE + " (" + COLUMN_ID + " integer primary key autoincrement, "
            + COLUMN_COMPANY_NAME + " text);";


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.beginTransaction();
        try {
            db.execSQL(DATABASE_MYTABLE);
            db.setTransactionSuccessful();
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            db.endTransaction();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MYTABLE);
        onCreate(db);
    }
}
