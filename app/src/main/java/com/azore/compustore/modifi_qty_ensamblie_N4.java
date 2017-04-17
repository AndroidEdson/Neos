package com.azore.compustore;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.azore.compustore.fiuady.db.Inventory;

/**
 * Created by Armín on 16/04/2017.
 */

public class modifi_qty_ensamblie_N4 extends Activity {

    public static String EXTRA_ORDER_ID="com.azore.compustore.order.EXTRA_ORDER_ID" ;
    public static String EXTRA_ENSAMBLE_ID="com.azore.compustore.order.EXTRA_ENSAMBLE_ID" ;
    public static String EXTRA_ENSAMBLE_QTY="com.azore.compustore.order.EXTRA_ENSAMBLE_QTY" ;


    //************************************************************************************
    //************************************************************************************
    //*********************************Variables ************************************************

    Button save_stock;
    Button cancel_stock;
    String id_order;
    String id_assembly;
    String name;
    String qty;
    Spinner spinner;
    Inventory inventory;
    TextView txt_add_product;
    TextView txt_tag_stock;


    //************************************************************************************
    //************************************************************************************
    //************************************ON CREATE ***************************************


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_stock_products);

        inventory = new Inventory(getApplicationContext());
        save_stock=(Button)findViewById(R.id.btn_stock_add);
        cancel_stock=(Button)findViewById(R.id.btn_cancel_Stock);
        spinner= (Spinner) findViewById(R.id.spinner_stock);
        txt_add_product = (TextView) findViewById( R.id.textview_products);
        txt_tag_stock = (TextView) findViewById( R.id.textView_tag_stock);

        Intent i = getIntent();
        id_order = i.getStringExtra(EXTRA_ORDER_ID);
        id_assembly = i.getStringExtra(EXTRA_ENSAMBLE_ID);
        qty = i.getStringExtra(EXTRA_ENSAMBLE_QTY);


        final ArrayAdapter<String> spinner_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item);

        for (int u=0; u<15; u++)
        {
            spinner_adapter.add(String.valueOf(u+1));

        }
        spinner.setAdapter(spinner_adapter);



        save_stock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int new_qty = 0;
                new_qty=  Integer.valueOf(qty) +Integer.valueOf(spinner.getSelectedItem().toString());
                //  Toast.makeText(getApplicationContext(),"id_pro: "+id_product+ " id_ens:  "+ id_assembly+" qty: "+ String.valueOf(new_qty),Toast.LENGTH_LONG).show();
                inventory.updateQtyAssemblyFromOrder(id_order, id_assembly, new_qty);
                Intent intent_back = new Intent();
                setResult(RESULT_OK, intent_back);
                // Toast.makeText(getApplicationContext(),"Productos de ensamble modificado",Toast.LENGTH_SHORT).show();
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
    //************************************************************************************
    //************************************************************************************
    //************************************ON CREATE **************************************

} //END CLASS
