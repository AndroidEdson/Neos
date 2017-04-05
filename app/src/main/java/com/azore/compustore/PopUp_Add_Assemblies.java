package com.azore.compustore;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.azore.compustore.fiuady.db.Inventory;

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

    private String       id;
    private String       name;
    private String       qty;

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

        txt_product_name.setText(name);
        modif_layout.setVisibility(TextView.GONE);


    } //FINAL ON CREATE



    //*******************************************************************************
    //*******************************************************************************
    //***********************************END ONCREATE********************************************


} // FINAL CLASS
