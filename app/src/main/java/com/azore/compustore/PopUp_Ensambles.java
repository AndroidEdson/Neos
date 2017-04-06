package com.azore.compustore;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.azore.compustore.fiuady.db.Inventory;

/**
 * Created by Armín on 05/04/2017.
 */

public class PopUp_Ensambles extends Activity {


    //***************************************************************************************
    //***************************************************************************************
    //*************************************VARIABLES ****************************************

    ImageButton btn_stock;
    ImageButton btn_edit;
    private Inventory inventory;
    ImageButton btn_delete;
    TextView txt_product_name;

    private String id;
    private String name;

    private  LinearLayout quitar_stock;

    public static String EXTRA_DESCRIPTION_ENSAMBLE = "com.azore.compustore.id.add.assemblies.description_ensamble";
    public static String EXTRA_ID_ENSAMBLE = "com.azore.compustore.id.add.assemblies.id_ensamble";


    private int request_code1=0;
    //***************************************************************************************
    //***************************************************************************************
    //***************************************ON CREATE************************************************



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up_products);

        Intent i = getIntent();

        id= i.getStringExtra(EXTRA_ID_ENSAMBLE);
        name=i.getStringExtra(EXTRA_DESCRIPTION_ENSAMBLE);

        btn_stock= (ImageButton) findViewById(R.id.pop_add_stock_products);
        btn_edit= (ImageButton) findViewById(R.id.pop_edit_product);
        btn_delete= (ImageButton) findViewById(R.id.pop_delete_product);
        txt_product_name= (TextView) findViewById(R.id.textview_products);
        quitar_stock= (LinearLayout)findViewById(R.id.linear_modified_categorie);

        inventory = new Inventory(getApplicationContext());
        txt_product_name.setText(name);
        quitar_stock.setVisibility(TextView.GONE);


        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), modif_ensamble.class);
                intent.putExtra(modif_ensamble.EXTRA_DESCRIPTION_ENSAMBLE_MODIF, name);
                intent.putExtra(modif_ensamble.EXTRA_ID_ENSAMBLE_MODIF,id);
               // intent.putExtra(PopUp_products.EXTRA_QTY, Integer.toString(product.getQty()));
                startActivityForResult(intent, request_code1);
            }
        });



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Intent intent_back = new Intent();
        setResult(RESULT_OK, intent_back);
        finish();

    }

    //***************************************************************************************
    //***************************************************************************************
    //***************************************END CREATE************************************************




}
