package com.azore.compustore;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.azore.compustore.fiuady.db.Inventory;
import com.azore.compustore.fiuady.db.Orders;
import java.util.List;

public class OrdenesActivity extends AppCompatActivity   {
    private class OrdenesHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        Context context;
        private List<Orders> Ordenes;

        public OrdenesHolder(View itemView, Context context, List<Orders> ordenes){
            super(itemView);
            this.context= context;
            this.Ordenes= ordenes;
            itemView.setOnClickListener(this);
           // txtFirstName= (TextView) itemView.findViewById(R.id.txt_customer_first_name);

        }

        public void bindOrdenes(Orders orders){
          /*  txtFirstName.setText(customer.getFirst_name());
            txtLastName.setText(customer.getLast_name());
            txtAddress.setText(customer.getAddress());
            txtPhone1.setText(customer.getPhone1());
            txtPhone2.setText(customer.getPhone2());
            txtPhone3.setText(customer.getPhone3());
            txtEmail.setText(customer.getEmail());*/
        }


        @Override
        public void onClick(View v) {
           /* int position = getAdapterPosition() ;
            Customers customer= this.customers.get(position);

            Intent intent = new Intent(getApplicationContext(), Pop_up_customers.class);
            intent.putExtra(Pop_up_customers.EXTRA_Customer_ID, Integer.toString(customer.getId()));
            intent.putExtra(Pop_up_customers.EXTRA_First_Name, customer.getFirst_name());
            startActivityForResult(intent, request_code);
            */

        }

    }


    private  class OrdenesAdapter extends  RecyclerView.Adapter<OrdenesActivity.OrdenesHolder>{
        private List<Orders> ordenes;
        Context context;

        public OrdenesAdapter(List<Orders> ordenes, Context context){
            this.ordenes=ordenes;
            this.context=context;
        }


        @Override
        public OrdenesActivity.OrdenesHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = getLayoutInflater().inflate(R.layout.customers_list_item,parent,false);
            return new OrdenesActivity.OrdenesHolder(view,context,ordenes);
        }

        @Override
        public void onBindViewHolder(OrdenesActivity.OrdenesHolder holder, int position) {
            holder.bindOrdenes(ordenes.get(position));
        }

        @Override
        public int getItemCount() {
            return ordenes.size();
        }
    }

    private RecyclerView recyclerView;
    private OrdenesActivity.OrdenesAdapter adapter;
    private Inventory inventory;
    private AlertDialog dialogShow ;
    private final int request_code=0;
    public boolean chkNombre = true;
    public boolean chkApellido = false;
    public boolean chkDireccion = false;
    public boolean chkph = false;
    public boolean chkemail = false;


//__________________________________________________________

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clientes);
        //Cambiar texto de App bar
        getSupportActionBar().setTitle("Ordenes");
        inventory = new Inventory(getApplicationContext());
        recyclerView = (RecyclerView) findViewById(R.id.recycler_customers);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        inventory = new Inventory(getApplicationContext());
        final List<Orders> ordenes = inventory.customers_alfabetic();
        adapter = new OrdenesActivity.OrdenesAdapter(ordenes, this);
        recyclerView.setAdapter(adapter);

    }


    //Hace que aparezca el icono en el App Bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu2,menu);
        /*MenuItem menuItem = menu.findItem(R.id.buscar);
        SearchView searchView =  (SearchView) MenuItemCompat.getActionView(menuItem);
        searchView.setOnQueryTextListener(this);*/
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

  /*  @Override
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

    //SearchView
    @Override
    public boolean onQueryTextSubmit(String query) {
        onQueryTextChange(query);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        //aca va el filtro del search , newText es lo que esta en el campo de busqueda
        final List<Customers> customers = inventory.searchCustomers(newText,chkNombre,chkApellido,chkDireccion,chkph,chkemail);
        adapter = new ClientesActivity.ClientesAdapter(customers, getApplicationContext());
        recyclerView.setAdapter(adapter);
        return false;
    }
    */
}
