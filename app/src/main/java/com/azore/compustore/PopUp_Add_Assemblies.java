package com.azore.compustore;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.azore.compustore.fiuady.db.Inventory;
import com.azore.compustore.fiuady.db.InventoryDbSchema;

/**
 * Created by Armín on 05/04/2017.
 */

public class PopUp_Add_Assemblies extends Activity {

    //*********************************VARIABLES****************************************

    ImageButton btn_stock;
    ImageButton btn_edit;
    private Inventory inventory;
    ImageButton btn_delete;
    TextView txt_product_name;
    LinearLayout modif_layout;
    LinearLayout delete_layout;


    private String       id;
    private String       name;
    private String       qty;
    int request_code3=3;
    int request_code4=4;

    public static String EXTRA_DESCRIPTION_ADD_ASSEMBLIES = "com.azore.compustore.description.add.assemblies";
    public static String EXTRA_ID_ADD_ASSEMBLIES = "com.azore.compustore.id.add.assemblies";
    public static String EXTRA_QTY_ADD_ASSEMBLIES = "com.azore.compustore.add.qty";
    //*******************************************************************************
    //*******************************************************************************
    //***********************************ONCREATE********************************************



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up_products);

        Intent i = getIntent();

        id= i.getStringExtra(EXTRA_ID_ADD_ASSEMBLIES);
        name=i.getStringExtra(EXTRA_DESCRIPTION_ADD_ASSEMBLIES);
        qty=i.getStringExtra(EXTRA_QTY_ADD_ASSEMBLIES);

        btn_stock= (ImageButton) findViewById(R.id.pop_add_stock_products);
        btn_edit= (ImageButton) findViewById(R.id.pop_edit_product);
        btn_delete= (ImageButton) findViewById(R.id.pop_delete_product);
        txt_product_name= (TextView) findViewById(R.id.textview_products);
        modif_layout= (LinearLayout)findViewById(R.id.linear_modified_categorie);
        delete_layout= (LinearLayout)findViewById(R.id.delete_layout_categories);


        txt_product_name.setText(name);
        modif_layout.setVisibility(TextView.GONE);
        inventory = new Inventory(getApplicationContext());


        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                   Intent intent = new Intent(getApplicationContext(), add_stock_from_ensambles.class);
                  // intent.putExtra(PopUp_products.EXTRA_DESCRIPTION, product.getDescription());
                   intent.putExtra(add_stock_from_ensambles.EXTRA_ID_ADD_ASSEMBLIES_STOCK, String.valueOf(id));
                       intent.putExtra(add_stock_from_ensambles.EXTRA_QTY_ADD_ASSEMBLIES_STOCK, String.valueOf(qty));
                 startActivityForResult(intent, request_code3);
            }
        });


        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), delete_confirmation.class);
                // intent.putExtra(PopUp_products.EXTRA_DESCRIPTION, product.getDescription());
                startActivityForResult(intent, request_code4);

            }
        });


    } //FINAL ON CREATE

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if ( (requestCode==request_code3 && resultCode== RESULT_OK) )
        {
            //  Toast.makeText(getApplicationContext(), " OK :(", Toast.LENGTH_SHORT).show();
            Intent intent_back = new Intent();
            setResult(RESULT_OK, intent_back);
            finish();
        }

        if ( (requestCode==request_code4 && resultCode== RESULT_OK))
        {
            int last_id_ensamble = inventory.getLastId(InventoryDbSchema.Assemblies_Table.NAME);
            inventory.deleteProductFromEnsambly(String.valueOf(last_id_ensamble), id);
            Toast.makeText(getApplicationContext(),"Eliminado", Toast.LENGTH_SHORT).show();
            Intent intent_back = new Intent();
            setResult(RESULT_OK, intent_back);
            finish();


        }

    }


//*******************************************************************************
    //*******************************************************************************
    //***********************************END ONCREATE********************************************


} // FINAL CLASS
