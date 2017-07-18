package com.example.starhood.inventoryapp2;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.starhood.inventoryapp2.Data.Contracts;

import static com.example.starhood.inventoryapp2.Data.Contracts.ItemEntry.*;

/**
 * Created by Starhood on 7/12/17.
 */

public class ItemCursorAdapter extends CursorAdapter {

    int quantity;
    int price;
    int id;
    String name;
    String supplier;
    String supplierEmail;
    byte [] img;
    Context context;
    ViewHolder viewHolder;

    public ItemCursorAdapter(Context context, Cursor c) {
        super(context, c, 0);
        this.context=context;
    }


    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {

        return LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
    }

    @Override
    public void bindView(View view, Context c,final  Cursor cursor) {


            viewHolder = new ViewHolder();
            viewHolder.itemName = (TextView) view.findViewById(R.id.name);
            viewHolder.itemPrice = (TextView) view.findViewById(R.id.price);
            viewHolder.itemQuantity = (TextView) view.findViewById(R.id.quantity);
            viewHolder.itemSupplier = (TextView) view.findViewById(R.id.supplier);
            viewHolder.itemImg = (ImageView) view.findViewById(R.id.img);
            viewHolder.sellBtn = (Button) view.findViewById(R.id.btn);



                // Extract properties from curso
            id = cursor.getInt(cursor.getColumnIndex(_ID));
            name = cursor.getString(cursor.getColumnIndex(COLUMN_ITEM_NAME));
            String nameString ="Item Name : "+name;
            supplier = cursor.getString(cursor.getColumnIndex(COLUMN_ITEM_SUPPLIER));
            String supplierString = "Supplier : " + supplier;
            supplierEmail = cursor.getString(cursor.getColumnIndex(COLUMN_ITEM_SUPPLIER_EMAIL));
            price = cursor.getInt(cursor.getColumnIndex(COLUMN_ITEM_PRICE));
            String priceString = "Price = " + String.valueOf(price) + "$";
            quantity = cursor.getInt(cursor.getColumnIndex(COLUMN_ITEM_QUANTITY));
            String quantityString = "Quantity = " + String.valueOf(quantity);
            img =cursor.getBlob(cursor.getColumnIndex(COLUMN_ITEM_IMG));

            Bitmap bitmap = BitmapFactory.decodeByteArray(img, 0, img.length);

        viewHolder.sellBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int quantity = cursor.getInt(cursor.getColumnIndex(COLUMN_ITEM_QUANTITY));
                if (quantity > 0) {

                   // Log.v("" + id, "" + quantity);
                   // int id = cursor.getInt(cursor.getColumnIndex(_ID));
                    updateQuantity(id);
                } else {
                    Toast.makeText(context, "No item available!!", Toast.LENGTH_SHORT).show();
                }
            }
        });


            // Populate fields with extracted properties
            viewHolder.itemName.setText(nameString);
            viewHolder.itemPrice.setText(priceString);
            viewHolder.itemQuantity.setText(quantityString);
            viewHolder.itemSupplier.setText(supplierString);
            viewHolder.itemImg.setImageBitmap(bitmap);

    }

    private void updateQuantity(int id){
        ContentValues values = new ContentValues();
        values.put(COLUMN_ITEM_NAME, name);
        values.put(COLUMN_ITEM_SUPPLIER, supplier);
        values.put(COLUMN_ITEM_PRICE, price);
        values.put(COLUMN_ITEM_QUANTITY, quantity);
        values.put(COLUMN_ITEM_IMG, img);
        values.put(COLUMN_ITEM_SUPPLIER_EMAIL, supplierEmail);

        Uri contentUri= ContentUris.withAppendedId(Contracts.ContentUri.CONTENT_URI,id);


        int rowsAffected = context.getContentResolver().update(contentUri, values, null, null);
       //Log.v(""+id,""+rowsAffected);
        if (rowsAffected>0)
            Toast.makeText(context,"Sold!!",Toast.LENGTH_SHORT).show();

    }


    protected class ViewHolder{
        TextView itemName ;
        TextView itemPrice ;
        TextView itemQuantity ;
        TextView itemSupplier ;
        ImageView itemImg ;
        Button sellBtn;
    }


}
