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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.azore.compustore.fiuady.db.Inventory;
import com.azore.compustore.fiuady.db.InventoryDbSchema;
import com.azore.compustore.fiuady.db.Products;

import java.util.List;

public class add_assemblies extends AppCompatActivity {

    private class ProductsHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView txtDescription;
        private TextView txtPrice;
        private  TextView txtStock;

        Context context;
        private List<Products> Products;

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


          Intent intent = new Intent(getApplicationContext(), PopUp_Add_Assemblies.class);
          intent.putExtra(PopUp_Add_Assemblies.EXTRA_DESCRIPTION_ADD_ASSEMBLIES, product.getDescription());
          intent.putExtra(PopUp_Add_Assemblies.EXTRA_ID_ADD_ASSEMBLIES, Integer.toString(product.getId()));
          intent.putExtra(PopUp_Add_Assemblies.EXTRA_QTY_ADD_ASSEMBLIES, Integer.toString(product.getQty()));
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
            View view = getLayoutInflater().inflate(R.layout.product_list_item_with_qty,parent,false);
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
    private  int lastid;
    private  String original_name="";

    //***************************************************************************
    //***************************************************************************
    //***************************ONCREATE*********************************

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_assemblies);
        getSupportActionBar().setTitle("Nuevo Ensamble");


        inventory = new Inventory(getApplicationContext());
        recyclerView = (RecyclerView) findViewById(R.id.recycler_products_assemblies);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        btn_save= (Button) findViewById(R.id.btnGuardar);
        btn_cancel= (Button) findViewById(R.id.btnCancelar);
        new_description= (EditText)findViewById(R.id.edit_new_assembli_description);

        lastid= inventory.getLastId(InventoryDbSchema.Assemblies_Table.NAME)+1;

        //final List<Products> products = null;
//  Toast.makeText(getApplicationContext(), products.get(1).getDescription(), Toast.LENGTH_SHORT).show();
        //adapter = new ProductsAdapter(products, this);
        //recyclerView.setAdapter(adapter);

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int i= inventory.NameValidationGeneric(InventoryDbSchema.Assemblies_Table.NAME,new_description.getText().toString());

                if ( new_description.getText().toString().equals("")){

                    Toast.makeText(getApplicationContext(), "Descripción Invalida",Toast.LENGTH_SHORT).show();

                }
                else
                {
                    inventory.updateAssemblies(String.valueOf(lastid), new_description.getText().toString());
                    finish();
                    Toast.makeText(getApplicationContext(), "Agregado Exitosamente",Toast.LENGTH_SHORT).show();
                }



            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                inventory.deleteAssemblies(String.valueOf(lastid));
                finish();
            }
        });






    }

    //***************************************************************************
    //***************************************************************************
    //***************************END ONCREATE*********************************

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
                int aux=0;
                 aux = inventory.NameValidationGeneric(InventoryDbSchema.Assemblies_Table.NAME,new_description.getText().toString() );
                if (new_description.getText().toString().equals(""))
                {
                    Toast.makeText(getApplicationContext(), "Ingrese Nombre del ensamble",Toast.LENGTH_SHORT).show();
                }else
            {

                if(original_name.equals(new_description.getText().toString())) {

                    //inventory.AddAssemblies(lastid, new_description.getText().toString());
                    Intent intent = new Intent(getApplicationContext(), Add_Product_to_Ensamble.class);

                    intent.putExtra(Add_Product_to_Ensamble.EXTRA_ID_PRODUCT, "");
                    intent.putExtra(Add_Product_to_Ensamble.EXTRA_DESCRIPTION_PRODUCT, "");

                    intent.putExtra(Add_Product_to_Ensamble.EXTRA_DESCRIPTION_ASSEMBLY, new_description.getText().toString());
                    intent.putExtra(Add_Product_to_Ensamble.EXTRA_ID_ASSEMBLY, String.valueOf(lastid));
                    startActivityForResult(intent, request_code2);

                }   else {
                    if (aux >= 1) {

                        Toast.makeText(getApplicationContext(), "Ya existe un ensamble con ese nombre", Toast.LENGTH_SHORT).show();

                    } else {
                        inventory.AddAssemblies(lastid, new_description.getText().toString());

                        Intent intent = new Intent(getApplicationContext(), Add_Product_to_Ensamble.class);

                        intent.putExtra(Add_Product_to_Ensamble.EXTRA_ID_PRODUCT, "");
                        intent.putExtra(Add_Product_to_Ensamble.EXTRA_DESCRIPTION_PRODUCT, "");

                        intent.putExtra(Add_Product_to_Ensamble.EXTRA_DESCRIPTION_ASSEMBLY, new_description.getText().toString());
                        intent.putExtra(Add_Product_to_Ensamble.EXTRA_ID_ASSEMBLY, String.valueOf(lastid));
                        startActivityForResult(intent, request_code2);
                    }

                }
            }
            // Codigo prueba

        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        List<Products> products =inventory.getProductsAssembly(String.valueOf(lastid));
        adapter = new ProductsAdapter(products, getApplicationContext());
        recyclerView.setAdapter(adapter);

        original_name=new_description.getText().toString();

        //if ((requestCode == request_code2 && resultCode == RESULT_OK)) {

         //  final List<Products> products = inventory.products_alfabetic();
         //  //   Toast.makeText(getApplicationContext(), products.get(1).getDescription(), Toast.LENGTH_SHORT).show();
         //  adapter = new ProductsAdapter(products, this);
         //  recyclerView.setAdapter(adapter);

      //  }

    }
}
