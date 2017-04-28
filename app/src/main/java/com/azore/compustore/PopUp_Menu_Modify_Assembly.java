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

public class PopUp_Menu_Modify_Assembly extends Activity {


    //*****************************************************************************
    //**********************Variables *******************************************************

    ImageButton btn_stock;
    ImageButton btn_edit;
    private Inventory inventory;
    ImageButton btn_delete;
    TextView txt_product_name;

    private String id_product;
    private String name_product;
    private String qty_product;

    private String id_ensamble;
    private String name_ensamble;

    private  LinearLayout quitar_stock;
    int request_code4=4;


    public static String EXTRA_QTY_PRODUCT = "com.azore.compustore.id.add.assemblies.description_product.qty";
    public static String EXTRA_DESCRIPTION_PRODUCT = "com.azore.compustore.id.add.assemblies.description_product";
    public static String EXTRA_ID_PRODUCT = "com.azore.compustore.id.add.assemblies.id_product";

    public static String EXTRA_DESCRIPTION_ENSAMBLE = "com.azore.compustore.id.add.assemblies.description_ensamble";
    public static String EXTRA_ID_ENSAMBLE = "com.azore.compustore.id.add.assemblies.id_ensamble";


    private int request_code1=0;
    //*****************************************************************************
    //*****************************ON CREATE ************************************************
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up_products);



        Intent i = getIntent();

        id_product= i.getStringExtra( EXTRA_ID_PRODUCT);
        name_product=i.getStringExtra(EXTRA_DESCRIPTION_PRODUCT);
        qty_product=i.getStringExtra(EXTRA_QTY_PRODUCT);


        id_ensamble= i.getStringExtra(EXTRA_ID_ENSAMBLE);
        name_ensamble=i.getStringExtra(EXTRA_DESCRIPTION_ENSAMBLE);

        btn_stock= (ImageButton) findViewById(R.id.pop_add_stock_products);
        btn_edit= (ImageButton) findViewById(R.id.pop_edit_product);
        btn_delete= (ImageButton) findViewById(R.id.pop_delete_product);
        txt_product_name= (TextView) findViewById(R.id.textview_products);
        quitar_stock= (LinearLayout)findViewById(R.id.linear_modified_categorie);

        inventory = new Inventory(getApplicationContext());
        txt_product_name.setText(name_product);
        quitar_stock.setVisibility(TextView.GONE);


        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), PopUp_modif_ensamble.class);
                intent.putExtra(PopUp_modif_ensamble.EXTRA_ID_PRODUCT, id_product);
                intent.putExtra(PopUp_modif_ensamble.EXTRA_ID_ASSEMBLY,id_ensamble);
                 intent.putExtra(PopUp_modif_ensamble.EXTRA_QTY_ASSEMBLY, qty_product);
                startActivityForResult(intent, request_code1);
            }
        });

        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), delete_confirmation.class);
                // intent.putExtra(PopUp_products.EXTRA_DESCRIPTION, product.getDescription());
                startActivityForResult(intent, request_code4);


            //   inventory.deleteProductFromEnsambly(id_ensamble,id_product);
            //   Intent intent_back = new Intent();
            //   setResult(RESULT_OK, intent_back);
            //   finish();
            }
        });





    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if ( (requestCode==request_code4 && resultCode== RESULT_OK))
        {
            inventory.deleteProductFromEnsambly(id_ensamble,id_product);
            Toast.makeText(getApplicationContext(),"Eliminado", Toast.LENGTH_SHORT).show();

        }

        Intent intent_back = new Intent();
        setResult(RESULT_OK, intent_back);
     //   Toast.makeText(getApplicationContext(),"Productos de ensamble modificado",Toast.LENGTH_SHORT).show();
        finish();
    }

    //**********************************END ONCREATE*******************************************
    //*****************************************************************************
    //*****************************************************************************


}
