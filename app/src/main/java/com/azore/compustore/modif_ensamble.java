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

import com.azore.compustore.fiuady.db.AssemblieOrders_Union;
import com.azore.compustore.fiuady.db.Inventory;
import com.azore.compustore.fiuady.db.InventoryDbSchema;
import com.azore.compustore.fiuady.db.Products;

import java.util.ArrayList;
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

            id_product= String.valueOf(product.getId());
            name_product= product.getDescription();

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

        public void clear() {
            int size = this.products.size();
            if (size > 0) {
                for (int i = 0; i < size; i++) {
                    this.products.remove(0);
                }
                this.notifyItemRangeRemoved(0, size);
            }
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
    int requestcode3=0;

    private String name_description;
    private String id;
    private String id_product;
    private String name_product;
    private List<String> new_products;
    private List<String> qty_original;


    private TextView txt_change;

    public static String EXTRA_DESCRIPTION_ENSAMBLE_MODIF = "com.azore.compustore.id.add.assemblies.description_ensamble.modif";
    public static String EXTRA_ID_ENSAMBLE_MODIF = "com.azore.compustore.id.add.assemblies.id_ensamble.modif";

    private static  String KEY_ID="key_id";
    private static  String KEY_NAME="key_name";

    private static  String KEY_ID_ORIGINAL="key_id_original";
    private static  String KEY_QTY_ORIGINAL="key_qty";

    private  int list_id[];
    private  int list_category_id[];
    private  String list_description[];
    int list_price[];
    private  int list_qty[];
    private List<Products> list_original;

    int category_id;
    String description;
    int price;
    int qty;

    //****************************************************************************
    //****************************************************************************
    //*********************************ON CREATE *******************************************


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_assemblies);

        new_products= new ArrayList<String>();
        qty_original= new ArrayList<String>();

        Intent i = getIntent();
        name_description= i.getStringExtra(EXTRA_DESCRIPTION_ENSAMBLE_MODIF);
        id=i.getStringExtra(EXTRA_ID_ENSAMBLE_MODIF);

        if(savedInstanceState!= null)
        {
            id= savedInstanceState.getString(KEY_ID, "");
            name_description= savedInstanceState.getString(KEY_NAME, "");
            //   original_name= savedInstanceState.getString(KEY_NAME, "");
        }

        getSupportActionBar().setTitle(name_description);

        inventory = new Inventory(getApplicationContext());
        recyclerView = (RecyclerView) findViewById(R.id.recycler_products_assemblies);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        btn_save= (Button) findViewById(R.id.btnGuardar);
        btn_cancel= (Button) findViewById(R.id.btnCancelar);
        new_description= (EditText)findViewById(R.id.edit_new_assembli_description);

        new_description.setText(null);
        new_description.setHint("Nueva descripción");
        new_description.setText(name_description);

        List<Products> products =inventory.getProductsAssembly(id);
        list_original =inventory.getProductsAssembly(id);


        list_id= new int[list_original.size()];
        list_qty= new int[list_original.size()];
        list_description= new String[list_original.size()];
        list_price= new int[list_original.size()];
        list_category_id= new int[list_original.size()];

        for (int j=0; j<products.size(); j++)
        {
            list_id[j]=list_original.get(j).getId();
            list_category_id[j]=list_original.get(j).getCategory_id();
            list_description[j]=list_original.get(j).getDescription();
            list_price[j]=list_original.get(j).getPrice();
            list_qty[j]=list_original.get(j).getQty();

            new_products.add(String.valueOf(list_original.get(j).getId()));
            qty_original.add(String.valueOf(list_original.get(j).getQty()));

        }



        if(savedInstanceState!= null) {
            list_id= savedInstanceState.getIntArray(KEY_ID_ORIGINAL);
            list_qty= savedInstanceState.getIntArray(KEY_QTY_ORIGINAL);

            for(int k=0; k<list_id.length; k++) {
              //  Products aux= new Products(list_id[k],list_category_id[k],list_description[k],list_price[k],list_qty[k]);
              //  list_original.set(k,aux);
                new_products.set(k,String.valueOf(list_id[k]));
                qty_original.set(k,String.valueOf(list_qty[k]));

            }
            //Toast.makeText(getApplicationContext(),"Entró", Toast.LENGTH_SHORT).show();
        }



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

                    if(name_description.equals(new_description.getText().toString()))
                    {
                        List<Products> products =inventory.getProductsAssembly(id);
                        if (products.size()==0){
                            Toast.makeText(getApplicationContext(), "Ensamble Vacío, Ingrese un producto!", Toast.LENGTH_SHORT).show();
                        }else {
                            inventory.updateAssemblies(id, new_description.getText().toString());
                            Intent intent_back = new Intent();
                            setResult(RESULT_OK, intent_back);
                            Toast.makeText(getApplicationContext(), "Ensamble Modificado", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }else{

                        if (aux >= 1) {
                            Toast.makeText(getApplicationContext(),"Ya existe un ensamble con esa descripción",Toast.LENGTH_SHORT).show();

                        }else {

                            List<Products> products =inventory.getProductsAssembly(id);
                            if (products.size()==0){
                                Toast.makeText(getApplicationContext(), "Ensamble Vacío, Ingrese un producto!", Toast.LENGTH_SHORT).show();
                            }else {
                                inventory.updateAssemblies(id, new_description.getText().toString());
                                Intent intent_back = new Intent();
                                setResult(RESULT_OK, intent_back);
                                Toast.makeText(getApplicationContext(), "Ensamble Modificado", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        }

                    }


                }

            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                inventory.deleteAllProductsFromAssembly(id);

                for (int i = 0; i < new_products.size(); i++) {
                    if (new_products.get(i) != null) {
                        inventory.AddAssemblyProductQty(id, new_products.get(i),  qty_original.get(i));
                    }
                }

                finish();
                Toast.makeText(getApplicationContext(), "No se realizaron modificaciones", Toast.LENGTH_SHORT).show();

            }
        });

    } // END ONCREATE

    //****************************************************************************
    //****************************************************************************
    //************************************END ONCREATE****************************************
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(KEY_ID,id);
        outState.putString(KEY_NAME,name_description);

        outState.putIntArray(KEY_ID_ORIGINAL,list_id );
        outState.putIntArray(KEY_QTY_ORIGINAL,list_qty );



    }
    @Override
    public void onBackPressed() {

        inventory.deleteAllProductsFromAssembly(id);

        for (int i = 0; i < new_products.size(); i++) {
            if (new_products.get(i) != null) {
                inventory.AddAssemblyProductQty(id, new_products.get(i),  qty_original.get(i));
            }
        }

        finish();
        Toast.makeText(getApplicationContext(), "No se realizaron modificaciones", Toast.LENGTH_SHORT).show();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if ((requestCode == request_code2) && (resultCode == RESULT_OK)) {

        //    new_products.add(data.getDataString());
          //  Toast.makeText(getApplicationContext(),new_products.get(new_products.size()), Toast.LENGTH_LONG).show();

        }

            List<Products> products = inventory.getProductsAssembly(id);
            adapter.clear();
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

                Intent intent = new Intent(getApplicationContext(), Add_Product_to_Ensamble.class);
                intent.putExtra(Add_Product_to_Ensamble.EXTRA_ID_PRODUCT,id_product);
                intent.putExtra(Add_Product_to_Ensamble.EXTRA_DESCRIPTION_PRODUCT,name_product);

                intent.putExtra(Add_Product_to_Ensamble.EXTRA_DESCRIPTION_ASSEMBLY, name_description);
                intent.putExtra(Add_Product_to_Ensamble.EXTRA_ID_ASSEMBLY, id);
                startActivityForResult(intent, requestcode3);



                return true;
            // Codigo prueba

        }
        return super.onOptionsItemSelected(item);

    }



}// FINAL DEL COSMOS
