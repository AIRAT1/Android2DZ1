package data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DBAdapter {
    SQLiteDatabase db;
    DBHelper dbHelper;
    Context context;

    public DBAdapter(Context context) {
        this.context = context;
    }
    public DBAdapter openToWrite() {
        dbHelper = new DBHelper(context, DBHelper.DB_NAME, null, 1);
        db = dbHelper.getWritableDatabase();
        return this;
    }
    public void close() {
        db.close();
    }
    public long insertData(String name) {
        ContentValues cv = new ContentValues();
        cv.put(DBHelper.COLUMN_NAME, name);
        openToWrite();
        long val = db.insert(DBHelper.TABLE_NAME, null, cv);
        close();
        return val;
    }
    public Cursor queryName() {
        String[] cols = {DBHelper.COLUMN_NAME};
        openToWrite();
        Cursor c = db.query(DBHelper.TABLE_NAME, cols, null, null, null, null, null);
        return c;
    }
    public long updateDetail(int rowId, String name) {
        ContentValues cv = new ContentValues();
        cv.put(DBHelper.COLUMN_NAME, name);
        openToWrite();
        long val = db.update(DBHelper.TABLE_NAME, cv, DBHelper.COLUMN_ID + " = " + rowId, null);
        close();
        return val;
    }
    public long deleteOneRecord(int rowId) {
        openToWrite();
        int val = db.delete(DBHelper.TABLE_NAME, DBHelper.COLUMN_ID, new String[]{String.valueOf(rowId)});
        close();
        return val;
    }
}
