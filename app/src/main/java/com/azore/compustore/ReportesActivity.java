package com.azore.compustore;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

public class ReportesActivity extends AppCompatActivity {


// ********************************************************************
// ********************************************************************
// ********************** VARIABLES ************************************

    private ImageButton btn_missing_products;
    private ImageButton btn_simulation;
    private ImageButton btn_sales;


// ********************************************************************
// ********************************************************************
// **********************ON CREATE ************************************

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reportes);
        //Cambiar texto de App bar
        getSupportActionBar().setTitle("Reportes");

        btn_missing_products= (ImageButton)findViewById(R.id.btn_missing_product);
        btn_simulation= (ImageButton)findViewById(R.id.btn_simulador);
        btn_sales= (ImageButton)findViewById(R.id.btn_more_sales);


        btn_missing_products.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),Reportes_Missing_Products.class);
                startActivity(i);


            }
        });

        btn_simulation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),reportes_simulador.class);
                startActivity(i);


            }
        });

        btn_sales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getApplicationContext(),Month_sales_main.class);
                startActivity(i);


            }
        });




// appbar_layout--  android:theme="@style/ThemeOverlay.AppCompat.Dark.ActionBar"

    }

// ********************************************************************
// ********************************************************************
// **********************END ON CREATE ************************************


}// END CLASS
