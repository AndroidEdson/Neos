package com.azore.compustore;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.azore.compustore.fiuady.db.AssemblieOrders_Union;
import com.azore.compustore.fiuady.db.Assemblies;
import com.azore.compustore.fiuady.db.Inventory;
import com.azore.compustore.fiuady.db.OrdenesUnion;
import com.azore.compustore.fiuady.db.Products;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Armín on 16/04/2017.
 */

public class Modif_Ordenes_N2 extends AppCompatActivity {


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
            intent.putExtra(pop_menu_orders_modif_N3.EXTRA_ORDER_ID,id_order);
           startActivityForResult(intent, request_code);


        }

    }


    private  class AssembliesOrdersUnionAdapter extends  RecyclerView.Adapter<AssembliesHolder>{
        private List<AssemblieOrders_Union> assemblies;
        Context context;

        public AssembliesOrdersUnionAdapter(List<AssemblieOrders_Union> assemblies, Context context){
            this.assemblies=assemblies;
            this.context=context;
        }


        @Override
        public AssembliesHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = getLayoutInflater().inflate(R.layout.orders_assemblies_union,parent,false);
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


    //**********************************************************************************************
    //**********************************************************************************************
    //*************************************ON VARIABLES************************************************
    //**********************************************************************************************





    public static String EXTRA_ORDER_ID="com.azore.compustore.order.id" ;

    private RecyclerView recyclerView;
    private AssembliesOrdersUnionAdapter adapter;
    private Inventory inventory;
    private String id_order;
    private EditText txt_description;
    private OrdenesUnion ordenesUnion;
    int request_code=0;
    private Button btn_save ;
    private Button btn_cancel ;
    private List<AssemblieOrders_Union> assemblieOrders_unions_original;
    private  int list_id[];
    private  int list_assembly_id[];
    private  String list_description[];
    private  int list_qty[];
    private  double list_price[];


//   this.id = id;
//       this.assembly_id = assembly_id;
//       this.description = description;
//       this.qty = qty;
//       this.price = price;
//
    private  int request_code1=1;
    private final String KEY_ID= "key_id";
    private final String KEY_ID_ORDER= "key_order";
    private final String KEY_ID_ASSEMBLY= "key_id_assembly";
    private final String KEY_DESCRIPTION= "key_description";
    private final String KEY_QTY= "key_qty";
    private final String KEY_PRICE= "key_price";


    //**********************************************************************************************
    //**********************************************************************************************
    //*************************************ON CREATE************************************************
    //**********************************************************************************************

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_assemblies);

        getSupportActionBar().setTitle("Modificar Orden");

        recyclerView = (RecyclerView) findViewById(R.id.recycler_products_assemblies);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        txt_description= (EditText)findViewById(R.id.edit_new_assembli_description) ;
        btn_save= (Button) findViewById(R.id.btnGuardar);
        btn_cancel= (Button) findViewById(R.id.btnCancelar);

        txt_description.setEnabled(false);
        inventory= new Inventory(getApplicationContext());

        Intent i = getIntent();
        id_order = i.getStringExtra(EXTRA_ORDER_ID);

       // assemblieOrders_unions_original;

        if(savedInstanceState!= null)
        {
            id_order= savedInstanceState.getString(KEY_ID, "");
            //   original_name= savedInstanceState.getString(KEY_NAME, "");
        }

        ordenesUnion= inventory.getOneOrder(id_order);
        txt_description.setText("Orden: "+ordenesUnion.getId()+ " " + ordenesUnion.getLast_name()+", "+ ordenesUnion.getFirst_name());

         assemblieOrders_unions_original =inventory.getEnsambliesInOrder(id_order);


        list_id= new int[assemblieOrders_unions_original.size()];
        list_assembly_id= new int[assemblieOrders_unions_original.size()];
        list_description=  new String[assemblieOrders_unions_original.size()];
        list_qty=  new int[assemblieOrders_unions_original.size()];
        list_price=  new double[assemblieOrders_unions_original.size()];

        if(savedInstanceState!= null) {
            list_id= savedInstanceState.getIntArray(KEY_ID_ORDER);
            list_assembly_id= savedInstanceState.getIntArray(KEY_ID_ASSEMBLY);
            list_description= savedInstanceState.getStringArray(KEY_DESCRIPTION);
            list_qty= savedInstanceState.getIntArray(KEY_QTY);
            list_price= savedInstanceState.getDoubleArray(KEY_PRICE);

            for(int k=0; k<list_id.length; k++) {
                AssemblieOrders_Union aux= new AssemblieOrders_Union(list_id[k],list_assembly_id[k],list_description[k],list_qty[k],list_price[k]);
                assemblieOrders_unions_original.set(k,aux);
            }
          // Toast.makeText(getApplicationContext(),"Entró", Toast.LENGTH_SHORT).show();
        }


        final List<AssemblieOrders_Union> assemblieOrders_unions = inventory.getEnsambliesInOrder(id_order);

        for(int j=0; j< assemblieOrders_unions_original.size(); j++){

            list_id[j]=assemblieOrders_unions_original.get(j).getId();
            list_assembly_id[j]=assemblieOrders_unions_original.get(j).getAssembly_id();
            list_description[j]=assemblieOrders_unions_original.get(j).getDescription();
            list_qty[j]=assemblieOrders_unions_original.get(j).getQty();
            list_price[j]=assemblieOrders_unions_original.get(j).getPrice();

        }



        adapter = new AssembliesOrdersUnionAdapter( assemblieOrders_unions, this);
        recyclerView.setAdapter(adapter);


        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final List<AssemblieOrders_Union> assemblieOrders_unions = inventory.getEnsambliesInOrder(id_order);
            if (assemblieOrders_unions.size()==0)
            {
                Toast.makeText(getApplicationContext(), "Orden Vacía, Ingrese un ensamble !", Toast.LENGTH_SHORT).show();
            }else {

                Toast.makeText(getApplicationContext(), "Cambios Realizados !", Toast.LENGTH_SHORT).show();
                Intent intent_back = new Intent();
                setResult(RESULT_OK, intent_back);
                finish();
            }
            }
        });




        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                inventory.deleteAllAssembliesFromOrder(id_order);
                for (int i = 0; i < assemblieOrders_unions_original.size(); i++) {
                    if (assemblieOrders_unions_original.get(i) != null) {
                        inventory.AddAssemblyOrder(assemblieOrders_unions_original.get(i).getId(), assemblieOrders_unions_original.get(i).getAssembly_id(),assemblieOrders_unions_original.get(i).getQty());
                    }
                }

                Toast.makeText(getApplicationContext(), "No se realizaron modificaciones", Toast.LENGTH_SHORT).show();
                Intent intent_back = new Intent();
                setResult(RESULT_OK, intent_back);
                finish();
            }
        });



    }
    //**********************************************************************************************
    //**********************************************************************************************
    //*************************************ON CREATE************************************************
    //**********************************************************************************************

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


                Intent intent = new Intent(getApplicationContext(), Add_assembly_to_order_modif.class);
                intent.putExtra(Add_assembly_to_order_modif.EXTRA_ID_ORDER, String.valueOf(ordenesUnion.getId())) ;
                startActivityForResult(intent, request_code1);

        }
        return super.onOptionsItemSelected(item);



    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        final List<AssemblieOrders_Union> assemblieOrders_unions = inventory.getEnsambliesInOrder(id_order);
        adapter = new AssembliesOrdersUnionAdapter( assemblieOrders_unions, this);
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void onBackPressed() {

        inventory.deleteAllAssembliesFromOrder(id_order);
        for (int i = 0; i < assemblieOrders_unions_original.size(); i++) {
            if (assemblieOrders_unions_original.get(i) != null) {
                inventory.AddAssemblyOrder(assemblieOrders_unions_original.get(i).getId(), assemblieOrders_unions_original.get(i).getAssembly_id(),assemblieOrders_unions_original.get(i).getQty());
            }
        }

        //Toast.makeText(getApplicationContext(), "No se realizaron modificaciones", Toast.LENGTH_SHORT).show();
        super.onBackPressed();
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(KEY_ID,id_order);
        outState.putIntArray(KEY_ID_ORDER,list_id );
        outState.putIntArray(KEY_ID_ASSEMBLY,list_assembly_id );
        outState.putStringArray(KEY_DESCRIPTION,list_description );
        outState.putIntArray(KEY_QTY,list_qty );
        outState.putDoubleArray(KEY_PRICE,list_price );


    }
}// END CLASS
