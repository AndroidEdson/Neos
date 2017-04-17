package com.azore.compustore;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.azore.compustore.fiuady.db.Inventory;
import com.azore.compustore.fiuady.db.OrdenesUnion;
import com.azore.compustore.fiuady.db.Order_Status;

public class Pop_Up_ordenes_N1 extends Activity {

    private LinearLayout linear_next;
    private LinearLayout linear_back;
    private LinearLayout linear_modif;
    private ImageButton btn_next;
    private ImageButton btn_back;
    private ImageButton btn_modif;
    private TextView txt_orden;
    private TextView txt_next;
    private TextView txt_back;
    private Inventory inventory;

    public static String EXTRA_ORDER_ID="com.azore.compustore.order.id" ;
    private String id_order;
    private int id_status;
    private OrdenesUnion ordenesUnion_aux;
    private Order_Status order_status_aux;

    private int requestcode=0;
    private int requestcode1=1;
    private int requestcode2=2;


    //***********************************************************¨***********************************
    //***********************************************************¨***********************************
    //***************************************ON CREATE********************¨***********************************


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop__up_ordenes_n1);


        linear_next = (LinearLayout) findViewById(R.id.linear_next_status);
        linear_back = (LinearLayout) findViewById(R.id.linear_back_status);
        linear_modif = (LinearLayout) findViewById(R.id.lineas_modificar_orden);
        btn_next = (ImageButton) findViewById(R.id.btn_next_status);
        btn_back = (ImageButton) findViewById(R.id.btn_back_status);
        btn_modif = (ImageButton) findViewById(R.id.btn_modificar_orden);
        txt_orden = (TextView) findViewById(R.id.textview_name_pop);
        txt_next = (TextView) findViewById(R.id.txt_next_status);
        txt_back = (TextView) findViewById(R.id.txt_back_status);

        inventory = new Inventory(getApplicationContext());

        Intent i = getIntent();

        id_order = i.getStringExtra(EXTRA_ORDER_ID);

        ordenesUnion_aux = inventory.getOneOrder(id_order);
        order_status_aux = inventory.getOneOrderStatus(String.valueOf(ordenesUnion_aux.getId_status()));

        id_status= ordenesUnion_aux.getId_status();
        txt_orden.setText("Orden: " + ordenesUnion_aux.getId() + ", " + ordenesUnion_aux.getLast_name() + " " + ordenesUnion_aux.getFirst_name());



        if (order_status_aux.getEditable() == 0)
        {
           linear_modif.setVisibility(TextView.GONE);
        }

        switch (order_status_aux.getId()){
            case 0:
                txt_next.setText("Confirmar");
                txt_back.setText("Cancelar");
                break;
            case 1:
                linear_next.setVisibility(View.GONE);
                txt_back.setText("Regresar orden a pendiente");
                break;
            case 2:
                linear_back.setVisibility(View.GONE);
                txt_next.setText("Poner en tránsito");

                break;
            case 3:
                linear_back.setVisibility(View.GONE);
                txt_next.setText("Finalizar orden ");
                break;
            case 4:
                linear_back.setVisibility(View.GONE);
                linear_next.setVisibility(View.GONE);

                break;
        }


       // inventory.updateStatusOrderId("0", "0");
       // inventory.updateStatusOrderId("1", "0");


        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), confirmation_status_order_N2.class);
                intent.putExtra(confirmation_status_order_N2.EXTRA_ORDER_ID,id_order);
                startActivityForResult(intent, requestcode);

            }
        });



        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), confirmation_status_order_N2.class);
                intent.putExtra(confirmation_status_order_N2.EXTRA_ORDER_ID,id_order);
                startActivityForResult(intent, requestcode1);

            }
        });

        btn_modif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Modif_Ordenes_N2.class);
                intent.putExtra(Modif_Ordenes_N2.EXTRA_ORDER_ID,id_order);
                startActivityForResult(intent, requestcode2);

            }
        });


    }

    //***************************************END ONCREATE*******************************************************
    //**********************************************************************************************



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if ( (requestCode==requestcode && resultCode== RESULT_OK)) {

            String change_state= order_status_aux.getNext();

            if (order_status_aux.getId()==0){
                change_state = Character.toString(change_state.charAt(2));
              //  Toast.makeText(getApplicationContext(), a_letter , Toast.LENGTH_LONG).show();

            }

            inventory.updateStatusOrderId(id_order, change_state);

        }
        if ( (requestCode==requestcode1 && resultCode== RESULT_OK)) {

           // Toast.makeText(getApplicationContext(), "result Return", Toast.LENGTH_LONG).show();

            String change_state= order_status_aux.getPrevious();

            if (order_status_aux.getId()==0){
                 change_state= order_status_aux.getNext();
                change_state  = Character.toString(change_state.charAt(0));
            }
            // Toast.makeText(getApplicationContext(), a_letter , Toast.LENGTH_LONG).show();

            inventory.updateStatusOrderId(id_order, change_state);
     ;
        }

        Intent intent_back = new Intent();
        setResult(RESULT_OK, intent_back);
        finish();

    }
}// END CLASS
