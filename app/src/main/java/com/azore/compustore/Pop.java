package com.azore.compustore;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IntegerRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.azore.compustore.fiuady.db.Inventory;
import com.azore.compustore.fiuady.db.InventoryDbSchema;

/**
 * Created by Armín on 29/03/2017.
 */

public class Pop extends Activity{

    private ImageButton edit_popup;
    private Inventory inventory;
    private ImageButton delete_popup;
    private ImageButton save_button;
    private TextView name_categorie;
    private String id;
    private String name;
    public static final String EXTRA_ID = "com.azore.compustore.extra_id";
    public static final String EXTRA_DESCRIPTION = "com.azore.compustore.extra_description";
    private LinearLayout modified_layout;
    private LinearLayout delete_layout;
    private final String KEY_NAME= "key_name";
    private final String KEY_ID= "key_id";



    private EditText new_name_category;
    private int aux=0;
    int  request_code=1;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.pop_up_category);


        Intent i = getIntent();

        id= i.getStringExtra(EXTRA_ID);
        name=i.getStringExtra(EXTRA_DESCRIPTION);

        if(savedInstanceState!= null)
        {
            id= savedInstanceState.getString(KEY_ID, "");
            name= savedInstanceState.getString(KEY_NAME, "");
        }


         edit_popup= (ImageButton)findViewById(R.id.popup_image_categori_edit);
        delete_popup= (ImageButton)findViewById(R.id.popup_image_categori_delete);
        save_button= (ImageButton)findViewById(R.id.save_modified_categories);
        name_categorie= (TextView)findViewById(R.id.textview_categories) ;

        modified_layout= (LinearLayout)findViewById(R.id.linear_modified_categorie);
        delete_layout= (LinearLayout)findViewById(R.id.delete_layout_categories);

        new_name_category= (EditText)findViewById(R.id.new_modified_categories);

        inventory= new Inventory(getApplicationContext());
     //   Toast.makeText(getApplicationContext(),id, Toast.LENGTH_SHORT).show();



        // PARA CLASIFICAR CUALES TENDRAN OPCION ELIMINAR
         aux = inventory.ExistProductWhitCategory(id);
        //Toast.makeText(getApplicationContext(), Integer.toString(aux),Toast.LENGTH_SHORT).show();

        if( aux >= 1 )
        {
            delete_layout.setVisibility(View.GONE);
            modified_layout.setVisibility(View.GONE);
            name_categorie.setText(name);


        }
        else {
            delete_layout.setVisibility(View.VISIBLE);
            modified_layout.setVisibility(View.GONE);
            name_categorie.setText(name);
        }



        edit_popup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (modified_layout.getVisibility()== View.VISIBLE) {
                    new_name_category.setText(name);
                    save_button.setEnabled(false);
                    modified_layout.setVisibility(View.GONE);

                }else {
                    new_name_category.setText(name);
                    save_button.setEnabled(true);
                    modified_layout.setVisibility(View.VISIBLE);

                }


             //   String name_modified="";



            }
        });

        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // SI LA CADENA ESTA VACIA
                if (new_name_category.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "Cuadro de texto vacío", Toast.LENGTH_SHORT).show();
                }
                else { //SI LA CADENA CONTIENE ALGO

                    if (new_name_category.getText().toString().equals(name)) {
                        Intent intent_back = new Intent();
                        setResult(RESULT_OK, intent_back);
                        finish();

                    } else {
                        // VERIFICAR QUE NO HAYA COINCIDENCIA
                        if (inventory.NameValidation(new_name_category.getText().toString().toUpperCase()) >= 1) {
                            Toast.makeText(getApplicationContext(), "Ya existe una categoria con este nombre", Toast.LENGTH_SHORT).show();
                        } else {
                            inventory.updateCategory(id, new_name_category.getText().toString());
                            Toast.makeText(getApplicationContext(), "Categoria modificada", Toast.LENGTH_SHORT).show();
                            Intent intent_back = new Intent();
                            setResult(RESULT_OK, intent_back);
                            finish();
                        }

                    }
                }

            }
        });


        delete_popup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), delete_confirmation.class);
                startActivityForResult(intent, request_code);

//


            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if ( (requestCode==request_code && resultCode== RESULT_OK))
        {

            inventory.deleteCategory(InventoryDbSchema.Categories_Table.NAME, id);
            Toast.makeText(getApplicationContext(),"Eliminado", Toast.LENGTH_SHORT).show();
            Intent intent_back = new Intent();
            setResult(RESULT_OK, intent_back);
            finish();


        }
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(KEY_NAME,name);
        outState.putString(KEY_ID,id);

    }
}
