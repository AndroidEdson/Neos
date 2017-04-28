package com.azore.compustore;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.azore.compustore.fiuady.db.Assemblies;
import com.azore.compustore.fiuady.db.CategoryProduct;
import com.azore.compustore.fiuady.db.Customers;
import com.azore.compustore.fiuady.db.Inventory;
import com.azore.compustore.fiuady.db.InventoryDbSchema;
import com.azore.compustore.fiuady.db.Products;

import java.util.List;

public class Add_Assembly_to_Order extends AppCompatActivity implements SearchView.OnQueryTextListener  {

    private class AssembliesHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView txtDescription;
        Context context;
        private List<Assemblies> assemblies;

        public AssembliesHolder(View itemView, Context context, List<Assemblies> assemblies){

            super(itemView);
            this.context= context;
            this.assemblies= assemblies;
            itemView.setOnClickListener(this);
            txtDescription= (TextView) itemView.findViewById(R.id.txt_assemblie_description);

        }

        public void bindCategories(Assemblies assemblies){
            txtDescription.setText(assemblies.getDescription());
        }


        @Override
        public void onClick(View v) {
            int position = getAdapterPosition() ;
            Assemblies assemblies=this.assemblies.get(position);

            Intent intent = new Intent(getApplicationContext(), PopUp_add_Ensamble_Order.class);
            intent.putExtra(PopUp_add_Ensamble_Order.EXTRA_DESCRIPTION_ENSAMBLE, assemblies.getDescription());
            intent.putExtra(PopUp_add_Ensamble_Order.EXTRA_ENSAMBLE_ID,String.valueOf(assemblies.getId()));
         //   intent.putExtra(PopUp_add_Ensamble_Order.EXTRA_ID_ORDER,id_order);

            startActivityForResult(intent, requestcode);
        }

    }


    private  class AssembliesAdapter extends  RecyclerView.Adapter<Add_Assembly_to_Order.AssembliesHolder>{
        private List<Assemblies> assemblies;
        Context context;

        public AssembliesAdapter(List<Assemblies> assemblies, Context context){
            this.assemblies=assemblies;
            this.context=context;
        }


        @Override
        public Add_Assembly_to_Order.AssembliesHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = getLayoutInflater().inflate(R.layout.ensambles_list_item,parent,false);
            return new Add_Assembly_to_Order.AssembliesHolder(view,context,assemblies);
        }

        @Override
        public void onBindViewHolder(Add_Assembly_to_Order.AssembliesHolder holder, int position) {
            holder.bindCategories(assemblies.get(position));
        }

        @Override
        public int getItemCount() {
            return assemblies.size();
        }
    }

    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    //:::::::::::::::::::::::::::VARIABLES  ::::::::::::::::::::::::::::::::::::
    public static String EXTRA_ID_CUSTOMER ="com.azore.compustore.idcustomer" ;
    public static String EXTRA_ID_ORDER ="com.azore.compustore.EXTRA_ID_ORDER" ;

    private RecyclerView recyclerView;
    private Add_Assembly_to_Order.AssembliesAdapter adapter;
    private Inventory inventory;
    String id_order;
    int requestcode;

    private String search_text;

    private final String KEY_SEARCH= "key_search";

    //**************************************************************************************
    //**************************************************************************************
    //************************************ONCREATE*********************************************



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__assembly_to__order);
        getSupportActionBar().setTitle("Agregar ensamble a la orden");

        Intent i = getIntent();
       id_order= i.getStringExtra(EXTRA_ID_ORDER);


        inventory= new Inventory(getApplicationContext());
        recyclerView= (RecyclerView) findViewById(R.id.recycler_assemblies_of_order);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        final List<Assemblies> assemblies = inventory.getAssemblies_alfabetic();
        adapter= new Add_Assembly_to_Order.AssembliesAdapter(assemblies,this);
        recyclerView.setAdapter(adapter);

        if(savedInstanceState!= null)
        {
            search_text= savedInstanceState.getString(KEY_SEARCH, "");

        }

        if(search_text != null) {
            final List<Assemblies> assemblies2 = inventory.searchEnsamble(search_text);
            adapter= new AssembliesAdapter(assemblies2,this);
            recyclerView.setAdapter(adapter);
        }




    }

    //**************************************************************************************
    //**************************************************************************************
    //************************************ END ONCREATE*********************************************


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        final List<Assemblies> assemblies = inventory.getAssemblies_alfabetic();
        adapter= new Add_Assembly_to_Order.AssembliesAdapter(assemblies,this);
        recyclerView.setAdapter(adapter);

        setResult(RESULT_OK);
        finish();

    }




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
        search_text=newText;

        final List<Assemblies> assemblies = inventory.searchEnsamble(newText);
        adapter= new Add_Assembly_to_Order.AssembliesAdapter(assemblies,this);
        recyclerView.setAdapter(adapter);
        return false;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(KEY_SEARCH,search_text);


    }



}
