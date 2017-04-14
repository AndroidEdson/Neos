package com.azore.compustore;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.azore.compustore.fiuady.db.Assemblies;
import com.azore.compustore.fiuady.db.CategoryProduct;
import com.azore.compustore.fiuady.db.Inventory;
import com.azore.compustore.fiuady.db.OrdenesUnion;
import com.azore.compustore.fiuady.db.Order_Status;
import com.azore.compustore.fiuady.db.Orders;
import com.azore.compustore.fiuady.db.Products;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class OrdenesActivity extends AppCompatActivity implements SearchView.OnQueryTextListener   {

    // ****************************************INICIO DE RECYCLER*****************************************************

    private class OrdenesUnionHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView txtId;
        private TextView txtName;
        private TextView txtStatus;
        private TextView txtCost;
        private TextView txtDate;

        Context context;
        private List<OrdenesUnion> ordenesUnions;

        public OrdenesUnionHolder(View itemView, Context context, List<OrdenesUnion> ordenesUnions){

            super(itemView);
            this.context= context;
            this.ordenesUnions= ordenesUnions;
            itemView.setOnClickListener(this);

            txtId= (TextView) itemView.findViewById(R.id.txt_id_orden);
            txtName= (TextView) itemView.findViewById(R.id.txt_name_customer_orden);
            txtStatus= (TextView) itemView.findViewById(R.id.txt_status_ordenes);
            txtCost= (TextView) itemView.findViewById(R.id.txt_costo_ordenes);
            txtDate= (TextView) itemView.findViewById(R.id.txt_date_ordenes);





        }

        public void bindCategories(OrdenesUnion ordenesUnion){
            txtId.setText(String.valueOf(ordenesUnion.getId()));
            txtName.setText(ordenesUnion.getLast_name() + " " + ordenesUnion.getFirst_name());
            txtStatus.setText(ordenesUnion.getStatus_description());
            txtCost.setText(Double.toString(( ordenesUnion.getCosto()/100)));

           // Double.toString((double) product.getPrice()/100)
         //   Date cDate = new Date();
          //  String fDate = new SimpleDateFormat("yyyy-MM-dd").format(cDate);

            txtDate.setText(ordenesUnion.getDate());


        }


        @Override
        public void onClick(View v) {
            int position = getAdapterPosition() ;
            OrdenesUnion ordenesUnion=this.ordenesUnions.get(position);

//
          //  Intent intent = new Intent(getApplicationContext(), PopUp_Ensambles.class);
          //  intent.putExtra(PopUp_Ensambles.EXTRA_DESCRIPTION_ENSAMBLE, assemblies.getDescription());
          //  intent.putExtra(PopUp_Ensambles.EXTRA_ID_ENSAMBLE, Integer.toString(assemblies.getId()));
          //  //   Toast.makeText(getApplicationContext(), "works ", Toast.LENGTH_SHORT).show();
          //  startActivityForResult(intent, request_code2);


        }

    }


    private  class OrdenesUnionAdapter extends  RecyclerView.Adapter< OrdenesUnionHolder >{
        private List<OrdenesUnion> ordenesUnions;
        Context context;

        public OrdenesUnionAdapter(List<OrdenesUnion> ordenesUnions, Context context){
            this.ordenesUnions=ordenesUnions;
            this.context=context;
        }


        @Override
        public OrdenesUnionHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = getLayoutInflater().inflate(R.layout.ordenesunion_list_item,parent,false);
            return new OrdenesUnionHolder(view, context, ordenesUnions);
        }

        @Override
        public void onBindViewHolder(OrdenesUnionHolder holder, int position) {
            holder.bindCategories(ordenesUnions.get(position));

        }

        @Override
        public int getItemCount() {
            return ordenesUnions.size();
        }
    }

    // ****************************************END  DE RECYCLER*****************************************************


    // **************************************** VARIABLES ****************************************************


    private RecyclerView recyclerView;
    private OrdenesUnionAdapter adapter;
    private Inventory inventory;
    private AlertDialog dialogShow ;
    private final int request_code=0;

    private Spinner categoriesSpinner;
    public int PosicionSpinner;

    // ******************************************************************************************************

    // **************************************** ON CREATE  ****************************************************

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ordenes);
        //Cambiar texto de App bar
        getSupportActionBar().setTitle("Ordenes");

        inventory = new Inventory(getApplicationContext());
        recyclerView = (RecyclerView) findViewById(R.id.recycler_orders);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        categoriesSpinner = (Spinner) findViewById(R.id.spinner_ordenes);
        inventory = new Inventory(getApplicationContext());

        final List<OrdenesUnion> ordenes_union = inventory.getOrdersUnion();
        adapter = new OrdenesUnionAdapter(ordenes_union, this);
        recyclerView.setAdapter(adapter);

        final List<Order_Status> ordes_status_list = inventory.getAllOrderStatus();

        final ArrayAdapter<String> spinner_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item);


       spinner_adapter.add("Todos");
     final List<Order_Status> order_statuses = inventory.getAllOrderStatus();
     for (Order_Status order_status : order_statuses) {
         spinner_adapter.add( order_status.getDescription());
     }
//
        categoriesSpinner.setAdapter(spinner_adapter);



        categoriesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

               //  int j = Integer.valueOf(spinner_adapter.getItem(position));
                //Toast.makeText(getApplicationContext(), categoriesProduct.get(position).getDescription(), Toast.LENGTH_SHORT).show();

              PosicionSpinner = position;
              if (position==0 ) {
//
                  final List<OrdenesUnion> ordenes_union = inventory.getOrdersUnion();
                  adapter = new OrdenesUnionAdapter(ordenes_union, getApplicationContext());
                  recyclerView.setAdapter(adapter);
//
              }
              else{
//
                  List<OrdenesUnion> ordenes_union =inventory.getFiltersOrder(String.valueOf(ordes_status_list.get(position-1).getId()));
                  adapter = new OrdenesUnionAdapter (ordenes_union, getApplicationContext());
                  recyclerView.setAdapter(adapter);
//
              }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
//
            }
        });




    } // END ON CREATE


    //Hace que aparezca el icono en el App Bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu2,menu);
        MenuItem menuItem = menu.findItem(R.id.buscar);
        SearchView searchView =  (SearchView) MenuItemCompat.getActionView(menuItem);
        searchView.setOnQueryTextListener(this);
        return true;
    }



    //Cuando se selecciona algo del item bar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        /*
            case R.id.agregar:

                final AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
                final View mView = getLayoutInflater().inflate(R.layout.customer_add,null);
                final EditText mNombre = (EditText)mView.findViewById(R.id.customer_first_name);
                final EditText mApellido = (EditText)mView.findViewById(R.id.customer_last_name);
                final EditText mDireccion = (EditText)mView.findViewById(R.id.customer_address);
                final EditText mTel1 = (EditText)mView.findViewById(R.id.customer_phone1);
                final EditText mTel2 = (EditText)mView.findViewById(R.id.customer_phone2);
                final EditText mTel3 = (EditText)mView.findViewById(R.id.customer_phone3);
                final EditText mEmail = (EditText)mView.findViewById(R.id.customer_email);
                Button mGuardar = (Button) mView.findViewById(R.id.customer_save);
                Button mCancelar = (Button) mView.findViewById(R.id.customer_cancel);


                mBuilder.setView(mView);
                dialogShow = mBuilder.create();
                dialogShow.show();




                mGuardar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if ( mNombre.getText().toString().equals("") || mApellido.getText().toString().equals("")|| mDireccion.getText().toString().equals("") )
                        {
                            Toast.makeText(getApplicationContext(), "¡Error! Campo Vacío", Toast.LENGTH_SHORT).show();
                        }
                        else {

                            Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_SHORT).show();
                            inventory.AddCustomer(inventory.getLastId(InventoryDbSchema.Customers_Table.NAME) + 1,mNombre.getText().toString(),mApellido.getText().toString(),mDireccion.getText().toString(),mTel1.getText().toString(),mTel2.getText().toString(),mTel3.getText().toString(),mEmail.getText().toString());
                            dialogShow.dismiss();
                            updateRecycler();
                        }
                    }
                });


                mCancelar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CancelarshowAlert();
                    }
                });



                return true;*/
        }
        return super.onOptionsItemSelected(item);
    }
    public void CancelarshowAlert( ){
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
                        dialogShow.dismiss();

                    }
                })
                .setTitle("Customer")
                .setIcon(R.drawable.ic_shortcut_warning)
                .create();
        myAlert.show();

    }




    public void updateRecycler(){
        /*inventory= new Inventory(getApplicationContext());
        final List<Customers> customers = inventory.customers_alfabetic();
        adapter= new ClientesActivity.ClientesAdapter(customers,this);
        recyclerView.setAdapter(adapter);*/
    }
/*
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if ( (requestCode==request_code && resultCode== RESULT_OK)) {
            inventory = new Inventory(getApplicationContext());

            // customers
            final List<Customers> customers = inventory.customers_alfabetic();
            adapter= new ClientesActivity.ClientesAdapter(customers,getApplicationContext());

        }
        recyclerView.setAdapter(adapter);


    }
    */

    //SearchView
    @Override
    public boolean onQueryTextSubmit(String query) {
        onQueryTextChange(query);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        //aca va el filtro del search , newText es lo que esta en el campo de busqueda
        final List<OrdenesUnion> ordenes = inventory.searchCustomerswithOrders(newText);
        adapter = new OrdenesActivity.OrdenesUnionAdapter(ordenes, getApplicationContext());
        recyclerView.setAdapter(adapter);
        return false;
    }

}
