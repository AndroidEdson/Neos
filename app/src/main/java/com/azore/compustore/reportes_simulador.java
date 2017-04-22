package com.azore.compustore;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.Layout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.azore.compustore.fiuady.db.Inventory;
import com.azore.compustore.fiuady.db.OrdenesUnion;
import com.azore.compustore.fiuady.db.Products;

import java.util.List;

public class reportes_simulador extends AppCompatActivity implements SearchView.OnQueryTextListener  {
    // ****************************************INICIO DE RECYCLER*****************************************************

    private class OrdenesUnionHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView txtId;
        private TextView txtName;
        private TextView txtStatus;
        private TextView txtCost;
        private TextView txtDate;
        private LinearLayout layout;

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
            layout = (LinearLayout)itemView.findViewById(R.id.LinearOrder);






        }

        public void bindCategories(OrdenesUnion ordenesUnion){
            txtId.setText(String.valueOf(ordenesUnion.getId()));
            txtName.setText(ordenesUnion.getLast_name() + " " + ordenesUnion.getFirst_name());
            txtStatus.setText(ordenesUnion.getStatus_description());
            txtCost.setText(Double.toString((ordenesUnion.getCosto()/100)));


            // Double.toString((double) product.getPrice()/100)
            //   Date cDate = new Date();
            //  String fDate = new SimpleDateFormat("yyyy-MM-dd").format(cDate);

            txtDate.setText(ordenesUnion.getDate());


        }


        @Override
        public void onClick(View v) {
            int position = getAdapterPosition() ;
            OrdenesUnion ordenesUnion=this.ordenesUnions.get(position);


/*
            Intent intent = new Intent(getApplicationContext(), Pop_Up_ordenes_N1.class);
            intent.putExtra(Pop_Up_ordenes_N1.EXTRA_ORDER_ID, String.valueOf(ordenesUnion.getId()));
            startActivityForResult(intent, request_code);*/


        }

    }


    private  class OrdenesUnionAdapter extends  RecyclerView.Adapter<reportes_simulador.OrdenesUnionHolder>{
        private List<OrdenesUnion> ordenesUnions;
        Context context;


        public OrdenesUnionAdapter(List<OrdenesUnion> ordenesUnions, Context context){
            this.ordenesUnions=ordenesUnions;
            this.context=context;
        }


        @Override
        public reportes_simulador.OrdenesUnionHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = getLayoutInflater().inflate(R.layout.ordenesunion_list_item,parent,false);
            return new reportes_simulador.OrdenesUnionHolder(view, context, ordenesUnions);
        }

        @Override
        public void onBindViewHolder(reportes_simulador.OrdenesUnionHolder holder, int position) {
            holder.bindCategories(ordenesUnions.get(position));

            List<Products> productosfaltantes=inventory.getMissing_products_of_order(ordenesUnions.get(position).getId());
            if (productosfaltantes.isEmpty()){
                holder.layout.setBackgroundColor(Color.GREEN);
            }
            else {
                holder.layout.setBackgroundColor(Color.RED);
            }


        }

        @Override
        public int getItemCount() {
            return ordenesUnions.size();
        }
    }

    // ****************************************END  DE RECYCLER*****************************************************


    // **************************************** VARIABLES ****************************************************


    private RecyclerView recyclerView;
    private reportes_simulador.OrdenesUnionAdapter adapter;
    private Inventory inventory;
    private final int request_code=0;


    // ******************************************************************************************************

    // **************************************** ON CREATE  ****************************************************

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reportes_simulador);
        //Cambiar texto de App bar
        getSupportActionBar().setTitle("Simulador");


        inventory = new Inventory(getApplicationContext());
        inventory.drop1();
        inventory.drop2();
        inventory.crear_tabla_2();
        inventory.create_actual_temporal_stock();

        recyclerView = (RecyclerView) findViewById(R.id.recycler_orders_simulator);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        final List<OrdenesUnion> ordenes_union = inventory.getOrdersUnion();

        for (OrdenesUnion orden : ordenes_union){
            inventory.insert_products(orden.getId());
        }

        adapter = new reportes_simulador.OrdenesUnionAdapter(ordenes_union, this);
        recyclerView.setAdapter(adapter);



    } // END ON CREATE


    //Hace que aparezca el icono en el App Bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu3,menu);
        MenuItem menuItem = menu.findItem(R.id.buscar);
        SearchView searchView =  (SearchView) MenuItemCompat.getActionView(menuItem);
        searchView.setOnQueryTextListener(this);
        return true;
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
        final List<OrdenesUnion> ordenes = inventory.searchCustomerswithOrders(newText);
        inventory.drop1();
        inventory.drop2();
        inventory.crear_tabla_2();
        inventory.create_actual_temporal_stock();
        for (OrdenesUnion orden : ordenes){
            inventory.insert_products(orden.getId());
        }
        adapter = new reportes_simulador.OrdenesUnionAdapter(ordenes, getApplicationContext());
        recyclerView.setAdapter(adapter);

        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //inventory.drop1();
        //inventory.drop2();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

}
