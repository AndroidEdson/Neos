package com.azore.compustore;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.azore.compustore.fiuady.db.CategoryProduct;
import com.azore.compustore.fiuady.db.Inventory;
import com.azore.compustore.fiuady.db.Products;

import java.util.List;

/**
 * Created by Azore on 03/04/2017.
 */

public class modify_products extends Activity {



    //public static String EXTRA_ID_STOCK = "com.azore.compustore.id.stock";


    private Inventory inventory;
    String id;

    public static String EXTRA_ID_STOCK = "com.azore.compustore.id-product";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.products_add);
        Intent i = getIntent();
        id= i.getStringExtra(EXTRA_ID_STOCK);

        id= i.getStringExtra(EXTRA_ID_STOCK);

        final AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
        final EditText mDescription = (EditText)findViewById(R.id.etDescription);
        final EditText mPrecio = (EditText)findViewById(R.id.etprecio);
        final Spinner spinner = (Spinner)findViewById(R.id.SpinnerCategory);
        Button mGuardar = (Button)findViewById(R.id.btnGuardar);
        Button mCancelar = (Button)findViewById(R.id.btnCancelar);
        final ArrayAdapter<String> spinner_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item);
        inventory = new Inventory(getApplicationContext());

        // equivalente a for each
        final List<CategoryProduct> categoriesProduct = inventory.getAllCategoriesProduct();
        for (CategoryProduct category : categoriesProduct) {
            spinner_adapter.add(category.getDescription());
        }


        spinner.setAdapter(spinner_adapter);




        mGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (  mDescription.getText().toString().equals("")|| mPrecio.getText().toString().equals("") )
                {
                    Toast.makeText(getApplicationContext(), "¡Error! Campo Vacío", Toast.LENGTH_SHORT).show();
                }
                else {


                    if( inventory.NameValidationProducts(mDescription.getText().toString()) >= 1 )
                    {
                        Toast.makeText(getApplicationContext(), "Ya existe un producto con ese nombre", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        inventory.updateProducts(id,categoriesProduct.get(spinner.getSelectedItemPosition()).getId(),mDescription.getText().toString(),(Double.parseDouble(mPrecio.getText().toString()))*100);
                        Intent intent_back = new Intent();
                        setResult(RESULT_OK, intent_back);
                        Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_SHORT).show();
                        finish();

                    }
                }
            }
        });

        mCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Toast.makeText(getApplicationContext(), "Cancelado", Toast.LENGTH_SHORT).show();
            }
        });




    }


}
