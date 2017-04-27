package com.azore.compustore;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.azore.compustore.fiuady.db.CategoryProduct;
import com.azore.compustore.fiuady.db.Inventory;
import com.azore.compustore.fiuady.db.Products;

import java.util.List;

public class add_stock_products extends Activity {

    Button save_stock;
    Button cancel_stock;
    String id;
    String name;
    String qty;
    Spinner spinner;
    Inventory inventory;

    public static String EXTRA_DESCRIPTION_STOCK = "com.azore.compustore.description.stock";
    public static String EXTRA_ID_STOCK = "com.azore.compustore.id.stock";
    public static String EXTRA_QTY_STOCK = "com.azore.compustore.qty_stock";


    private final String KEY_NAME= "key_name";
    private final String KEY_ID= "key_id";
    private final String KEY_QTY= "key_qty";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_stock_products);

        Intent i = getIntent();
        inventory = new Inventory(getApplicationContext());
        save_stock=(Button)findViewById(R.id.btn_stock_add);
        cancel_stock=(Button)findViewById(R.id.btn_cancel_Stock);
        spinner= (Spinner) findViewById(R.id.spinner_stock);

        id= i.getStringExtra(EXTRA_ID_STOCK);
        name=i.getStringExtra(EXTRA_DESCRIPTION_STOCK);
        qty=i.getStringExtra(EXTRA_QTY_STOCK);

        if(savedInstanceState!= null)
        {
            id= savedInstanceState.getString(KEY_ID, "");
            name= savedInstanceState.getString(KEY_NAME, "");
            qty= savedInstanceState.getString(KEY_QTY, "");

        }

      //  Toast.makeText(getApplicationContext(),id+ name,Toast.LENGTH_SHORT).show();
      final ArrayAdapter<String> spinner_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item);

        final List<Products> products = inventory.QtyProducts(id);
        //Toast.makeText(getApplicationContext(),qty,Toast.LENGTH_SHORT).show();


            spinner_adapter.add(qty);
        spinner.setAdapter(spinner_adapter);

        for (int u=0; u<10; u++)
        {
            spinner_adapter.add(String.valueOf(Integer.valueOf(qty)+u+1));

        }
        spinner.setAdapter(spinner_adapter);


        save_stock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


inventory.updateQuantityProducts(id, String.valueOf(spinner.getSelectedItem().toString()));
                Intent intent_back = new Intent();
                setResult(RESULT_OK, intent_back);
                Toast.makeText(getApplicationContext(),"Stock modificado!",Toast.LENGTH_SHORT).show();
                finish();

            }
        });

        cancel_stock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(KEY_NAME,name);
        outState.putString(KEY_ID,id);
        outState.putString(KEY_QTY,qty);


    }




}
