package com.azore.compustore;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageButton;
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
    private TextView name_categorie;
    private String id;
    private String name;
    public static final String EXTRA_ID = "com.azore.compustore.extra_id";
    public static final String EXTRA_DESCRIPTION = "com.azore.compustore.extra_description";


    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.pop_up_category);

        Intent i = getIntent();

        id= i.getStringExtra(EXTRA_ID);
        name=i.getStringExtra(EXTRA_DESCRIPTION);
        if(savedInstanceState!= null)
        {
            id= savedInstanceState.getString(EXTRA_ID, "");
            name= savedInstanceState.getString(EXTRA_DESCRIPTION, "");
        }


         edit_popup= (ImageButton)findViewById(R.id.popup_image_categori_edit);
        delete_popup= (ImageButton)findViewById(R.id.popup_image_categori_delete);
        name_categorie= (TextView)findViewById(R.id.textview_categories) ;

        inventory= new Inventory(getApplicationContext());
        Toast.makeText(getApplicationContext(),name, Toast.LENGTH_SHORT).show();

        DisplayMetrics dm =new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);


        int width= dm.widthPixels;
        int height= dm.heightPixels;
        getWindow().setLayout((int)(width*.8) ,(int)(height*.4));
        name_categorie.setText(name);



        edit_popup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"modifica", Toast.LENGTH_SHORT).show();

            }
        });


        delete_popup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                inventory.deleteCategory(InventoryDbSchema.Categories_Table.NAME, id);
                Toast.makeText(getApplicationContext(),"Eliminado", Toast.LENGTH_SHORT).show();
                Intent intent_back = new Intent();
                setResult(RESULT_OK, intent_back);
                finish();

                //  AlertDialog.Builder myAlert= new AlertDialog.Builder(getApplicationContext());
              //  myAlert.setMessage("¿Seguro que desea Eliminar?")
              //          .setNegativeButton("No", new DialogInterface.OnClickListener() {
              //              @Override
              //              public void onClick(DialogInterface dialog, int which) {
              //                  dialog.dismiss();
//
              //              }
              //          })
              //          .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
              //              @Override
              //              public void onClick(DialogInterface dialog, int which) {
              //                  inventory.deleteCategory(InventoryDbSchema.Categories_Table.NAME, id);
              //                  Toast.makeText(getApplicationContext(),"Eliminado", Toast.LENGTH_SHORT).show();
              //                  Intent intent_back = new Intent();
              //                  setResult(RESULT_OK, intent_back);
              //                  finish();
              //              }
              //          })
              //          .setTitle("Product Categories")
              //          .setIcon(R.drawable.ic_shortcut_warning)
              //          .create();
              //  myAlert.show();
//
            }
        });

    }


}
