package com.azore.compustore;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.azore.compustore.fiuady.db.Inventory;
import com.azore.compustore.fiuady.db.Products;

import java.util.List;

public class Missing_Products_Of_Order extends AppCompatActivity {

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
            txtStock.setText(String.valueOf(Math.abs(product.getQty())));
        }


        @Override
        public void onClick(View v) {
            int position = getAdapterPosition() ;
            Products product=this.Products.get(position);

            //  Intent intent = new Intent(getApplicationContext(), PopUp_products.class);
            //  intent.putExtra(PopUp_products.EXTRA_DESCRIPTION, product.getDescription());
            //  intent.putExtra(PopUp_products.EXTRA_ID, Integer.toString(product.getId()));
            //  intent.putExtra(PopUp_products.EXTRA_QTY, Integer.toString(product.getQty()));
            //  startActivityForResult(intent, request_code2);

        }

    }


    private  class ProductsAdapter extends  RecyclerView.Adapter<Missing_Products_Of_Order.ProductsHolder>{
        private List<Products> products;
        Context context;

        public ProductsAdapter(List<Products> products, Context context){
            this.products=products;

            this.context=context;
        }


        @Override
        public Missing_Products_Of_Order.ProductsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = getLayoutInflater().inflate(R.layout.product_list_item_missing,parent,false);
            return new Missing_Products_Of_Order.ProductsHolder(view,context,products);
        }

        @Override
        public void onBindViewHolder(Missing_Products_Of_Order.ProductsHolder holder, int position) {
            holder.bindCategories(products.get(position));
        }

        @Override
        public int getItemCount() {
            return products.size();
        }
    }




// ********************************************************************
// ********************************************************************
// ********************** VARIABLES ************************************

    public static String EXTRA_ORDER_ID="com.azore.compustore.order.id" ;

    private RecyclerView recyclerView;
    private Missing_Products_Of_Order.ProductsAdapter adapter;
    private Inventory inventory;
    private String id_order;



// ********************************************************************
// ********************************************************************
// **********************ON CREATE ************************************


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_missing__products__of__order);


        Intent i = getIntent();
        id_order = i.getStringExtra(EXTRA_ORDER_ID);
        getSupportActionBar().setTitle("Productos faltantes Orden "+id_order);

        inventory = new Inventory(getApplicationContext());
        recyclerView = (RecyclerView) findViewById(R.id.recycler_products);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        final List<Products> products = inventory.getMissing_products_of_order(Integer.parseInt(id_order));
        adapter = new Missing_Products_Of_Order.ProductsAdapter(products, this);
        recyclerView.setAdapter(adapter);

    }
    // ********************************************************************
// ********************************************************************
// **********************END CREATE ************************************
}
