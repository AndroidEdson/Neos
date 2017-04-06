package com.azore.compustore;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.azore.compustore.fiuady.db.CategoryProduct;
import com.azore.compustore.fiuady.db.Customers;
import com.azore.compustore.fiuady.db.Inventory;
import com.azore.compustore.fiuady.db.InventoryDbSchema;
import com.azore.compustore.fiuady.db.Products;

import java.util.List;


public class ClientesActivity extends AppCompatActivity {



    private class ClientesHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView txtFirstName;
        private TextView txtLastName;
        private  TextView txtAddress;
        private  TextView txtPhone1;
        private  TextView txtPhone2;
        private  TextView txtPhone3;
        private  TextView txtEmail;
        private LinearLayout linealphone1;
        private LinearLayout linealphone2;
        private LinearLayout linealphone3;
        private LinearLayout linealemail;



        Context context;
        private List<Customers> customers;

        public ClientesHolder(View itemView, Context context, List<Customers> customers){
            super(itemView);
            this.context= context;
            this.customers= customers;
            itemView.setOnClickListener(this);
            txtFirstName= (TextView) itemView.findViewById(R.id.txt_customer_first_name);
            txtLastName= (TextView) itemView.findViewById(R.id.txt_customer_last_name);
            txtAddress= (TextView) itemView.findViewById(R.id.txt_customer_address);

            txtPhone1= (TextView) itemView.findViewById(R.id.txt_customer_phone1);
            txtPhone2= (TextView) itemView.findViewById(R.id.txt_customer_phone2);
            txtPhone3= (TextView) itemView.findViewById(R.id.txt_customer_phone3);
            txtEmail= (TextView) itemView.findViewById(R.id.txt_customer_email);
            linealphone1= (LinearLayout) itemView.findViewById(R.id.label_tel1);
            linealphone2= (LinearLayout) itemView.findViewById(R.id.label_tel2);
            linealphone3= (LinearLayout) itemView.findViewById(R.id.label_tel3);
            linealemail= (LinearLayout) itemView.findViewById(R.id.label_email);


        }

        public void bindCustomers(Customers customer){
            txtFirstName.setText(customer.getFirst_name());
            txtLastName.setText(customer.getLast_name());
            txtAddress.setText(customer.getAddress());
            txtPhone1.setText(customer.getPhone1());
            txtPhone2.setText(customer.getPhone2());
            txtPhone3.setText(customer.getPhone3());
            txtEmail.setText(customer.getEmail());
        }


        @Override
        public void onClick(View v) {
            int position = getAdapterPosition() ;
            Customers customer= this.customers.get(position);

            Intent intent = new Intent(getApplicationContext(), Pop_up_customers.class);
            intent.putExtra(Pop_up_customers.EXTRA_Customer_ID, Integer.toString(customer.getId()));
            intent.putExtra(Pop_up_customers.EXTRA_First_Name, customer.getFirst_name());
            startActivityForResult(intent, request_code);

        }

    }


    private  class ClientesAdapter extends  RecyclerView.Adapter<ClientesActivity.ClientesHolder>{
        private List<Customers> customers;
        Context context;

        public ClientesAdapter(List<Customers> customers, Context context){
            this.customers=customers;
            this.context=context;
        }


        @Override
        public ClientesActivity.ClientesHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = getLayoutInflater().inflate(R.layout.customers_list_item,parent,false);
            return new ClientesActivity.ClientesHolder(view,context,customers);
        }

        @Override
        public void onBindViewHolder(ClientesActivity.ClientesHolder holder, int position) {
            holder.bindCustomers(customers.get(position));
            //Validacion de datos opcionales
            if(customers.get(position).getPhone1() == null || customers.get(position).getPhone1().isEmpty()){
               holder.linealphone1.setVisibility(View.GONE);
            }
            if(customers.get(position).getPhone2() == null ||customers.get(position).getPhone2().isEmpty()){
                holder.linealphone2.setVisibility(View.GONE);
            }
            if(customers.get(position).getPhone3() == null || customers.get(position).getPhone3().isEmpty() ){
                holder.linealphone3.setVisibility(View.GONE);
            }
            if(customers.get(position).getEmail() == null || customers.get(position).getEmail().isEmpty() ){
                holder.linealemail.setVisibility(View.GONE);
            }
            //Validacion de datos opcionales
        }

        @Override
        public int getItemCount() {
            return customers.size();
        }
    }

    private RecyclerView recyclerView;
    private ClientesActivity.ClientesAdapter adapter;
    private Inventory inventory;
    private AlertDialog dialogShow ;
    private final int request_code=0;


//__________________________________________________________

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clientes);
        //Cambiar texto de App bar
        getSupportActionBar().setTitle("Clientes");
        inventory = new Inventory(getApplicationContext());
        recyclerView = (RecyclerView) findViewById(R.id.recycler_customers);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        inventory = new Inventory(getApplicationContext());
        final List<Customers> customers = inventory.customers_alfabetic();
        adapter = new ClientesActivity.ClientesAdapter(customers, this);
        recyclerView.setAdapter(adapter);






    }


    //Hace que aparezca el icono en el App Bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu2,menu);
        MenuItem menuItem = menu.findItem(R.id.buscar);

        return true;
    }



    //Cuando se selecciona algo del item bar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

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




                return true;
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
        inventory= new Inventory(getApplicationContext());
        final List<Customers> customers = inventory.customers_alfabetic();
        adapter= new ClientesActivity.ClientesAdapter(customers,this);
        recyclerView.setAdapter(adapter);
    }

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

}
