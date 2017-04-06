package com.azore.compustore;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.azore.compustore.fiuady.db.Inventory;

/**
 * Created by Armín on 05/04/2017.
 */

public class PopUp_modif_ensamble extends Activity {

    public static String EXTRA_QTY_ASSEMBLY = "com.azore.compustore.id.add.assemblies.id.qty";
    public static String EXTRA_ID_PRODUCT = "com.azore.compustore.id.add.assemblies.id.product";
    public static String EXTRA_ID_ASSEMBLY = "com.azore.compustore.id.add.assemblies.id.assembly";

    //************************************************************************************
    //************************************************************************************
    //*********************************Variables ************************************************

    Button save_stock;
    Button cancel_stock;
    String id_product;
    String id_assembly;
    String name;
    String qty;
    Spinner spinner;
    Inventory inventory;
    TextView txt_add_product;
    TextView txt_tag_stock;

    //************************************************************************************
    //************************************************************************************
    //************************************ON CREATE ************************************************


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_stock_products);


        Intent i = getIntent();
        inventory = new Inventory(getApplicationContext());
        save_stock=(Button)findViewById(R.id.btn_stock_add);
        cancel_stock=(Button)findViewById(R.id.btn_cancel_Stock);
        spinner= (Spinner) findViewById(R.id.spinner_stock);
        txt_add_product = (TextView) findViewById( R.id.textview_products);
        txt_tag_stock = (TextView) findViewById( R.id.textView_tag_stock);


        txt_add_product.setText("Agregar a ensamble");
        txt_tag_stock.setText("           Cantidad:   ");


        id_assembly=i.getStringExtra(EXTRA_ID_ASSEMBLY);
        id_product=i.getStringExtra(EXTRA_ID_PRODUCT);
        qty=i.getStringExtra(EXTRA_QTY_ASSEMBLY);

        //qty=i.getStringExtra(EXTRA_QTY_ADD_ASSEMBLIES_STOCK);


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
                inventory.updateAssemblyProduct(id_assembly, id_product, new_qty);
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
    //************************************END ON CREATE ************************************************


    }//END CLASSS
