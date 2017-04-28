package com.azore.compustore;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.azore.compustore.fiuady.db.Inventory;

/**
 * Created by Armín on 16/04/2017.
 */

public class pop_menu_orders_modif_N3 extends Activity {

    //*******************************************************************************
    //*******************************************************************************
    //***********************************Variables************************************

    ImageButton btn_stock;
    ImageButton btn_edit;
    private Inventory inventory;
    ImageButton btn_delete;
    TextView txt_product_name;
    private LinearLayout quitar_stock;
    public static String EXTRA_ENSAMBLE_ID="com.azore.compustore.order.EXTRA_ENSAMBLE_ID" ;
    public static String EXTRA_ENSAMBLE_DESCRIPTION="com.azore.compustore.order.EXTRA_ENSAMBLE_DESCRIPTION" ;
    public static String EXTRA_ORDER_ID="com.azore.compustore.order.EXTRA_ORDER_ID" ;
    public static String EXTRA_ENSAMBLE_QTY="com.azore.compustore.order.EXTRA_ENSAMBLE_QTY" ;



    private String id_order;
    private String ensamble_name;
    private String id_ensamble;
    private String qty_ensamble;

    private  int request_code=0;
    private  int request_code1=1;

    private final String KEY_ID_ORDER= "key_id_order";
    private final String KEY_ENSAMBLE_NAME= "key_ensamble_name";
    private final String KEY_ID_ENSAMBLE= "key_id_ensamble";
    private final String KEY_QTY= "key_qty";




    //*******************************************************************************
    //*******************************************************************************
    //***********************************ON CREATE************************************


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up_products);


        btn_stock= (ImageButton) findViewById(R.id.pop_add_stock_products);
        btn_edit= (ImageButton) findViewById(R.id.pop_edit_product);
        btn_delete= (ImageButton) findViewById(R.id.pop_delete_product);
        txt_product_name= (TextView) findViewById(R.id.textview_products);

        quitar_stock= (LinearLayout)findViewById(R.id.linear_modified_categorie);
        quitar_stock.setVisibility(TextView.GONE);
        inventory = new Inventory(getApplicationContext());



        Intent i = getIntent();
        id_order = i.getStringExtra(EXTRA_ORDER_ID);
        ensamble_name = i.getStringExtra(EXTRA_ENSAMBLE_DESCRIPTION);
        id_ensamble = i.getStringExtra(EXTRA_ENSAMBLE_ID);
        qty_ensamble = i.getStringExtra(EXTRA_ENSAMBLE_QTY);

        if(savedInstanceState!= null)
        {
            id_order= savedInstanceState.getString(KEY_ID_ORDER, "");
            ensamble_name= savedInstanceState.getString(KEY_ENSAMBLE_NAME, "");
            id_ensamble= savedInstanceState.getString(KEY_ID_ENSAMBLE, "");
            qty_ensamble= savedInstanceState.getString(KEY_QTY, "");

        }

        txt_product_name.setText(ensamble_name);



        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               Intent intent = new Intent(getApplicationContext(), modifi_qty_ensamblie_N4.class);
               intent.putExtra(modifi_qty_ensamblie_N4.EXTRA_ORDER_ID, id_order);
                intent.putExtra(modifi_qty_ensamblie_N4.EXTRA_ENSAMBLE_ID, id_ensamble);
                intent.putExtra(modifi_qty_ensamblie_N4.EXTRA_ENSAMBLE_QTY, qty_ensamble);
                startActivityForResult(intent, request_code);
            }
        });

        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inten = new Intent(getApplicationContext(), delete_confirmation.class);
                startActivityForResult(inten, request_code1);
                //  inventory.deleteProductFromEnsambly(delete_confirmation,id_product);
               Intent intent_back = new Intent();

            }
        });

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(KEY_ID_ENSAMBLE,id_ensamble);
        outState.putString(KEY_ENSAMBLE_NAME,ensamble_name);
        outState.putString(KEY_ID_ORDER,id_order);
        outState.putString(KEY_QTY,qty_ensamble);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if ( (requestCode==request_code1 && resultCode== RESULT_OK)){
            inventory.DeleteAssemblyFromOrder(id_order,id_ensamble);
        }

       Intent intent_back = new Intent();
       setResult(RESULT_OK, intent_back);
       //   Toast.makeText(getApplicationContext(),"Productos de ensamble modificado",Toast.LENGTH_SHORT).show();
       finish();

    }


    //*******************************************************************************
    //*******************************************************************************
    //***********************************END ONCREATE************************************

}// END CLASS


