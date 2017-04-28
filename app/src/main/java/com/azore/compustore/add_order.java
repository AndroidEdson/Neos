package com.azore.compustore;


import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.azore.compustore.fiuady.db.AssemblieOrders_Union;
import com.azore.compustore.fiuady.db.Customers;
import com.azore.compustore.fiuady.db.Inventory;
import com.azore.compustore.fiuady.db.InventoryDbSchema;



import java.util.Date;
import java.util.List;


public class add_order extends AppCompatActivity {
    private class AssembliesHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView txtDescription;
        private TextView txtCosto;
        private TextView txtQty;


        Context context;
        private List<AssemblieOrders_Union> assemblieOrders_unions;

        public AssembliesHolder(View itemView, Context context, List<AssemblieOrders_Union> assemblieOrders_unions){

            super(itemView);
            this.context= context;
            this.assemblieOrders_unions= assemblieOrders_unions;
            itemView.setOnClickListener(this);
            txtDescription= (TextView) itemView.findViewById(R.id.txt_assemblie_description);
            txtCosto= (TextView) itemView.findViewById(R.id.txt_price_emsamblie_order);
            txtQty= (TextView) itemView.findViewById(R.id.txt_qty_emsamblie_order);


        }

        public void bindCategories(AssemblieOrders_Union assemblieOrders_union){
            txtDescription.setText(assemblieOrders_union.getDescription());
            txtCosto.setText(Double.toString(assemblieOrders_union.getPrice()/100));
            txtQty.setText(String.valueOf(assemblieOrders_union.getQty()));

        }


        @Override
        public void onClick(View v) {
            int position = getAdapterPosition() ;
            AssemblieOrders_Union assemblieOrders_union=this.assemblieOrders_unions.get(position);

            Intent intent = new Intent(getApplicationContext(), pop_menu_orders_modif_N3.class);
            intent.putExtra(pop_menu_orders_modif_N3.EXTRA_ENSAMBLE_ID,String.valueOf(assemblieOrders_union.getAssembly_id()));
            intent.putExtra(pop_menu_orders_modif_N3.EXTRA_ENSAMBLE_DESCRIPTION,assemblieOrders_union.getDescription());
            intent.putExtra(pop_menu_orders_modif_N3.EXTRA_ENSAMBLE_QTY,String.valueOf(assemblieOrders_union.getQty()));
            intent.putExtra(pop_menu_orders_modif_N3.EXTRA_ORDER_ID,String.valueOf(lastorderid));
            startActivityForResult(intent, request_code);
        }

    }


    private  class AssembliesOrdersUnionAdapter extends  RecyclerView.Adapter<add_order.AssembliesHolder>{
        private List<AssemblieOrders_Union> assemblies;
        Context context;

        public AssembliesOrdersUnionAdapter(List<AssemblieOrders_Union> assemblies, Context context){
            this.assemblies=assemblies;
            this.context=context;
        }


        @Override
        public add_order.AssembliesHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = getLayoutInflater().inflate(R.layout.orders_assemblies_union,parent,false);
            return new add_order.AssembliesHolder(view,context,assemblies);
        }

        @Override
        public void onBindViewHolder(add_order.AssembliesHolder holder, int position) {
            holder.bindCategories(assemblies.get(position));
        }

        @Override
        public int getItemCount() {
            return assemblies.size();
        }
    }




    //**************************************************************************************
    //**************************************************************************************
    //************************************ONCREATE*********************************************
    Spinner add_order_spinner;
    private Inventory inventory;
    private RecyclerView recyclerView;
    private AssembliesOrdersUnionAdapter adapter;
    private Button btn_save ;
    private Button btn_cancel ;
    private int request_code;
    public  List<Customers> clientes ;
    public int lastorderid;
    public boolean btn_save_pressed = false;
    int posicionSpinner=0;
    int customerid=-1;

    private final String KEY_NAME= "key_name";
    private final String KEY_ID= "key_id";
    private final String KEY_ID_SPINNER= "key_flag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_order);

        //Cambiar texto de App bar
        getSupportActionBar().setTitle("Nueva Orden");
        inventory = new Inventory(getApplicationContext());
        lastorderid=inventory.getLastId(InventoryDbSchema.Orders_Table.NAME)+1;

        if(savedInstanceState!= null)
        {
            lastorderid= savedInstanceState.getInt(KEY_ID, 0);
            customerid= savedInstanceState.getInt(KEY_ID_SPINNER, 0);
         //   original_name= savedInstanceState.getString(KEY_NAME, "");

        }

        //spinner start
        add_order_spinner  = (Spinner)findViewById(R.id.add_order_spinner);
        final ArrayAdapter<String> spinner_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item);
        clientes = inventory.customers_alfabetic();

        for (Customers cliente : clientes) {
            spinner_adapter.add(cliente.getFirst_name() + " " + cliente.getLast_name() );
        }
        add_order_spinner.setAdapter(spinner_adapter);
        //spinner end

        //recycler view start
        recyclerView= (RecyclerView) findViewById(R.id.recycler_orders_assemblies);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        btn_save= (Button) findViewById(R.id.btnGuardar);
        btn_cancel= (Button) findViewById(R.id.btnCancelar);


        if(customerid!=-1){
            add_order_spinner.setEnabled(false);
            add_order_spinner.setSelection(customerid);
        }

        final List<AssemblieOrders_Union> assemblieOrders_unions = inventory.getEnsambliesInOrder(String.valueOf(lastorderid));
        adapter = new add_order.AssembliesOrdersUnionAdapter(assemblieOrders_unions, this);
        recyclerView.setAdapter(adapter);

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                btn_save_pressed = true;
                final List<AssemblieOrders_Union> assemblieOrders_unions = inventory.getEnsambliesInOrder(String.valueOf(lastorderid));
            if( assemblieOrders_unions.size()==0 ) {
                Toast.makeText(getApplicationContext(), "Orden Vacía, Agregue un ensamble !", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(getApplicationContext(), "Agregado Exitosamente", Toast.LENGTH_SHORT).show();
                Intent intent_back = new Intent();
                setResult(RESULT_OK, intent_back);
                finish();
            }

            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                inventory.deleteOrders(String.valueOf(lastorderid));
                finish();
            }
        });



    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
       // outState.putString(KEY_NAME,lastorderid);
        outState.putInt(KEY_ID,lastorderid);
        outState.putInt(KEY_ID_SPINNER,customerid);


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        inventory.deleteOrders(String.valueOf(lastorderid));
    }



    //Hace que aparezca el icono en el App Bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    //Cuando se selecciona algo del item bar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.agregar:
                // Codigo prueba
                Calendar c = Calendar.getInstance();
                SimpleDateFormat sd1 = new SimpleDateFormat("yyyy-MM-dd");
                String date =  sd1.format(new Date(c.getTimeInMillis()));
                 customerid= clientes.get(add_order_spinner.getSelectedItemPosition()).getId();
                inventory.AddOrder(lastorderid,customerid,date);
                Intent intent = new Intent(getApplicationContext(), Add_Assembly_to_Order.class);
                startActivityForResult(intent, request_code);

        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        final List<AssemblieOrders_Union> assemblieOrders_unions = inventory.getEnsambliesInOrder(String.valueOf(lastorderid));
        adapter = new add_order.AssembliesOrdersUnionAdapter(assemblieOrders_unions, this);
        recyclerView.setAdapter(adapter);
        if(assemblieOrders_unions.size() > 0) {
            add_order_spinner.setEnabled(false);
        }
        else{add_order_spinner.setEnabled(true);}

    }
}
