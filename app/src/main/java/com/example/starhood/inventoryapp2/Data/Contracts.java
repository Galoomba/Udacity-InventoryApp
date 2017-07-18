package com.example.starhood.inventoryapp2.Data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Starhood on 7/11/17.
 */

public class Contracts {


    public static final class ContentUri {

        public static final String CONTENT_AUTHORITY = "com.example.starhood.inventoryapp2";
        public static final Uri BASE_CONTENT = Uri.parse("content://" + CONTENT_AUTHORITY);
        public static final String PATH_ITEMS = "items";
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT, PATH_ITEMS);

        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_ITEMS;

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" +PATH_ITEMS;
    }



    public static final class ItemEntry implements BaseColumns {
        public static final String TABLE_NAME = "items";

        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_ITEM_NAME = "name";
        public static final String COLUMN_ITEM_PRICE = "price";
        public static final String COLUMN_ITEM_QUANTITY = "quantity";
        public static final String COLUMN_ITEM_SUPPLIER = "supplier";
        public static final String COLUMN_ITEM_SUPPLIER_EMAIL = "supplierEmail";
        public static final String COLUMN_ITEM_IMG = "img";
    }
}
