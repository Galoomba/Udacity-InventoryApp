package com.example.starhood.inventoryapp2.Data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.example.starhood.inventoryapp2.Data.Contracts.ItemEntry.*;
import static com.example.starhood.inventoryapp2.Data.Contracts.ItemEntry.COLUMN_ITEM_IMG;
import static com.example.starhood.inventoryapp2.Data.Contracts.ItemEntry.COLUMN_ITEM_NAME;
import static com.example.starhood.inventoryapp2.Data.Contracts.ItemEntry.COLUMN_ITEM_PRICE;
import static com.example.starhood.inventoryapp2.Data.Contracts.ItemEntry.COLUMN_ITEM_QUANTITY;
import static com.example.starhood.inventoryapp2.Data.Contracts.ItemEntry.COLUMN_ITEM_SUPPLIER;
import static com.example.starhood.inventoryapp2.Data.Contracts.ItemEntry.COLUMN_ITEM_SUPPLIER_EMAIL;
import static com.example.starhood.inventoryapp2.Data.Contracts.ItemEntry.TABLE_NAME;
import static com.example.starhood.inventoryapp2.Data.Contracts.ItemEntry._ID;

/**
 * Created by Starhood on 7/12/17.
 */

public class DataBaseHelper extends SQLiteOpenHelper {


    public static final int DATABASE_VERSION=1;
    public static final String DATABASE_NAME="inventory.db";

    public DataBaseHelper(Context context)
    {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_TABLE="CREATE TABLE "+ TABLE_NAME+" ("+
                _ID+" INTEGER PRIMARY KEY,"+
                COLUMN_ITEM_NAME+" TEXT,"+
                COLUMN_ITEM_SUPPLIER +" TEXT,"+
                COLUMN_ITEM_SUPPLIER_EMAIL+" TEXT,"+
                COLUMN_ITEM_PRICE+" INTEGER,"+
                COLUMN_ITEM_QUANTITY+" INTEGER ,"+
                COLUMN_ITEM_IMG+" BLOB);";
        db.execSQL(CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
