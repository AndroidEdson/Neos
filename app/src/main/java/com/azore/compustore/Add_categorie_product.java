package com.azore.compustore;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.azore.compustore.fiuady.db.Inventory;


public class Add_categorie_product extends AppCompatActivity {

    private ImageButton save_categories;
    private ImageButton cancel_categories;
    private EditText editText_id;
    private EditText editText_description;
    private Inventory inventory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_categorie_product);

        save_categories = (ImageButton) findViewById(R.id.save_categories);
        cancel_categories = (ImageButton) findViewById(R.id.cancel_Categories);
        editText_description= (EditText)findViewById(R.id.txt_edit_categories_description);
        editText_id= (EditText)findViewById(R.id.txt_edit_categories_ID);

        inventory= new Inventory(getApplicationContext());


        cancel_categories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlert();
            }
        });



        save_categories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if ( editText_id.getText().toString().equals("") || editText_description.getText().toString().equals("") )
                {
                    Toast.makeText(getApplicationContext(), "¡Error! Campos Vacíos", Toast.LENGTH_SHORT).show();
                }
                else {


                    if( inventory.NameValidation(editText_description.getText().toString()) >= 1 )
                    {
                        Toast.makeText(getApplicationContext(), "Ya existe una categoria con ese nombre", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_SHORT).show();
                        inventory.AddCategory(Integer.parseInt(editText_id.getText().toString()), editText_description.getText().toString());
                        Intent intent_back = new Intent();
                        setResult(RESULT_OK, intent_back);
                        finish();
                    }

                }


            }
        });


    }// END ON CREATE

    public void showAlert( ){
        AlertDialog.Builder myAlert= new AlertDialog.Builder(this);
        myAlert.setMessage("¿Seguro que quieres cancelar?")
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

            }
        })
                .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                   finish();
                    }
                })
                .setTitle("Product Categories")
                .setIcon(R.drawable.ic_shortcut_warning)
                .create();
        myAlert.show();

    }



}//END FINAL
