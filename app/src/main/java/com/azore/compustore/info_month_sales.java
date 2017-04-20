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
import com.azore.compustore.fiuady.db.OrdenesUnion;

import java.util.List;

public class info_month_sales extends AppCompatActivity {

    // ****************************************INICIO DE RECYCLER*****************************************************

    private class OrdenesUnionHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView txtId;
        private TextView txtName;
        private TextView txtStatus;
        private TextView txtCost;
        private TextView txtDate;

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





        }

        public void bindCategories(OrdenesUnion ordenesUnion){
            txtId.setText(String.valueOf(ordenesUnion.getId()));
            txtName.setText(ordenesUnion.getLast_name() + " " + ordenesUnion.getFirst_name());
            txtStatus.setText(ordenesUnion.getStatus_description());
            txtCost.setText(Double.toString(( ordenesUnion.getCosto()/100)));

            // Double.toString((double) product.getPrice()/100)
            //   Date cDate = new Date();
            //  String fDate = new SimpleDateFormat("yyyy-MM-dd").format(cDate);

            txtDate.setText(ordenesUnion.getDate());


        }


        @Override
        public void onClick(View v) {
            int position = getAdapterPosition() ;
            OrdenesUnion ordenesUnion=this.ordenesUnions.get(position);



         //   Intent intent = new Intent(getApplicationContext(), Pop_Up_ordenes_N1.class);
         //   intent.putExtra(Pop_Up_ordenes_N1.EXTRA_ORDER_ID, String.valueOf(ordenesUnion.getId()));
         //    startActivityForResult(intent, request_code);


        }

    }


    private  class OrdenesUnionAdapter extends  RecyclerView.Adapter<OrdenesUnionHolder>{
        private List<OrdenesUnion> ordenesUnions;
        Context context;

        public OrdenesUnionAdapter(List<OrdenesUnion> ordenesUnions, Context context){
            this.ordenesUnions=ordenesUnions;
            this.context=context;
        }


        @Override
        public OrdenesUnionHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = getLayoutInflater().inflate(R.layout.ordenesunion_list_item,parent,false);
            return new OrdenesUnionHolder(view, context, ordenesUnions);
        }

        @Override
        public void onBindViewHolder(OrdenesUnionHolder holder, int position) {
            holder.bindCategories(ordenesUnions.get(position));

        }

        @Override
        public int getItemCount() {
            return ordenesUnions.size();
        }
    }

    // ****************************************END  DE RECYCLER*****************************************************


    // **************************************** VARIABLES ****************************************************


    private RecyclerView recyclerView;
    private OrdenesUnionAdapter adapter;
    private Inventory inventory;
    private final int request_code=0;

    public static String EXTRA_DATE_BEGIN= "com.azore.compustore.EXTRA_DATE_BEGIN";
    public static String EXTRA_DATE_END= "com.azore.compustore.EXTRA_DATE_END";

    private  String date_begin;
    private  String date_ending;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_month_sales);

        Intent i = getIntent();
        date_begin = i.getStringExtra(EXTRA_DATE_BEGIN);
        date_ending = i.getStringExtra(EXTRA_DATE_END);

        getSupportActionBar().setTitle("Ordenes en el mes");

        recyclerView = (RecyclerView) findViewById(R.id.recycler_orders);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        inventory = new Inventory(getApplicationContext());


        final List<OrdenesUnion> ordenes_union = inventory.getSalesMonthOrdersUnion(date_begin,date_ending);
        adapter = new OrdenesUnionAdapter(ordenes_union, this);
        recyclerView.setAdapter(adapter);

    }
}
