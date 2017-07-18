package com.example.starhood.inventoryapp2;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.starhood.inventoryapp2.Data.Contracts;
import com.example.starhood.inventoryapp2.Data.DataBaseHelper;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import static com.example.starhood.inventoryapp2.Data.Contracts.ItemEntry.COLUMN_ITEM_IMG;
import static com.example.starhood.inventoryapp2.Data.Contracts.ItemEntry.COLUMN_ITEM_NAME;
import static com.example.starhood.inventoryapp2.Data.Contracts.ItemEntry.COLUMN_ITEM_PRICE;
import static com.example.starhood.inventoryapp2.Data.Contracts.ItemEntry.COLUMN_ITEM_QUANTITY;
import static com.example.starhood.inventoryapp2.Data.Contracts.ItemEntry.COLUMN_ITEM_SUPPLIER;
import static com.example.starhood.inventoryapp2.Data.Contracts.ItemEntry.COLUMN_ITEM_SUPPLIER_EMAIL;

public class InsertItem extends AppCompatActivity {
    final int REQUEST_CODE_GALLERY=999;
    Button btn,chooseBtn;
    ImageView img;
    EditText name,price,quantity,supplier,supplierEmail;
    DataBaseHelper dh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_item);

        dh=new DataBaseHelper(this);
        img=(ImageView) findViewById(R.id.img);
        btn=(Button) findViewById(R.id.btn);
        chooseBtn=(Button) findViewById(R.id.choosebtn);
        name=(EditText) findViewById(R.id.name);
        price=(EditText) findViewById(R.id.price);
        quantity=(EditText) findViewById(R.id.quantity);
        supplier=(EditText) findViewById(R.id.supplier);
        supplierEmail=(EditText) findViewById(R.id.supplieremail);




        chooseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions
                        (InsertItem.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}
                                ,REQUEST_CODE_GALLERY);
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                byte [] imgEntry = imageToByte(img);
                insertData(imgEntry);

                //Intent intent=new Intent(InsertItem.this,MainActivity.class);
                InsertItem.this.finish();
                //startActivity(intent);

            }

            private byte[] imageToByte(ImageView img)
            {
                Bitmap bitmap=((BitmapDrawable)img.getDrawable()).getBitmap();
                ByteArrayOutputStream stream=new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG,100,stream);
                byte [] buffer =stream.toByteArray();
                return  buffer;
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if(requestCode==REQUEST_CODE_GALLERY){
            if(grantResults.length>=0&&grantResults[0]== PackageManager.PERMISSION_GRANTED)
            {
                Intent intent=new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,REQUEST_CODE_GALLERY);
            }
            else {
                Toast.makeText(getApplicationContext(),"PERMISSION FAILD",Toast.LENGTH_SHORT);
            }
            return;
        }


        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Uri uri=data.getData();
        try{
            InputStream inputStream=getContentResolver().openInputStream(uri);
            Bitmap bitmap= BitmapFactory.decodeStream(inputStream);
            img.setImageBitmap(bitmap);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private void insertData(byte [] imgData) {

        SQLiteDatabase db=dh.getWritableDatabase();

        ContentValues values =new ContentValues();
        values.put(COLUMN_ITEM_NAME,name.getText().toString().trim());
        values.put(COLUMN_ITEM_PRICE,Integer.parseInt(price.getText().toString().trim()));
        values.put(COLUMN_ITEM_QUANTITY,Integer.parseInt(quantity.getText().toString().trim()));
        values.put(COLUMN_ITEM_IMG,imgData);
        values.put(COLUMN_ITEM_SUPPLIER,supplier.getText().toString().trim());
        values.put(COLUMN_ITEM_SUPPLIER_EMAIL,supplierEmail.getText().toString().trim());


        Uri uri=getContentResolver().insert(Contracts.ContentUri.CONTENT_URI,values);

        if(uri==null)
            Log.v("InsertItems",""+"FAILED");


    }
}
