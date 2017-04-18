package com.azore.compustore;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.azore.compustore.fiuady.db.Inventory;
import com.azore.compustore.fiuady.db.InventoryDbSchema;

/**
 * Created by Azore on 17/04/2017.
 */

public class PopUp_add_Ensamble_Order extends Activity {
    ImageButton btn_assembly;
    private Inventory inventory;
    TextView txt_product_name;
    TextView txt_tag;



    public static String EXTRA_DESCRIPTION_ENSAMBLE = "com.azore.compustore.description.assembly_";
    public static String EXTRA_ENSAMBLE_ID="com.azore.compustore.order.EXTRA_ENSAMBLE_ID" ;






    private String id_customer;
    private String id_order;
    private String id_assembly;
    private String name_product;


    private int aux=0;
    private LinearLayout delete_layout;
    private LinearLayout modif_layout;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //  requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_pop_up_products);

        Intent i = getIntent();
        name_product= i.getStringExtra(EXTRA_DESCRIPTION_ENSAMBLE);
        id_assembly = i.getStringExtra(EXTRA_ENSAMBLE_ID);
        inventory= new Inventory(getApplicationContext());
        id_order= String.valueOf(inventory.getLastId(InventoryDbSchema.Orders_Table.NAME));
        btn_assembly = (ImageButton) findViewById(R.id.pop_add_stock_products);
        txt_product_name = (TextView) findViewById(R.id.textview_products);
        txt_tag = (TextView) findViewById(R.id.txt_tag_stock);
        txt_product_name.setText(name_product);
        txt_tag.setText("Agregar");
        delete_layout= (LinearLayout)findViewById(R.id.delete_layout_categories);
        modif_layout= (LinearLayout)findViewById(R.id.pop_up_modif);
        delete_layout.setVisibility(TextView.GONE);
        modif_layout.setVisibility(TextView.GONE);



        btn_assembly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                int flag=0;
                flag= inventory.getNumberOfEnsamblesOnActualOrder(id_order, id_assembly);


                if (flag >=1)
                {
                    int new_qty = 0;
                    new_qty=  Integer.valueOf(flag) +1;
                    //  Toast.makeText(getApplicationContext(),"id_pro: "+id_product+ " id_ens:  "+ id_assembly+" qty: "+ String.valueOf(new_qty),Toast.LENGTH_LONG).show();
                    inventory.updateQtyAssemblyFromOrder(id_order, id_assembly, new_qty);

                }else
                {
                    inventory.AddOrderAssembly(id_order, id_assembly);
                    //     Toast.makeText(getApplicationContext(), id_ensamble + " ENTRE " + id_product, Toast.LENGTH_LONG).show();

                }


                //Intent intent_back = new Intent();
                //intent_back.setData(Uri.parse(id_product));
                setResult(RESULT_OK);
                Toast.makeText(getApplicationContext(), "Ensamble Agregado  ", Toast.LENGTH_SHORT).show();
                finish();

            }
        });



    }
}
