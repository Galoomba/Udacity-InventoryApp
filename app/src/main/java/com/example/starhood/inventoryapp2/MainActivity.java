package com.example.starhood.inventoryapp2;


import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.starhood.inventoryapp2.Data.Contracts;
import com.example.starhood.inventoryapp2.Data.DataBaseHelper;

import static com.example.starhood.inventoryapp2.Data.Contracts.ItemEntry.*;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{
    int CURSOR_LOADER_ID=0;
    ItemCursorAdapter mAdapter;
    DataBaseHelper dh;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        dh=new DataBaseHelper(this);

        mAdapter=new ItemCursorAdapter(this,null);
        ListView list = (ListView) findViewById(R.id.list);
        list.setAdapter(mAdapter);
        list.setFocusable(true);
        Button button =(Button)findViewById(R.id.insert);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //insertData();
                Intent intent=new Intent(MainActivity.this,InsertItem.class);
                startActivity(intent);
            }
        });



        getSupportLoaderManager().initLoader(CURSOR_LOADER_ID, null, this);
    }

    private void insertData() {

        SQLiteDatabase db=dh.getWritableDatabase();

        ContentValues values =new ContentValues();
        values.put(COLUMN_ITEM_NAME,"tareek");
        values.put(COLUMN_ITEM_PRICE,20);
        values.put(COLUMN_ITEM_QUANTITY,5);
        values.put(COLUMN_ITEM_SUPPLIER,"a7mad");
        values.put(COLUMN_ITEM_SUPPLIER_EMAIL,"a7mad@sa7s");


        Uri uri=getContentResolver().insert(Contracts.ContentUri.CONTENT_URI,values);

        if(uri==null)
            Log.v("main",""+"FAILED");

    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        //String []Projection ={_ID,COLUMN_ITEM_NAME,COLUMN_ITEM_PRICE,COLUMN_ITEM_IMG,COLUMN_ITEM_QUANTITY,COLUMN_ITEM_SUPPLIER};

        return new CursorLoader(
                this,
                Contracts.ContentUri.CONTENT_URI,
                null,
                null,
                null,
                null
        );
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mAdapter.swapCursor(null);
    }
}
