package com.azore.compustore;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.Layout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.azore.compustore.fiuady.db.Inventory;
import com.azore.compustore.fiuady.db.OrdenesUnion;
import com.azore.compustore.fiuady.db.Products;

import java.util.Calendar;
import java.util.List;

public class reportes_simulador extends AppCompatActivity implements SearchView.OnQueryTextListener  {
    // ****************************************INICIO DE RECYCLER*****************************************************

    private class OrdenesUnionHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView txtId;
        private TextView txtName;
        private TextView txtStatus;
        private TextView txtCost;
        private TextView txtDate;
        private LinearLayout layout;

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
            layout = (LinearLayout)itemView.findViewById(R.id.LinearOrder);






        }

        public void bindCategories(OrdenesUnion ordenesUnion){
            txtId.setText(String.valueOf(ordenesUnion.getId()));
            txtName.setText(ordenesUnion.getLast_name() + " " + ordenesUnion.getFirst_name());
            txtStatus.setText(ordenesUnion.getStatus_description());
            txtCost.setText(Double.toString((ordenesUnion.getCosto()/100)));


            // Double.toString((double) product.getPrice()/100)
            //   Date cDate = new Date();
            //  String fDate = new SimpleDateFormat("yyyy-MM-dd").format(cDate);

            txtDate.setText(ordenesUnion.getDate());


        }


        @Override
        public void onClick(View v) {
            int position = getAdapterPosition() ;
            OrdenesUnion ordenesUnion=this.ordenesUnions.get(position);



            Intent intent = new Intent(getApplicationContext(), Missing_Products_Of_Order.class);
            intent.putExtra(Missing_Products_Of_Order.EXTRA_ORDER_ID, String.valueOf(ordenesUnion.getId()));
            startActivity(intent);


        }

    }


    private  class OrdenesUnionAdapter extends  RecyclerView.Adapter<reportes_simulador.OrdenesUnionHolder>{
        private List<OrdenesUnion> ordenesUnions;
        Context context;


        public OrdenesUnionAdapter(List<OrdenesUnion> ordenesUnions, Context context){
            this.ordenesUnions=ordenesUnions;
            this.context=context;
        }


        @Override
        public reportes_simulador.OrdenesUnionHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = getLayoutInflater().inflate(R.layout.ordenesunion_list_item,parent,false);
            return new reportes_simulador.OrdenesUnionHolder(view, context, ordenesUnions);
        }

        @Override
        public void onBindViewHolder(reportes_simulador.OrdenesUnionHolder holder, int position) {
            holder.bindCategories(ordenesUnions.get(position));

            List<Products> productosfaltantes=inventory.getMissing_products_of_order(ordenesUnions.get(position).getId());
            List<Products> productosdelaorden=inventory.getproductsofordervalidation(ordenesUnions.get(position).getId());
            if (productosfaltantes.isEmpty()){
                holder.layout.setBackgroundColor(Color.GREEN);
            }
            else if(productosdelaorden.size()!= productosfaltantes.size()){
                holder.layout.setBackgroundColor(Color.YELLOW);
            }
            else {
                holder.layout.setBackgroundColor(Color.RED);
            }


        }

        @Override
        public int getItemCount() {
            return ordenesUnions.size();
        }
    }

    // ****************************************END  DE RECYCLER*****************************************************


    // **************************************** VARIABLES ****************************************************


    private RecyclerView recyclerView;
    private reportes_simulador.OrdenesUnionAdapter adapter;
    private Inventory inventory;
    private final int request_code=0;

    private CheckBox checkBox_inicial;
    private CheckBox checkBox_final;
    private LinearLayout linear_filterDate;

    private int dia, mes, anio;
    private  TextView txt_date_initial;
    private  TextView txt_date_final;
    private DatePickerDialog datePickerDialog;
    private  String date_begin;
    private  String date_End;
    private  String tag,monat,jahr;
    private ImageButton btn_filter_date;
    private static String string_date_begin="1000-01-01";
    private static String string_date_End="3000-12-12";
    private EditText monto_editext;
    private CheckBox chkMonto;

    private String customer ="";


    // ******************************************************************************************************

    // **************************************** ON CREATE  ****************************************************

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reportes_simulador);
        //Cambiar texto de App bar
        getSupportActionBar().setTitle("Simulador");




        inventory = new Inventory(getApplicationContext());
        inventory.drop1();
        inventory.drop2();
        inventory.crear_tabla_2();
        inventory.create_actual_temporal_stock();

        checkBox_inicial= (CheckBox)findViewById(R.id.checkBox_date_initial);
        checkBox_final= (CheckBox)findViewById(R.id.checkBox_date_end);
        linear_filterDate= (LinearLayout)findViewById(R.id.linear_date_ordenes);
        txt_date_initial= (TextView)findViewById(R.id.txt_Date_beging);
        txt_date_final= (TextView)findViewById(R.id.txt_Date_end);
        btn_filter_date= (ImageButton) findViewById(R.id.btn_filter_date_order);
        monto_editext = (EditText) findViewById(R.id.monto_text);
        chkMonto = (CheckBox) findViewById(R.id.monto_check);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_orders_simulator);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        final List<OrdenesUnion> ordenes_union = inventory.getOrdersUnionPendientes();

        for (OrdenesUnion orden : ordenes_union){
            inventory.insert_products(orden.getId());
        }

        adapter = new reportes_simulador.OrdenesUnionAdapter(ordenes_union, this);
        recyclerView.setAdapter(adapter);


        btn_filter_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Monto = String.valueOf(Double.parseDouble(monto_editext.getText().toString())*100);
                Toast.makeText(getApplicationContext(),"Filtrado",Toast.LENGTH_SHORT).show();
                if (checkBox_inicial.isChecked() && checkBox_final.isChecked()) {
                    if(chkMonto.isChecked()) {
                        List<OrdenesUnion> ordenes_union = inventory.getOrderFilterDate(date_begin, date_End, "0", " AND sum(c.qty * p.price* ap.qty)=" + Monto,customer);
                        adapter = new reportes_simulador.OrdenesUnionAdapter(ordenes_union, getApplicationContext());
                        recyclerView.setAdapter(adapter);
                    }
                    else {
                        List<OrdenesUnion> ordenes_union = inventory.getOrderFilterDate(date_begin, date_End, "0", "",customer);
                        adapter = new reportes_simulador.OrdenesUnionAdapter(ordenes_union, getApplicationContext());
                        recyclerView.setAdapter(adapter);
                    }
//
                }
                else if ( checkBox_final.isChecked()==true && checkBox_inicial.isChecked()==false)
                {
                    if(chkMonto.isChecked()) {
                    List<OrdenesUnion> ordenes_union = inventory.getOrderFilterDate(string_date_begin, date_End, "0"," AND sum(c.qty * p.price* ap.qty)=" + Monto,customer);
                    adapter = new reportes_simulador.OrdenesUnionAdapter(ordenes_union, getApplicationContext());
                    recyclerView.setAdapter(adapter);}
                    else {
                        List<OrdenesUnion> ordenes_union = inventory.getOrderFilterDate(string_date_begin, date_End, "0","",customer);
                        adapter = new reportes_simulador.OrdenesUnionAdapter(ordenes_union, getApplicationContext());
                        recyclerView.setAdapter(adapter);
                    }
//


                }else if ( checkBox_final.isChecked()==false && checkBox_inicial.isChecked()==true)
                {
                    if(chkMonto.isChecked()) {
                        List<OrdenesUnion> ordenes_union = inventory.getOrderFilterDate(date_begin, string_date_End, "0", " AND sum(c.qty * p.price* ap.qty)=" + Monto,customer);
                        adapter = new reportes_simulador.OrdenesUnionAdapter(ordenes_union, getApplicationContext());
                        recyclerView.setAdapter(adapter);
                    }
                    else {
                        List<OrdenesUnion> ordenes_union = inventory.getOrderFilterDate(date_begin, string_date_End, "0", "",customer);
                        adapter = new reportes_simulador.OrdenesUnionAdapter(ordenes_union, getApplicationContext());
                        recyclerView.setAdapter(adapter);
                    }
//

                }
                else{

                    if(chkMonto.isChecked()) {
                        List<OrdenesUnion> ordenes_union = inventory.getOrderFilterDate(string_date_begin, string_date_End, "0", " AND sum(c.qty * p.price* ap.qty)=" + Monto,customer);
                        adapter = new reportes_simulador.OrdenesUnionAdapter(ordenes_union, getApplicationContext());
                        recyclerView.setAdapter(adapter);
                    }


                    else{
                        List<OrdenesUnion> ordenes_union = inventory.getOrderFilterDate(string_date_begin, string_date_End, "0","",customer);
                        adapter = new reportes_simulador.OrdenesUnionAdapter(ordenes_union, getApplicationContext());
                        recyclerView.setAdapter(adapter);
                    }
//

                }

            }
        });
        checkBox_inicial.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked){
                    btn_filter_date.setVisibility(View.VISIBLE);
                    linear_filterDate.setVisibility(View.VISIBLE);
                    final Calendar c= Calendar.getInstance();
                    dia=c.get(Calendar.DAY_OF_MONTH);
                    mes=c.get(Calendar.MONTH);
                    anio= c.get(Calendar.YEAR);


                    datePickerDialog = new DatePickerDialog(reportes_simulador.this, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            tag=String.valueOf(dayOfMonth);
                            monat=String.valueOf(month+1);
                            if (String.valueOf(month+1).length() == 1) {  monat= "0"+String.valueOf(month+1) ;   }
                            if (String.valueOf(dayOfMonth).length() == 1) { tag= "0"+String.valueOf(dayOfMonth) ;   }
                            jahr=String.valueOf(year);
                            date_begin=jahr+"-"+monat+"-"+tag;

                            txt_date_initial.setText(tag+"-"+monat +"-" + year);


                        }
                    }, dia, mes, anio);
                    datePickerDialog.show();

                    if(txt_date_initial.getText().toString().equals("Fecha Inicial")){
                        date_begin=string_date_begin;
                    }

                }
                else
                {
                    if (checkBox_final.isChecked()){

                    }
                    else{
                        linear_filterDate.setVisibility(View.GONE);
                    }

                }



            }
        });

        checkBox_final.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked){

                    btn_filter_date.setVisibility(View.VISIBLE);
                    linear_filterDate.setVisibility(View.VISIBLE);
                    final Calendar c= Calendar.getInstance();
                    dia=c.get(Calendar.DAY_OF_MONTH);
                    mes=c.get(Calendar.MONTH);
                    anio= c.get(Calendar.YEAR);


                    datePickerDialog = new DatePickerDialog(reportes_simulador.this, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            tag=String.valueOf(dayOfMonth);
                            monat=String.valueOf(month+1);
                            if (String.valueOf(month+1).length() == 1) {  monat= "0"+String.valueOf(month+1) ;   }
                            if (String.valueOf(dayOfMonth).length() == 1) { tag= "0"+String.valueOf(dayOfMonth) ;   }
                            jahr=String.valueOf(year);

                            date_End=jahr+"-"+monat+"-"+tag;

                            txt_date_final.setText(tag+"-"+ monat +"-" + year);

                            if(txt_date_final.getText().toString().equals("Fecha Final")){
                                date_End=string_date_End;
                            }


                        }
                    }, dia, mes, anio);

                    datePickerDialog.show();


                }
                else
                {
                    if (checkBox_inicial.isChecked()){

                    }
                    else{
                        linear_filterDate.setVisibility(View.GONE);
                        btn_filter_date.setVisibility(View.INVISIBLE);

                    }

                }



            }
        });


    } // END ON CREATE


    //Hace que aparezca el icono en el App Bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu3,menu);
        MenuItem menuItem = menu.findItem(R.id.buscar);
        SearchView searchView =  (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.buscar));
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
        final List<OrdenesUnion> ordenes = inventory.searchCustomerswithOrdersPendientes(newText);
        inventory.drop1();
        inventory.drop2();
        inventory.crear_tabla_2();
        inventory.create_actual_temporal_stock();
        customer = newText;

        for (OrdenesUnion orden : ordenes){
            inventory.insert_products(orden.getId());
        }
        adapter = new reportes_simulador.OrdenesUnionAdapter(ordenes, getApplicationContext());
        recyclerView.setAdapter(adapter);

        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        inventory.drop1();
        inventory.drop2();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

}
