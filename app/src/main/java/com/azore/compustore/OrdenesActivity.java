package com.azore.compustore;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.azore.compustore.fiuady.db.Assemblies;
import com.azore.compustore.fiuady.db.CategoryProduct;
import com.azore.compustore.fiuady.db.Inventory;
import com.azore.compustore.fiuady.db.OrdenesUnion;
import com.azore.compustore.fiuady.db.Order_Status;
import com.azore.compustore.fiuady.db.Orders;
import com.azore.compustore.fiuady.db.Products;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class OrdenesActivity extends AppCompatActivity implements SearchView.OnQueryTextListener   {

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



            Intent intent = new Intent(getApplicationContext(), Pop_Up_ordenes_N1.class);
            intent.putExtra(Pop_Up_ordenes_N1.EXTRA_ORDER_ID, String.valueOf(ordenesUnion.getId()));
            startActivityForResult(intent, request_code);


        }

    }


    private  class OrdenesUnionAdapter extends  RecyclerView.Adapter< OrdenesUnionHolder >{
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
    private AlertDialog dialogShow ;
    private final int request_code=0;

    private Spinner categoriesSpinner;
    public int PosicionSpinner=0;
    private CheckBox checkBox_inicial;
    private CheckBox checkBox_final;
    private LinearLayout linear_filterDate;

    private int dia, mes, anio;
    private  TextView txt_date_initial;
    private  TextView txt_date_final;
    private  DatePickerDialog datePickerDialog;
    private  String date_begin;
    private  String date_End;
    private  String tag,monat,jahr;
    private ImageButton btn_filter_date;
    private static String string_date_begin="1000-01-01";
    private static String string_date_End="3000-12-12";

    // ******************************************************************************************************

    // **************************************** ON CREATE  ****************************************************

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ordenes);
        //Cambiar texto de App bar
        getSupportActionBar().setTitle("Ordenes");

        recyclerView = (RecyclerView) findViewById(R.id.recycler_orders);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        categoriesSpinner = (Spinner) findViewById(R.id.spinner_ordenes);
        checkBox_inicial= (CheckBox)findViewById(R.id.checkBox_date_initial);
        checkBox_final= (CheckBox)findViewById(R.id.checkBox_date_end);
        linear_filterDate= (LinearLayout)findViewById(R.id.linear_date_ordenes);
        txt_date_initial= (TextView)findViewById(R.id.txt_Date_beging);
        txt_date_final= (TextView)findViewById(R.id.txt_Date_end);
        btn_filter_date= (ImageButton) findViewById(R.id.btn_filter_date_order);
        inventory = new Inventory(getApplicationContext());


        //LA PRIMERA VEZ QUE LO CORRAS COMPILA TODOS LOS UPDATES PARA CAMBIAR EL FORMATO DE LA FECHA
      //  inventory.updateOrderDate("2016-10-05","0");
      //  inventory.updateOrderDate("2016-11-12","1");
      //  inventory.updateOrderDate("2016-12-26","2");
      //  inventory.updateOrderDate("2017-01-03","3");
      //  inventory.updateOrderDate("2017-01-15","4");
      //  inventory.updateOrderDate("2017-02-04","5");
      //  inventory.updateOrderDate("2017-03-05","6");
      //  inventory.updateOrderDate("2017-03-12","7");
      //  inventory.updateOrderDate("2017-03-18","8");


      //  String orders = inventory.getOneOrderTable(String.valueOf(8));
      //  Toast.makeText(getApplicationContext(), orders  , Toast.LENGTH_LONG).show();

        final List<OrdenesUnion> ordenes_union = inventory.getOrdersUnion();
        adapter = new OrdenesUnionAdapter(ordenes_union, this);
        recyclerView.setAdapter(adapter);
       // datePickerDialog.updateDate(2017,04,01);

        final List<Order_Status> ordes_status_list = inventory.getAllOrderStatus();

        final ArrayAdapter<String> spinner_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item);


       spinner_adapter.add("Todos");
     final List<Order_Status> order_statuses = inventory.getAllOrderStatus();
     for (Order_Status order_status : order_statuses) {
         spinner_adapter.add( order_status.getDescription());
     }
//
        categoriesSpinner.setAdapter(spinner_adapter);

        btn_filter_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (checkBox_inicial.isChecked() && checkBox_final.isChecked()) {

                    if (PosicionSpinner == 0) {

                        final List<OrdenesUnion> ordenes_union = inventory.getOrderFilterDateAll(date_begin, date_End);
                        adapter = new OrdenesUnionAdapter(ordenes_union, getApplicationContext());
                        recyclerView.setAdapter(adapter);
//
                    } else {

                        List<OrdenesUnion> ordenes_union = inventory.getOrderFilterDate(date_begin, date_End, String.valueOf(ordes_status_list.get(PosicionSpinner - 1).getId()),"","");
                        adapter = new OrdenesUnionAdapter(ordenes_union, getApplicationContext());
                        recyclerView.setAdapter(adapter);
//
                    }

                }
                else if ( checkBox_final.isChecked()==true && checkBox_inicial.isChecked()==false)
                {
                    if (PosicionSpinner == 0) {

                        final List<OrdenesUnion> ordenes_union = inventory.getOrderFilterDateAll(string_date_begin, date_End);
                        adapter = new OrdenesUnionAdapter(ordenes_union, getApplicationContext());
                        recyclerView.setAdapter(adapter);
//
                    } else {

                        List<OrdenesUnion> ordenes_union = inventory.getOrderFilterDate(string_date_begin, date_End, String.valueOf(ordes_status_list.get(PosicionSpinner - 1).getId()),"","");
                        adapter = new OrdenesUnionAdapter(ordenes_union, getApplicationContext());
                        recyclerView.setAdapter(adapter);
//
                    }

                }else if ( checkBox_final.isChecked()==false && checkBox_inicial.isChecked()==true)
                {
                    if (PosicionSpinner == 0) {

                        final List<OrdenesUnion> ordenes_union = inventory.getOrderFilterDateAll(date_begin, string_date_End);
                        adapter = new OrdenesUnionAdapter(ordenes_union, getApplicationContext());
                        recyclerView.setAdapter(adapter);
//
                    } else {

                        List<OrdenesUnion> ordenes_union = inventory.getOrderFilterDate(date_begin, string_date_End, String.valueOf(ordes_status_list.get(PosicionSpinner - 1).getId()),"","");
                        adapter = new OrdenesUnionAdapter(ordenes_union, getApplicationContext());
                        recyclerView.setAdapter(adapter);
//
                    }

                }
                else{

                    if (PosicionSpinner==0 ) {
//
                        final List<OrdenesUnion> ordenes_union = inventory.getOrdersUnion();
                        adapter = new OrdenesUnionAdapter(ordenes_union, getApplicationContext());
                        recyclerView.setAdapter(adapter);
//
                    }
                    else{
//
                        List<OrdenesUnion> ordenes_union =inventory.getFiltersOrder(String.valueOf(ordes_status_list.get(PosicionSpinner-1).getId()));
                        adapter = new OrdenesUnionAdapter (ordenes_union, getApplicationContext());
                        recyclerView.setAdapter(adapter);
//
                    }

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


                 datePickerDialog = new DatePickerDialog(OrdenesActivity.this, new DatePickerDialog.OnDateSetListener() {
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
                       btn_filter_date.setVisibility(View.INVISIBLE);
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


                    datePickerDialog = new DatePickerDialog(OrdenesActivity.this, new DatePickerDialog.OnDateSetListener() {
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



        categoriesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

               //  int j = Integer.valueOf(spinner_adapter.getItem(position));
                //Toast.makeText(getApplicationContext(), categoriesProduct.get(position).getDescription(), Toast.LENGTH_SHORT).show();

              PosicionSpinner = position;
              if (position==0 ) {
//
                  final List<OrdenesUnion> ordenes_union = inventory.getOrdersUnion();
                  adapter = new OrdenesUnionAdapter(ordenes_union, getApplicationContext());
                  recyclerView.setAdapter(adapter);
//
              }
              else{
//
                  List<OrdenesUnion> ordenes_union =inventory.getFiltersOrder(String.valueOf(ordes_status_list.get(position-1).getId()));
                  adapter = new OrdenesUnionAdapter (ordenes_union, getApplicationContext());
                  recyclerView.setAdapter(adapter);
//
              }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
//
            }
        });




    } // END ON CREATE


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
                Intent i = new Intent(getApplicationContext(),add_order.class);
                startActivityForResult(i, request_code);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public void CancelarshowAlert( ){
        AlertDialog.Builder myAlert= new AlertDialog.Builder(this);
        myAlert.setMessage("¿Seguro que quieres cancelar?")
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                    }
                })
                .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialogShow.dismiss();

                    }
                })
                .setTitle("Customer")
                .setIcon(R.drawable.ic_shortcut_warning)
                .create();
        myAlert.show();

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
        final List<OrdenesUnion> ordenes = inventory.searchCustomerswithOrders(newText);
        adapter = new OrdenesActivity.OrdenesUnionAdapter(ordenes, getApplicationContext());
        recyclerView.setAdapter(adapter);
        return false;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if ( (requestCode==request_code && resultCode== RESULT_OK)){
            final List<Order_Status> ordes_status_list = inventory.getAllOrderStatus();

            if (checkBox_inicial.isChecked() && checkBox_final.isChecked()) {

                if (PosicionSpinner == 0) {

                    final List<OrdenesUnion> ordenes_union = inventory.getOrderFilterDateAll(date_begin, date_End);
                    adapter = new OrdenesUnionAdapter(ordenes_union, getApplicationContext());
                    recyclerView.setAdapter(adapter);
//
                } else {

                    List<OrdenesUnion> ordenes_union = inventory.getOrderFilterDate(date_begin, date_End, String.valueOf(ordes_status_list.get(PosicionSpinner - 1).getId()),"","");
                    adapter = new OrdenesUnionAdapter(ordenes_union, getApplicationContext());
                    recyclerView.setAdapter(adapter);
//
                }

            }
            else if ( checkBox_final.isChecked()==true && checkBox_inicial.isChecked()==false)
            {
                if (PosicionSpinner == 0) {

                    final List<OrdenesUnion> ordenes_union = inventory.getOrderFilterDateAll(string_date_begin, date_End);
                    adapter = new OrdenesUnionAdapter(ordenes_union, getApplicationContext());
                    recyclerView.setAdapter(adapter);
//
                } else {

                    List<OrdenesUnion> ordenes_union = inventory.getOrderFilterDate(string_date_begin, date_End, String.valueOf(ordes_status_list.get(PosicionSpinner - 1).getId()),"","");
                    adapter = new OrdenesUnionAdapter(ordenes_union, getApplicationContext());
                    recyclerView.setAdapter(adapter);
//
                }

            }else if ( checkBox_final.isChecked()==false && checkBox_inicial.isChecked()==true)
            {
                if (PosicionSpinner == 0) {

                    final List<OrdenesUnion> ordenes_union = inventory.getOrderFilterDateAll(date_begin, string_date_End);
                    adapter = new OrdenesUnionAdapter(ordenes_union, getApplicationContext());
                    recyclerView.setAdapter(adapter);
//
                } else {

                    List<OrdenesUnion> ordenes_union = inventory.getOrderFilterDate(date_begin, string_date_End, String.valueOf(ordes_status_list.get(PosicionSpinner - 1).getId()),"","");
                    adapter = new OrdenesUnionAdapter(ordenes_union, getApplicationContext());
                    recyclerView.setAdapter(adapter);
//
                }

            }
            else{

                if (PosicionSpinner==0 ) {
//
                    final List<OrdenesUnion> ordenes_union = inventory.getOrdersUnion();
                    adapter = new OrdenesUnionAdapter(ordenes_union, getApplicationContext());
                    recyclerView.setAdapter(adapter);
//
                }
                else{
//
                    List<OrdenesUnion> ordenes_union =inventory.getFiltersOrder(String.valueOf(ordes_status_list.get(PosicionSpinner-1).getId()));
                    adapter = new OrdenesUnionAdapter (ordenes_union, getApplicationContext());
                    recyclerView.setAdapter(adapter);
//
                }

            } // TERMINA REQUEST


        }
    }
}
