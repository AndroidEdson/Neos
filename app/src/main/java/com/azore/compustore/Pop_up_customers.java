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
import android.widget.Toast;

import com.azore.compustore.fiuady.db.Inventory;
import com.azore.compustore.fiuady.db.InventoryDbSchema;

public class Pop_up_customers extends Activity{
    ImageButton btn_edit;
    private Inventory inventory;
    ImageButton btn_delete;
    TextView txt_customer_name;
    int  request_code=1;
    int  request_code2=2;

    public static String EXTRA_Customer_ID = "com.azore.compustore.customerid";
    public static String EXTRA_First_Name = "com.azore.compustore.customername";
    public static String EXTRA_Last_Name = "com.azore.compustore.customerlastname";
    public static String EXTRA_Address = "com.azore.compustore.customeraddres";
    public static String EXTRA_Phone1 = "com.azore.compustore.customerPhone1";
    public static String EXTRA_Phone2 = "com.azore.compustore.customerPhone2";
    public static String EXTRA_Phone3 = "com.azore.compustore.customerPhone3";
    public static String EXTRA_Email = "com.azore.compustore.customerEmail";



    private String id;
    private String name;
    private String lastname;
    private String phone1;
    private String phone2;
    private String phone3;
    private String address;
    private String email;
    private int aux=0;
    private LinearLayout delete_layout;
    private LinearLayout layout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.pop_up_category);

        Intent i =  getIntent();

        btn_edit= (ImageButton)findViewById(R.id.popup_image_categori_edit);
        btn_delete= (ImageButton)findViewById(R.id.popup_image_categori_delete);
        txt_customer_name= (TextView)findViewById(R.id.textview_categories) ;
        btn_edit= (ImageButton)findViewById(R.id.popup_image_categori_edit);
        btn_delete= (ImageButton)findViewById(R.id.popup_image_categori_delete);


        id= i.getStringExtra(EXTRA_Customer_ID);
        name=i.getStringExtra(EXTRA_First_Name);
        lastname=i.getStringExtra(EXTRA_Last_Name);
        phone1=i.getStringExtra(EXTRA_Phone1);
        phone2=i.getStringExtra(EXTRA_Phone2);
        phone3=i.getStringExtra(EXTRA_Phone3);
        address=i.getStringExtra(EXTRA_Address);
        email=i.getStringExtra(EXTRA_Email);



        if(savedInstanceState!= null)
        {
            id= savedInstanceState.getString(EXTRA_Customer_ID, "");
            name= savedInstanceState.getString(EXTRA_Customer_ID, "");
        }

        txt_customer_name.setText(name);



        layout = (LinearLayout)findViewById(R.id.linear_modified_categorie);
        layout.setVisibility(View.GONE);

        inventory= new Inventory(getApplicationContext());

        delete_layout= (LinearLayout)findViewById(R.id.delete_layout_categories);
        // PARA CLASIFICAR CUALES TENDRAN OPCION ELIMINAR
        aux = inventory.ExistCustomersWhitOrders(id);
        //Toast.makeText(getApplicationContext(), Integer.toString(aux),Toast.LENGTH_SHORT).show();

        if( aux >= 1 )
        {
            delete_layout.setVisibility(View.GONE);



        }
        else {
            delete_layout.setVisibility(View.VISIBLE);
        }




        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), delete_confirmation.class);
                startActivityForResult(intent, request_code2);

            }
        });

        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), modify_customers.class);
                intent.putExtra(modify_customers.EXTRA_ID, id);
                intent.putExtra(modify_customers.EXTRA_First_Name, name);
                intent.putExtra(modify_customers.EXTRA_Last_Name, lastname);
                intent.putExtra(modify_customers.EXTRA_Address, address);
                intent.putExtra(modify_customers.EXTRA_Phone1, phone1);
                intent.putExtra(modify_customers.EXTRA_Phone2, phone2);
                intent.putExtra(modify_customers.EXTRA_Phone3, phone3);
                intent.putExtra(modify_customers.EXTRA_Email, email);
                startActivityForResult(intent, request_code);
            }
        });





    }// END ONCREATE

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if ( (requestCode==request_code && resultCode== RESULT_OK))
        {

            //  Toast.makeText(getApplicationContext(), " OK :(", Toast.LENGTH_SHORT).show();
            Intent intent_back = new Intent();
            setResult(RESULT_OK, intent_back);
            finish();


        }

        if ( (requestCode==request_code2 && resultCode== RESULT_OK))
        {

            inventory.deleteCustomer(InventoryDbSchema.Customers_Table.NAME, id);
            Toast.makeText(getApplicationContext(),"Eliminado", Toast.LENGTH_SHORT).show();
            Intent intent_back = new Intent();
            setResult(RESULT_OK, intent_back);
            finish();


        }
    }
}
