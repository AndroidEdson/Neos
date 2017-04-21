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

import com.azore.compustore.fiuady.db.Ensambles_vendidos_Mes;
import com.azore.compustore.fiuady.db.Inventory;
import com.azore.compustore.fiuady.db.Products;

import java.util.List;

public class ensambles_in_month extends AppCompatActivity {

    private class ProductsHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView txtDescription;
        private TextView txtPrice;
        private  TextView txtStock;
        private  TextView txtDate;


        Context context;
        private List<Ensambles_vendidos_Mes> ensambles_vendidos_mes;

        public ProductsHolder(View itemView, Context context, List<Ensambles_vendidos_Mes> ensambles_vendidos_mes){

            super(itemView);
            this.context= context;
            this.ensambles_vendidos_mes= ensambles_vendidos_mes;

            itemView.setOnClickListener(this);
            txtDescription= (TextView) itemView.findViewById(R.id.txt_product_description);
            txtPrice= (TextView) itemView.findViewById(R.id.txt_product_price);
            txtStock= (TextView) itemView.findViewById(R.id.txt_stock_product);
            txtDate= (TextView) itemView.findViewById(R.id.txt_Date_ENSAMBLE);


        }

        public void bindCategories(Ensambles_vendidos_Mes ensambles_vendidos_mes){

            txtDescription.setText(ensambles_vendidos_mes.getDescription());
            txtPrice.setText(Double.toString((double) ensambles_vendidos_mes.getPrice_assembly()/100));
            txtStock.setText(String.valueOf(ensambles_vendidos_mes.getQty()));
            txtDate.setText(ensambles_vendidos_mes.getDate());

        }


        @Override
        public void onClick(View v) {
            int position = getAdapterPosition() ;
          // Products product=this.Products.get(position);


          // Intent intent = new Intent(getApplicationContext(), PopUp_products.class);
          // intent.putExtra(PopUp_products.EXTRA_DESCRIPTION, product.getDescription());
          // intent.putExtra(PopUp_products.EXTRA_ID, Integer.toString(product.getId()));
          // intent.putExtra(PopUp_products.EXTRA_QTY, Integer.toString(product.getQty()));

          // startActivityForResult(intent, request_code2);


        }

    }


    private  class ProductsAdapter extends  RecyclerView.Adapter<ProductsHolder>{
        private List<Ensambles_vendidos_Mes> ensambles_vendidos_mes;
        Context context;

        public ProductsAdapter(List<Ensambles_vendidos_Mes> ensambles_vendidos_mes, Context context){
            this.ensambles_vendidos_mes=ensambles_vendidos_mes;

            this.context=context;
        }


        @Override
        public ProductsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = getLayoutInflater().inflate(R.layout.ensambles_month_sales,parent,false);
            return new ProductsHolder(view,context,ensambles_vendidos_mes);
        }

        @Override
        public void onBindViewHolder(ProductsHolder holder, int position) {
            holder.bindCategories(ensambles_vendidos_mes.get(position));
        }

        @Override
        public int getItemCount() {
            return ensambles_vendidos_mes.size();
        }
    }

    private RecyclerView recyclerView;
    private ProductsAdapter adapter;
    private Inventory inventory;

    public static String EXTRA_DATE_BEGIN= "com.azore.compustore.EXTRA_DATE_BEGIN";
    public static String EXTRA_DATE_END= "com.azore.compustore.EXTRA_DATE_END";

    private  String date_begin;
    private  String date_ending;


    //***************************************************************
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ensambles_in_month);

        getSupportActionBar().setTitle("Ensambles Vendidos ");
        inventory = new Inventory(getApplicationContext());

        Intent i = getIntent();
        date_begin = i.getStringExtra(EXTRA_DATE_BEGIN);
        date_ending = i.getStringExtra(EXTRA_DATE_END);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_products);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        final List<Ensambles_vendidos_Mes> products = inventory.getListEnsamblesVendidosMes(date_begin,date_ending);
        adapter = new ProductsAdapter(products, this);
        recyclerView.setAdapter(adapter);




    }
}
