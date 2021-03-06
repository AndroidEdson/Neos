package com.azore.compustore;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.azore.compustore.fiuady.db.CategoryProduct;
import com.azore.compustore.fiuady.db.Inventory;
import com.azore.compustore.fiuady.db.InventoryDbSchema;

import java.util.List;


public class PopUp_products extends Activity {

    ImageButton btn_stock;
    ImageButton btn_edit;
    private Inventory inventory;
    ImageButton btn_delete;
    TextView txt_product_name;

    public static String EXTRA_DESCRIPTION = "com.azore.compustore.description";
    public static String EXTRA_ID = "com.azore.compustore.id";
    public static String EXTRA_QTY = "com.azore.compustore.qty";
    public static String EXTRA_PRICE = "com.azore.compustore.price";
    public static String EXTRA_CATEG = "com.azore.compustore.categ";



    private String id;
    private String name;
    private String qty;
    private String price;
    private String id_categorie;

    private  AlertDialog dialogShow ;
    int  request_code2=1;
    int  request_code3=2;
    private int aux=0;
    private LinearLayout delete_layout;

    private final String KEY_NAME= "key_name";
    private final String KEY_ID= "key_id";
    private final String KEY_QTY= "key_qty";
    private final String KEY_PRICE= "key_price";
    private final String KEY_CATEG= "key_categ";






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_pop_up_products);

        Intent i =  getIntent();

        btn_stock= (ImageButton) findViewById(R.id.pop_add_stock_products);
        btn_edit= (ImageButton) findViewById(R.id.pop_edit_product);
        btn_delete= (ImageButton) findViewById(R.id.pop_delete_product);
        txt_product_name= (TextView) findViewById(R.id.textview_products);

        id= i.getStringExtra(EXTRA_ID);
        name=i.getStringExtra(EXTRA_DESCRIPTION);
        qty=i.getStringExtra(EXTRA_QTY);
        price=i.getStringExtra(EXTRA_PRICE);
        id_categorie=i.getStringExtra(EXTRA_CATEG);




        if(savedInstanceState!= null)
        {
            id= savedInstanceState.getString(KEY_ID, "");
            name= savedInstanceState.getString(KEY_NAME, "");
            qty= savedInstanceState.getString(KEY_QTY, "");
            price= savedInstanceState.getString(KEY_PRICE, "");
            id_categorie= savedInstanceState.getString(KEY_CATEG, "");

        }

        txt_product_name.setText(name);

        inventory= new Inventory(getApplicationContext());

        delete_layout= (LinearLayout)findViewById(R.id.delete_layout_categories);
        // PARA CLASIFICAR CUALES TENDRAN OPCION ELIMINAR
        aux = inventory.ExistAssemblyWhitProduct(id);
        //Toast.makeText(getApplicationContext(), Integer.toString(aux),Toast.LENGTH_SHORT).show();

        if( aux >= 1 )
        {
            delete_layout.setVisibility(View.GONE);
        }
        else {
            delete_layout.setVisibility(View.VISIBLE);
        }

    btn_stock.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

        Intent intent = new Intent(getApplicationContext(), add_stock_products.class);
        intent.putExtra(add_stock_products.EXTRA_DESCRIPTION_STOCK, name);
        intent.putExtra(add_stock_products.EXTRA_ID_STOCK, id);
        intent.putExtra(add_stock_products.EXTRA_QTY_STOCK, qty);
        startActivityForResult(intent, request_code2);


    }


});

        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), delete_confirmation.class);
                startActivityForResult(intent, request_code3);

            }
        });

     btn_edit.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             Intent intent = new Intent(getApplicationContext(), modify_products.class);
             intent.putExtra(modify_products.EXTRA_ID_STOCK, id);
             intent.putExtra(modify_products.EXTRA_DESCRIPTION, name);
             intent.putExtra(modify_products.EXTRA_PRICE, price);
             intent.putExtra(modify_products.EXTRA_CATEG, id_categorie);

             startActivityForResult(intent, request_code2);
         }
     });





}// END ONCREATE

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if ( (requestCode==request_code2 && resultCode== RESULT_OK))
        {

            //  Toast.makeText(getApplicationContext(), " OK :(", Toast.LENGTH_SHORT).show();
            Intent intent_back = new Intent();
            setResult(RESULT_OK, intent_back);
            finish();


        }

        if ( (requestCode==request_code3 && resultCode== RESULT_OK))
        {

            inventory.deleteCategory(InventoryDbSchema.Products_Table.NAME, id);
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
        outState.putString(KEY_QTY,qty);
        outState.putString(KEY_PRICE,price);
        outState.putString(KEY_CATEG,id_categorie);


    }


}//END CLASS
