package com.noamcanter.android.swapapp.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.noamcanter.android.swapapp.Item;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper2 extends SQLiteOpenHelper {
    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "ItemManager.db";
    // User table name
    private static final String TABLE_ITEM = "item";
    // User Table Columns names
    private static final String COLUMN_ITEM_ID = "item_id";
    private static final String COLUMN_ITEM_NAME = "item_name";
    private static final String COLUMN_ITEM_OWNER = "item_owner";
    private static final String COLUMN_ITEM_DESCRIPTION = "item_description";
    private static final String COLUMN_ITEM_IMAGE = "item_image";
    // create table sql query
    private String CREATE_ITEM_TABLE = "CREATE TABLE " + TABLE_ITEM + "("
            + COLUMN_ITEM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_ITEM_NAME + " TEXT,"
            + COLUMN_ITEM_OWNER + " TEXT," + COLUMN_ITEM_IMAGE + " BLOB,"  + COLUMN_ITEM_DESCRIPTION + " TEXT" +  ")";
    // drop table sql query
    private String DROP_ITEM_TABLE = "DROP TABLE IF EXISTS " + TABLE_ITEM;
    /**
     * Constructor
     *
     * @param context
     *///
    public DatabaseHelper2(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_ITEM_TABLE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Drop User Table if exist
        db.execSQL(DROP_ITEM_TABLE);
        // Create tables again
        onCreate(db);
    }
    /**
     * This method is to create item record
     *
     * @param item
     */
    public void addItem(Item item) throws SQLException {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ITEM_NAME, item.getItemName());
        values.put(COLUMN_ITEM_OWNER, item.getOwnerEmail());
        values.put(COLUMN_ITEM_IMAGE, item.getItemImage());
        values.put(COLUMN_ITEM_DESCRIPTION, item.getDescription());
        // Inserting Row
        db.insert(TABLE_ITEM, null, values);
        db.close();
    }
    /**
     * This method is to fetch all user and return the list of user records
     *
     * @return list
     */

    public List<Item> getAllItems() {
        // array of columns to fetch
        String[] columns = {
                COLUMN_ITEM_ID,
                COLUMN_ITEM_NAME,
                COLUMN_ITEM_OWNER,
                COLUMN_ITEM_IMAGE,
                COLUMN_ITEM_DESCRIPTION,
        };
        // sorting orders
        String sortOrder =
                COLUMN_ITEM_ID + " ASC";
        List<Item> itemList = new ArrayList<Item>();
        SQLiteDatabase db = this.getReadableDatabase();
        // query the user table
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id,user_name,user_email,user_password FROM user ORDER BY user_name;
         */
        Cursor cursor = db.query(TABLE_ITEM, //Table to query
                columns,    //columns to return
                null,        //columns for the WHERE clause
                null,        //The values for the WHERE clause
                null,       //group the rows
                null,       //filter by row groups
                sortOrder); //The sort order
        // Traversing through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Item item = new Item();
                item.setItemID(Integer.parseInt(cursor.getString((cursor.getColumnIndex(COLUMN_ITEM_ID)))));
                item.setItemName(cursor.getString((cursor.getColumnIndex(COLUMN_ITEM_NAME))));
                item.setDescription(cursor.getString((cursor.getColumnIndex(COLUMN_ITEM_DESCRIPTION))));
                item.setOwnerEmail(cursor.getString((cursor.getColumnIndex(COLUMN_ITEM_OWNER))));
                item.setItemImage(cursor.getBlob(cursor.getColumnIndex(COLUMN_ITEM_IMAGE)));


                // Adding user record to list
                itemList.add(item);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        // return user list
        return itemList;
    }

    /**
     * This method is to delete user record
     *
     * @param item
     */
    public void deleteItem(Item item) {
        SQLiteDatabase db = this.getWritableDatabase();
        //delete item record by id
        db.delete(TABLE_ITEM,
                COLUMN_ITEM_NAME + " =? AND "
                        + COLUMN_ITEM_DESCRIPTION + " =? AND "
                        + COLUMN_ITEM_OWNER + " =?", new String[]{item.getItemName(), item.getDescription(), item.getOwnerEmail()});
        db.close();
    }
    public Item getItem(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        Item item = new Item();
        Cursor c = db.rawQuery("SELECT * FROM item WHERE item_id = " + id, null);
        if (c.moveToFirst()) {
            do {
                item.setItemName(c.getString(1));
                item.setOwnerEmail(c.getString(2));
                item.setItemImage(c.getBlob(3));
                item.setDescription(c.getString(4));
            } while(c.moveToNext());
        }
        c.close();
        db.close();
        return item;
    }

    public int getID(Item item){
        int id = -1;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT ID FROM item WHERE item_name = " + item.getItemName()
                + " AND item_owner = " + item.getOwnerEmail()
                + " AND item_description = " + item.getDescription()
                + " AND item_image = " + item.getItemImage()
                ,null);
        if (c.moveToFirst()) {
            do {
                item.setItemName(c.getString(1));
                item.setOwnerEmail(c.getString(2));
                item.setItemImage(c.getBlob(3));
                item.setDescription(c.getString(4));
            } while(c.moveToNext());
        }
        c.close();
        db.close();
        return id;
    }

    //this dont work
    public ArrayList<Item> getUsersItems(String ownerEmail) {
        SQLiteDatabase db = this.getWritableDatabase();
        int i = 0;
        ArrayList<Item> item = new ArrayList<Item>();
        Cursor c = db.rawQuery("SELECT * FROM item WHERE item_owner = \"" + ownerEmail +"\"", null);
        if (c.moveToFirst()) {
            do {
                item.add(new Item());
                item.get(i).setItemName(c.getString(1));
                item.get(i).setOwnerEmail(c.getString(2));
                item.get(i).setItemImage(c.getBlob(3));
                item.get(i).setDescription(c.getString(4));
                i++;
            } while(c.moveToNext());
        }
        c.close();
        db.close();
        return item;
    }
}