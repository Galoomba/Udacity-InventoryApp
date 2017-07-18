package com.example.starhood.inventoryapp2.Data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import static com.example.starhood.inventoryapp2.Data.Contracts.ContentUri.*;
import static com.example.starhood.inventoryapp2.Data.Contracts.ItemEntry.*;


/**
 * Created by Starhood on 7/12/17.
 */

public class ItemsProvider extends ContentProvider {

    private static final int ITEMS = 100;
    private static final int ITEMS_ID = 101;
    DataBaseHelper db;

    private static final UriMatcher MATCHER = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        MATCHER.addURI(CONTENT_AUTHORITY, PATH_ITEMS, ITEMS);
        MATCHER.addURI(CONTENT_AUTHORITY, PATH_ITEMS + "/#", ITEMS_ID);
    }




    @Override
    public boolean onCreate() {
        db = new DataBaseHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteDatabase db = this.db.getReadableDatabase();
        Cursor cursor;

        int match = MATCHER.match(uri);
        switch (match) {
            case ITEMS:
                cursor = db.query(TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;

            case ITEMS_ID:
                selection = _ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                cursor = db.query(TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;

            default:
                throw new IllegalArgumentException("Cannot Query " + uri);

        }

        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
    }


    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        final int match = MATCHER.match(uri);

        switch (match) {
            case ITEMS:
                return insertItem(uri, values);
            default:
                throw new IllegalArgumentException("Cannot Query " + uri);


        }
    }

    private Uri insertItem(Uri uri, ContentValues values) {
        if (values.containsKey(COLUMN_ITEM_NAME)) {
            String name = values.getAsString(COLUMN_ITEM_NAME);
            if (name == null || TextUtils.isEmpty(name)) {
                throw new IllegalArgumentException("item requires a name");
            }
        }
        if (values.containsKey(COLUMN_ITEM_PRICE)) {
            String price = values.getAsString(COLUMN_ITEM_PRICE);
            if (price == null || TextUtils.isEmpty(price)) {
                throw new IllegalArgumentException("item requires a name");
            }
        }
        if (values.containsKey(COLUMN_ITEM_QUANTITY)) {
            String quantity = values.getAsString(COLUMN_ITEM_QUANTITY);
            if (quantity == null || TextUtils.isEmpty(quantity)) {
                throw new IllegalArgumentException("item requires a quantity");
            }
        }
        if (values.containsKey(COLUMN_ITEM_SUPPLIER)) {
            String supplier = values.getAsString(COLUMN_ITEM_SUPPLIER);
            if (supplier == null || TextUtils.isEmpty(supplier)) {
                throw new IllegalArgumentException("item requires a supplier");
            }
        }
        if (values.containsKey(COLUMN_ITEM_SUPPLIER_EMAIL)) {
            String supplierEmail = values.getAsString(COLUMN_ITEM_SUPPLIER_EMAIL);
            if (supplierEmail == null || TextUtils.isEmpty(supplierEmail)) {
                throw new IllegalArgumentException("item requires a supplierEmail");
            }
        }
        if (values.containsKey(COLUMN_ITEM_IMG)) {
            String img = values.getAsString(COLUMN_ITEM_IMG);
            if (img == null || TextUtils.isEmpty(img)) {
                throw new IllegalArgumentException("item requires a img");
            }
        }

        if (values.size() == 0) {
            return uri;
        }

        SQLiteDatabase db = this.db.getWritableDatabase();

        long rawID = db.insert(TABLE_NAME, null, values);

        if (rawID == -1) {
            Log.e("INSERT_PROVIDER", "FAILD TO INSERT ROE FOR " + uri);
            return null;
        }


        getContext().getContentResolver().notifyChange(uri, null);
        return ContentUris.withAppendedId(CONTENT_URI, rawID);
    }


    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {

        SQLiteDatabase database = db.getWritableDatabase();

        final int match = MATCHER.match(uri);

        // Track the number of rows that were deleted
        int rowsDeleted;

        switch (match) {
            case ITEMS:
                // Delete all rows that match the selection and selection args
                rowsDeleted = database.delete(TABLE_NAME, selection, selectionArgs);
                break;

            case ITEMS_ID:
                // Delete a single row given by the ID in the URI
                selection = _ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                // Delete a single row given by the ID in the URI
                rowsDeleted = database.delete(TABLE_NAME, selection, selectionArgs);
                break;

            default:
                throw new IllegalArgumentException("Deletion is not supported for " + uri);
        }
        if (rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        // Return the number of rows deleted
        return rowsDeleted;
    }


    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        final int match = MATCHER.match(uri);
        switch (match) {
            case ITEMS:
                return updateItems(uri, values, selection, selectionArgs);
            case ITEMS_ID:

                selection = _ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                return updateItems(uri, values, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("Update is not supported for " + uri);
        }
    }

    private int updateItems(Uri uri, ContentValues values, String selection, String[] selectionArgs) {

        if (values.containsKey(COLUMN_ITEM_NAME)) {
            String name = values.getAsString(COLUMN_ITEM_NAME);
            if (name == null || TextUtils.isEmpty(name)) {
                throw new IllegalArgumentException("item requires a name");
            }
        }
        if (values.containsKey(COLUMN_ITEM_PRICE)) {
            String price = values.getAsString(COLUMN_ITEM_PRICE);
            if (price == null || TextUtils.isEmpty(price)) {
                throw new IllegalArgumentException("item requires a name");
            }
        }
        if (values.containsKey(COLUMN_ITEM_QUANTITY)) {
            String quantity = values.getAsString(COLUMN_ITEM_QUANTITY);
            if (quantity == null || TextUtils.isEmpty(quantity)) {
                throw new IllegalArgumentException("item requires a quantity");
            }
        }
        if (values.containsKey(COLUMN_ITEM_SUPPLIER)) {
            String supplier = values.getAsString(COLUMN_ITEM_SUPPLIER);
            if (supplier == null || TextUtils.isEmpty(supplier)) {
                throw new IllegalArgumentException("item requires a supplier");
            }
        }
        if (values.containsKey(COLUMN_ITEM_SUPPLIER_EMAIL)) {
            String supplierEmail = values.getAsString(COLUMN_ITEM_SUPPLIER_EMAIL);
            if (supplierEmail == null || TextUtils.isEmpty(supplierEmail)) {
                throw new IllegalArgumentException("item requires a supplierEmail");
            }
        }
       if (values.containsKey(COLUMN_ITEM_IMG)) {
            String img = values.getAsString(COLUMN_ITEM_IMG);
            if (img == null || TextUtils.isEmpty(img)) {
                throw new IllegalArgumentException("item requires a img");
            }
        }



        if (values.size() == 0) {
            return 0;
        }

        SQLiteDatabase database = db.getWritableDatabase();


        int rowsUpdated = database.update(TABLE_NAME, values, selection, selectionArgs);


        if (rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return rowsUpdated;

    }


    @Override
    public String getType(Uri uri) {
        final int match = MATCHER.match(uri);
        switch (match) {
            case ITEMS:
                return CONTENT_LIST_TYPE;
            case ITEMS_ID:
                return CONTENT_ITEM_TYPE;
            default:
                throw new IllegalStateException("Unknown URI " + uri + " with match " + match);
        }
    }


}