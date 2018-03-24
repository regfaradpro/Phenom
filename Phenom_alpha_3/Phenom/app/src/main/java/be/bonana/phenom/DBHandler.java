package be.bonana.phenom;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liron.bonana on 21/05/2017.
 */

public class DBHandler extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "phenomInfo";
    // Contacts table name
    private static final String TABLE_DESCRIPTION = "description";
    // Shops Table Columns names
    private static final String KEY_ID = "userName";
    private static final String KEY_NAME = "description";
    private static final String KEY_END_DATE = "endDate";
    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE" + TABLE_DESCRIPTION + "("
        + KEY_ID + " TEXT," + KEY_NAME + " TEXT,"
        + KEY_END_DATE + " DATE" + ")" ;
        db.execSQL(CREATE_CONTACTS_TABLE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
// Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DESCRIPTION);
// Creating tables again
        onCreate(db);
    }

    // Adding new description
    public void addShop(Defis defi) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_ID, defi.getUserName()); // Username
        values.put(KEY_NAME, defi.getDescription()); // Description
        values.put(KEY_END_DATE, defi.getEndDate()); // Ennd date
// Inserting Row
        db.insert(TABLE_DESCRIPTION, null, values);
        db.close(); // Closing database connection
    }

    // Getting one shop
    public Defis getDefi(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_DESCRIPTION, new String[] { KEY_ID,
                        KEY_NAME, KEY_END_DATE }, KEY_END_DATE + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        Defis  defi = new Defis(cursor.getString(0), cursor.getString(1), cursor.getString(2));
// return description
        return defi;
    }

    // Getting All Shops
    public List<Defis> getAllShops() {
        List<Defis> descriptionList = new ArrayList<Defis>();
// Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_DESCRIPTION;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
// looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Defis defi = new Defis();
                defi.setUserName(cursor.getString(0));
                defi.setDescription(cursor.getString(1));
                defi.setEndDate(cursor.getString(2));
// Adding contact to list
                descriptionList.add(defi);
            } while (cursor.moveToNext());
        }
// return contact list
        return descriptionList;
    }

    // Getting shops Count
    public int getShopsCount() {
        String countQuery = "SELECT * FROM " + TABLE_DESCRIPTION;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();
// return count
        return cursor.getCount();
    }

    // Updating a shop
    public int updateShop(Defis defi) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, defi.getDescription());
        values.put(KEY_END_DATE, defi.getEndDate());
// updating row
        return db.update(TABLE_DESCRIPTION, values, KEY_ID + " = ?",
                new String[]{String.valueOf(defi.getUserName())});
    }

    // Deleting a shop
    public void deleteShop(Defis defi) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_DESCRIPTION, KEY_ID + " = ?",
                new String[] { String.valueOf(defi.getUserName()) });
        db.close();
    }

}
