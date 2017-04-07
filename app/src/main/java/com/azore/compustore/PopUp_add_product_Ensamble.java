package com.azore.compustore;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.azore.compustore.fiuady.db.Inventory;

/**
 * Created by Armín on 06/04/2017.
 */

public class PopUp_add_product_Ensamble extends Activity {

    ImageButton btn_stock;
    ImageButton btn_edit;
    private Inventory inventory;
    ImageButton btn_delete;
    TextView txt_product_name;
    TextView txt_tag;


    public static String EXTRA_DESCRIPTION_ASSEMBLY = "com.azore.compustore.description.assembly";
    public static String EXTRA_ID_PRODUCT_ASSEMBLY = "com.azore.compustore.id.product.assembly";
    public static String EXTRA_ID_ASSEMBLY = "com.azore.compustore.id.assem";




    private String id_ensamble;
    private String name_ensamble;
    private String id_product;
    private String qty;
    private AlertDialog dialogShow ;
    int  request_code2=1;
    int  request_code3=2;
    private int aux=0;
    private LinearLayout delete_layout;
    private LinearLayout modif_layout;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_pop_up_products);

        Intent i = getIntent();

        btn_stock = (ImageButton) findViewById(R.id.pop_add_stock_products);
        btn_edit = (ImageButton) findViewById(R.id.pop_edit_product);
        btn_delete = (ImageButton) findViewById(R.id.pop_delete_product);
        txt_product_name = (TextView) findViewById(R.id.textview_products);
        txt_tag = (TextView) findViewById(R.id.txt_tag_stock);
        txt_tag.setText("Agregar");


        delete_layout= (LinearLayout)findViewById(R.id.delete_layout_categories);
        modif_layout= (LinearLayout)findViewById(R.id.pop_up_modif);

        delete_layout.setVisibility(TextView.GONE);
        modif_layout.setVisibility(TextView.GONE);


        id_ensamble = i.getStringExtra(EXTRA_ID_ASSEMBLY);
        id_product = i.getStringExtra(EXTRA_ID_PRODUCT_ASSEMBLY);
        name_ensamble = i.getStringExtra(EXTRA_DESCRIPTION_ASSEMBLY);
        inventory = new Inventory(getApplicationContext());
      //  qty = i.getStringExtra(EXTRA_QTY);
        //txt_product_name.setText(name);

        btn_stock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            //    inventory.ad

            }
        });


    }

}
