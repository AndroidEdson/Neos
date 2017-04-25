package com.azore.compustore;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.azore.compustore.fiuady.db.Assemblies;
import com.azore.compustore.fiuady.db.Assembly_Products;
import com.azore.compustore.fiuady.db.CategoryProduct;
import com.azore.compustore.fiuady.db.Inventory;
import com.azore.compustore.fiuady.db.InventoryDbSchema;
import com.azore.compustore.fiuady.db.Products;

import java.util.List;

public class EnsamblesActivity extends AppCompatActivity implements SearchView.OnQueryTextListener{

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


            Intent intent = new Intent(getApplicationContext(), PopUp_Ensambles.class);
            intent.putExtra(PopUp_Ensambles.EXTRA_DESCRIPTION_ENSAMBLE, assemblies.getDescription());
            intent.putExtra(PopUp_Ensambles.EXTRA_ID_ENSAMBLE, Integer.toString(assemblies.getId()));
 //   Toast.makeText(getApplicationContext(), "works ", Toast.LENGTH_SHORT).show();
            startActivityForResult(intent, request_code2);


        }

    }


    private  class AssembliesAdapter extends  RecyclerView.Adapter<AssembliesHolder>{
        private List<Assemblies> assemblies;
        Context context;

        public AssembliesAdapter(List<Assemblies> assemblies, Context context){
            this.assemblies=assemblies;
            this.context=context;
        }


        @Override
        public AssembliesHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = getLayoutInflater().inflate(R.layout.ensambles_list_item,parent,false);
            return new AssembliesHolder(view,context,assemblies);
        }

        @Override
        public void onBindViewHolder(AssembliesHolder holder, int position) {
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

    private RecyclerView recyclerView;
    private AssembliesAdapter adapter;
    private Inventory inventory;

    int requestcode1=0;
    int request_code2=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ensambles);
        //Cambiar texto de App bar
        getSupportActionBar().setTitle("Ensambles");

        inventory= new Inventory(getApplicationContext());
        recyclerView= (RecyclerView) findViewById(R.id.recycler_assemblies);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        final List<Assemblies> assemblies = inventory.getAssemblies_alfabetic();
        adapter= new AssembliesAdapter(assemblies,this);
        recyclerView.setAdapter(adapter);

       // int lastid= inventory.getLastId(InventoryDbSchema.Products_Table.NAME);
        //Toast.makeText(getApplicationContext(),  String.valueOf(lastid), Toast.LENGTH_SHORT).show();

    }

    //**************************************************************************************
    //**************************************************************************************
    //************************************ END ONCREATE*********************************************


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


            final List<Assemblies> assemblies = inventory.getAssemblies_alfabetic();
            adapter= new AssembliesAdapter(assemblies,this);
            recyclerView.setAdapter(adapter);

    }



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

            case R.id.agregar:
                // Codigo prueba

                Intent i = new Intent(getApplicationContext(),add_assemblies.class);
                startActivityForResult(i, requestcode1);
                return true;
            // Codigo prueba

        }
        return super.onOptionsItemSelected(item);

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
        final List<Assemblies> assemblies = inventory.searchEnsamble(newText);
        adapter= new EnsamblesActivity.AssembliesAdapter(assemblies,this);
        recyclerView.setAdapter(adapter);
        return false;
    }

}
