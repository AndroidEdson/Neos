package com.azore.compustore;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.azore.compustore.fiuady.db.Assemblies;
import com.azore.compustore.fiuady.db.Inventory;
import com.azore.compustore.fiuady.db.InventoryDbSchema;

import java.util.List;

/**
 * Created by Armín on 18/04/2017.
 */

public class Add_assembly_to_order_modif extends AppCompatActivity  {

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

            Intent intent = new Intent(getApplicationContext(), PopUp_add_Ensamble_order_modif.class);
            intent.putExtra(PopUp_add_Ensamble_order_modif.EXTRA_DESCRIPTION_ENSAMBLE, assemblies.getDescription());
            intent.putExtra(PopUp_add_Ensamble_order_modif.EXTRA_ENSAMBLE_ID,String.valueOf(assemblies.getId()));
               intent.putExtra(PopUp_add_Ensamble_order_modif.EXTRA_ID_ORDER,id_order);

            startActivityForResult(intent, requestcode);
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

    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    //:::::::::::::::::::::::::::VARIABLES  ::::::::::::::::::::::::::::::::::::
    public static String EXTRA_ID_CUSTOMER ="com.azore.compustore.idcustomer" ;
    public static String EXTRA_ID_ORDER ="com.azore.compustore.EXTRA_ID_ORDER" ;

    private RecyclerView recyclerView;
    private AssembliesAdapter adapter;
    private Inventory inventory;
    String id_order;
    int requestcode;
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
        adapter= new AssembliesAdapter(assemblies,this);
        recyclerView.setAdapter(adapter);





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

        setResult(RESULT_OK);
        finish();

    }

    /*
    //Hace que aparezca el icono en el App Bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }
    */
}
