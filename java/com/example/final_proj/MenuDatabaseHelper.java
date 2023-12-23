package com.example.final_proj;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;

public class MenuDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "menu_database";
    private static final int DATABASE_VERSION = 4;

    private static final String TABLE_MENU = "menu";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_PRICE = "price";

    public MenuDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableQuery = "CREATE TABLE " + TABLE_MENU + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_NAME + " TEXT," +
                COLUMN_PRICE + " REAL)";
        db.execSQL(createTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop the old table and recreate it
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MENU);
        onCreate(db);
    }


    public void addMenuItem(String name, double price) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_PRICE, price);
        db.insert(TABLE_MENU, null, values);
        db.close();
    }

    public List<MenuItem> getMenuItems() {
        List<MenuItem> menuItems = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_MENU;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        try {
            int idColumnIndex = cursor.getColumnIndexOrThrow(COLUMN_ID);
            int nameColumnIndex = cursor.getColumnIndexOrThrow(COLUMN_NAME);
            int priceColumnIndex = cursor.getColumnIndexOrThrow(COLUMN_PRICE);

            while (cursor.moveToNext()) {
                int id = cursor.getInt(idColumnIndex);
                String name = cursor.getString(nameColumnIndex);
                double price = cursor.getDouble(priceColumnIndex);

                MenuItem menuItem = new MenuItem(id, name, price);
                menuItems.add(menuItem);
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace(); // Log the error for debugging
        } finally {
            cursor.close();
            db.close();
        }

        return menuItems;
    }

    public void updateMenuItem(int id, String newName, double newPrice) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, newName);
        values.put(COLUMN_PRICE, newPrice);
        db.update(TABLE_MENU, values, COLUMN_ID + "=?", new String[]{String.valueOf(id)});
        db.close();
    }

    public void deleteMenuItem(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_MENU, COLUMN_ID + "=?", new String[]{String.valueOf(id)});
        db.close();
    }
}
