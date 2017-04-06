package com.azore.compustore;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

import android.widget.Toast;


import com.azore.compustore.fiuady.db.Inventory;

import java.util.List;

/**
 * Created by Azore on 05/04/2017.
 */

public class modify_customers extends Activity {


    //public static String EXTRA_ID_STOCK = "com.azore.compustore.id.stock";


    private Inventory inventory;
    String id;

    public static String EXTRA_ID = "com.azore.compustore.id";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_add);
        Intent i = getIntent();
        id= i.getStringExtra(EXTRA_ID);
        inventory = new Inventory(getApplicationContext());


        final EditText mId = (EditText)findViewById(R.id.customer_id);
        final EditText mNombre = (EditText)findViewById(R.id.customer_first_name);
        final EditText mApellido = (EditText)findViewById(R.id.customer_last_name);
        final EditText mDireccion = (EditText)findViewById(R.id.customer_address);
        final EditText mTel1 = (EditText)findViewById(R.id.customer_phone1);
        final EditText mTel2 = (EditText)findViewById(R.id.customer_phone2);
        final EditText mTel3 = (EditText)findViewById(R.id.customer_phone3);
        final EditText mEmail = (EditText)findViewById(R.id.customer_email);
        Button mGuardar = (Button) findViewById(R.id.customer_save);
        Button mCancelar = (Button) findViewById(R.id.customer_cancel);
        mId.setVisibility(View.GONE);




        mGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if ( mNombre.getText().toString().equals("") || mApellido.getText().toString().equals("")|| mDireccion.getText().toString().equals("") )
                {
                    Toast.makeText(getApplicationContext(), "¡Error! Campo Vacío", Toast.LENGTH_SHORT).show();
                }
                else {

                    Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_SHORT).show();
                    inventory.updateCustomer(id,mNombre.getText().toString(),mApellido.getText().toString(),mDireccion.getText().toString(),mTel1.getText().toString(),mTel2.getText().toString(),mTel3.getText().toString(),mEmail.getText().toString());
                    Intent intent_back = new Intent();
                    setResult(RESULT_OK, intent_back);
                    finish();
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
