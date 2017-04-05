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
import android.widget.TextView;
import android.widget.Toast;

import com.azore.compustore.fiuady.db.Inventory;
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

          startActivityForResult(intent,0);


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

    private RecyclerView recyclerView;
    private ProductsAdapter adapter;
    private Inventory inventory;

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


        final List<Products> products = inventory.products_alfabetic();


        Toast.makeText(getApplicationContext(), products.get(1).getDescription(), Toast.LENGTH_SHORT).show();
        adapter = new ProductsAdapter(products, this);
        recyclerView.setAdapter(adapter);

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
                Toast.makeText(this,"me tocaste",Toast.LENGTH_SHORT).show();
                return true;
            // Codigo prueba

        }
        return super.onOptionsItemSelected(item);

    }





}
