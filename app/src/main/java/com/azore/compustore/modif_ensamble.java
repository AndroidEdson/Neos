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
import android.view.accessibility.AccessibilityManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.azore.compustore.fiuady.db.Inventory;
import com.azore.compustore.fiuady.db.InventoryDbSchema;
import com.azore.compustore.fiuady.db.Products;

import java.util.List;

public class modif_ensamble extends AppCompatActivity {

    //****************************************************************************
    //****************************************************************************
    //********************************RECYCLER ***********************************


    private class ProductsHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView txtDescription;
        private TextView txtPrice;
        private  TextView txtStock;

        Context context;
        private List<com.azore.compustore.fiuady.db.Products> Products;

        public ProductsHolder(View itemView, Context context, List<Products> Products){

            super(itemView);
            this.context= context;
            this.Products= Products;
            itemView.setOnClickListener(this);
            txtDescription= (TextView) itemView.findViewById(R.id.txt_product_description);
            txtPrice= (TextView) itemView.findViewById(R.id.txt_product_price);
            txtStock= (TextView) itemView.findViewById(R.id.txt_stock_product);



        }

        public void bindCategories(Products product){

            txtDescription.setText(product.getDescription());
            txtPrice.setText(Double.toString((double) product.getPrice()/100));
            txtStock.setText(String.valueOf(product.getQty()));
        }


        @Override
        public void onClick(View v) {
            int position = getAdapterPosition() ;
            Products product=this.Products.get(position);


          Intent intent = new Intent(getApplicationContext(), PopUp_Menu_Modify_Assembly.class);
          intent.putExtra(PopUp_Menu_Modify_Assembly.EXTRA_DESCRIPTION_PRODUCT, product.getDescription());
          intent.putExtra(PopUp_Menu_Modify_Assembly.EXTRA_ID_PRODUCT, Integer.toString(product.getId()));
          intent.putExtra(PopUp_Menu_Modify_Assembly.EXTRA_QTY_PRODUCT, Integer.toString(product.getQty()));
            intent.putExtra(PopUp_Menu_Modify_Assembly.EXTRA_DESCRIPTION_ENSAMBLE, name_description);
            intent.putExtra(PopUp_Menu_Modify_Assembly.EXTRA_ID_ENSAMBLE, id);
           // Toast.makeText(getApplicationContext(), id+ " " + name_description, Toast.LENGTH_SHORT).show();

            startActivityForResult(intent,request_code2);


        }

    }


    private  class ProductsAdapter extends  RecyclerView.Adapter<ProductsHolder>{
        private List<Products> products;
        Context context;

        public ProductsAdapter(List<Products> products, Context context){
            this.products=products;
            this.context=context;
        }


        @Override
        public ProductsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = getLayoutInflater().inflate(R.layout.product_list_item,parent,false);
            return new ProductsHolder(view,context,products);
        }

        @Override
        public void onBindViewHolder(ProductsHolder holder, int position) {
            holder.bindCategories(products.get(position));
        }

        @Override
        public int getItemCount() {
            return products.size();
        }
    }

    //____________________________________________________________________________________

    //***************************************************************************
    //***************************************************************************
    //***************************VARIABLES *********************************

    private RecyclerView recyclerView;
    private ProductsAdapter adapter;
    private Inventory inventory;
    int request_code2=1;
    private Button btn_save ;
    private Button btn_cancel ;
    private EditText new_description;

    private String name_description;
    private String id;
    private TextView txt_change;

    public static String EXTRA_DESCRIPTION_ENSAMBLE_MODIF = "com.azore.compustore.id.add.assemblies.description_ensamble.modif";
    public static String EXTRA_ID_ENSAMBLE_MODIF = "com.azore.compustore.id.add.assemblies.id_ensamble.modif";


    //****************************************************************************
    //****************************************************************************
    //*********************************ON CREATE *******************************************


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_assemblies);

        Intent i = getIntent();
        name_description= i.getStringExtra(EXTRA_DESCRIPTION_ENSAMBLE_MODIF);
        id=i.getStringExtra(EXTRA_ID_ENSAMBLE_MODIF);
        getSupportActionBar().setTitle(name_description);


        inventory = new Inventory(getApplicationContext());
        recyclerView = (RecyclerView) findViewById(R.id.recycler_products_assemblies);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        btn_save= (Button) findViewById(R.id.btnGuardar);
        btn_cancel= (Button) findViewById(R.id.btnCancelar);
        new_description= (EditText)findViewById(R.id.edit_new_assembli_description);

        new_description.setText(null);
        new_description.setHint("Nueva descripción");

        List<Products> products =inventory.getProductsAssembly(id);
        adapter = new ProductsAdapter(products, getApplicationContext());
        recyclerView.setAdapter(adapter);

        //txt_change.setText("Num:");


        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int aux = inventory.NameValidationGeneric(InventoryDbSchema.Assemblies_Table.NAME,new_description.getText().toString() );
                if (new_description.getText().toString().equals(""))
                {
                    Toast.makeText(getApplicationContext(),"Descripción no valida",Toast.LENGTH_SHORT).show();

                }
                else {
                    if (aux >= 1) {
                        Toast.makeText(getApplicationContext(),"Ya existe un ensamble con esa descripción",Toast.LENGTH_SHORT).show();

                    }else {

                        inventory.updateAssemblies(id, new_description.getText().toString());
                        Intent intent_back = new Intent();
                        setResult(RESULT_OK, intent_back);
                        Toast.makeText(getApplicationContext(), "Ensamble Modificado", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }


            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    } // END ONCREATE

    //****************************************************************************
    //****************************************************************************
    //************************************END ONCREATE****************************************


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

       // Toast.makeText(getApplicationContext(),"UPDATED", Toast.LENGTH_LONG).show();
        List<Products> products =inventory.getProductsAssembly(id);
        adapter = new ProductsAdapter(products, getApplicationContext());
        recyclerView.setAdapter(adapter);

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


                return true;
            // Codigo prueba

        }
        return super.onOptionsItemSelected(item);

    }



}// FINAL DEL COSMOS
