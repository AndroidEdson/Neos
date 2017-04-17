package com.azore.compustore;

import android.app.Activity;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.azore.compustore.fiuady.db.Inventory;

public class confirmation_status_order_N2 extends Activity {

    private Inventory inventory;
    private Button btn_save;
    private Button btn_cancel;
    public static String EXTRA_ORDER_ID="com.azore.compustore.order.id" ;
    private  String id_order;
    private EditText editText_changelog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation_status_order_n2);

        btn_save=(Button)findViewById(R.id.btn_save_changes);
        btn_cancel=(Button)findViewById(R.id.btn_cancel_confirmation);
        editText_changelog=(EditText) findViewById(R.id.txt_changelog);


        inventory = new Inventory(getApplicationContext());

        Intent i = getIntent();

        id_order = i.getStringExtra(EXTRA_ORDER_ID);



        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(editText_changelog.getText().toString().equals("")){

                    Toast.makeText(getApplicationContext(), "Ingrese una razón del cambio", Toast.LENGTH_LONG).show();


                }else {


                    final Calendar c = Calendar.getInstance();
                    int year = c.get(Calendar.YEAR);
                    int month = c.get(Calendar.MONTH);
                    int dayOfMonth = c.get(Calendar.DAY_OF_MONTH);
                    String monat = String.valueOf(month + 1);
                    String tag = String.valueOf(dayOfMonth);
                    if (String.valueOf(month + 1).length() == 1) {
                        monat = "0" + String.valueOf(month + 1);
                    }
                    if (String.valueOf(dayOfMonth).length() == 1) {
                        tag = "0" + String.valueOf(dayOfMonth);
                    }

                    String date_current = String.valueOf(year) + "-" + monat + "-" + tag;


                    inventory.AddChangeLog(id_order, date_current+ ": "+ editText_changelog.getText().toString());
                    Intent intent_back = new Intent();
                    setResult(RESULT_OK, intent_back);
                    finish();
                }
            }
        });


        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });



    } // END ON CREATE
}
