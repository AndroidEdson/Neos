package com.azore.compustore;

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
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.azore.compustore.fiuady.db.CategoryProduct;
import com.azore.compustore.fiuady.db.Inventory;
import com.azore.compustore.fiuady.db.InventoryDbSchema;
import com.azore.compustore.fiuady.db.Products;

import java.util.List;

public class ProductosActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {


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


            Intent intent = new Intent(getApplicationContext(), PopUp_products.class);
            intent.putExtra(PopUp_products.EXTRA_DESCRIPTION, product.getDescription());
            intent.putExtra(PopUp_products.EXTRA_ID, Integer.toString(product.getId()));
            intent.putExtra(PopUp_products.EXTRA_QTY, Integer.toString(product.getQty()));
            intent.putExtra(PopUp_products.EXTRA_PRICE, Double.toString((double) product.getPrice()/100));
            intent.putExtra(PopUp_products.EXTRA_CATEG, String.valueOf(product.getCategory_id()));



            startActivityForResult(intent, request_code2);

        }

    }


    private  class ProductsAdapter extends  RecyclerView.Adapter<ProductosActivity.ProductsHolder>{
        private List<Products> products;
        Context context;

        public ProductsAdapter(List<Products> products, Context context){
            this.products=products;

            this.context=context;
        }


        @Override
        public ProductosActivity.ProductsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = getLayoutInflater().inflate(R.layout.product_list_item,parent,false);
            return new ProductosActivity.ProductsHolder(view,context,products);
        }

        @Override
        public void onBindViewHolder(ProductosActivity.ProductsHolder holder, int position) {
            holder.bindCategories(products.get(position));
        }

        @Override
        public int getItemCount() {
            return products.size();
        }
    }

    private RecyclerView recyclerView;
    private ProductosActivity.ProductsAdapter adapter;
    private Inventory inventory;
    private Spinner categoriesSpinner;
    public int PosicionSpinner;
    public boolean flagSpinnerTouch;
    public String search_text;



    private final int request_code=0;
    private final int request_code2=1;
    private AlertDialog dialogShow ;
    private List<Products> productos_pr;
    private final String KEY_SEARCH= "key_search";
    private final String KEY_POSITION= "key_position";





//__________________________________________________________

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productos);
        //Cambiar texto de App bar
        getSupportActionBar().setTitle("Productos");
        inventory = new Inventory(getApplicationContext());


        if(savedInstanceState!= null)
        {
            search_text= savedInstanceState.getString(KEY_SEARCH, "");
            PosicionSpinner= savedInstanceState.getInt(KEY_POSITION, 0);

        }

        recyclerView = (RecyclerView) findViewById(R.id.recycler_products);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        categoriesSpinner = (Spinner) findViewById(R.id.categories_list);


        inventory = new Inventory(getApplicationContext());

        List<Products> products = inventory.products_alfabetic();

        final ArrayAdapter<String> spinner_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item);


        // int products_max = inventory.getLastId(InventoryDbSchema.Products_Table.NAME);
       // Toast.makeText(getApplicationContext(), String.valueOf(products_max), Toast.LENGTH_SHORT).show();

        // equivalente a for each
        spinner_adapter.add("Todos");
         final List<CategoryProduct> categoriesProduct = inventory.getAllCategoriesProduct();
        for (CategoryProduct category : categoriesProduct) {
                spinner_adapter.add(category.getDescription());
        }


        categoriesSpinner.setAdapter(spinner_adapter);





        adapter = new ProductsAdapter(products, this);
        recyclerView.setAdapter(adapter);

        categoriesSpinner.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                flagSpinnerTouch=true;

                return false;
            }
        });


            categoriesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

               // int j = Integer.valueOf(spinner_adapter.getItem(position));
                //Toast.makeText(getApplicationContext(), categoriesProduct.get(position).getDescription(), Toast.LENGTH_SHORT).show();

                if (!flagSpinnerTouch) return;
                PosicionSpinner = position;
                //Toast.makeText(getApplicationContext(),"Entro ", Toast.LENGTH_SHORT).show();

                if (position==0 ) {
                    final List<Products> products = inventory.products_alfabetic();
                    adapter = new ProductosActivity.ProductsAdapter(products, getApplicationContext());
                    recyclerView.setAdapter(adapter);

                }
                else{
                    List<Products> products =inventory.categoryFilters(String.valueOf(categoriesProduct.get(position-1).getId()));
                    adapter = new ProductosActivity.ProductsAdapter(products, getApplicationContext());
                    recyclerView.setAdapter(adapter);
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
//
            }
        });



        if(search_text != null && !search_text.equals("")) {
            if (PosicionSpinner == 0) {
              //  Toast.makeText(getApplicationContext(),search_text, Toast.LENGTH_SHORT).show();

                products = inventory.searchProducts(search_text);
                adapter = new ProductsAdapter(products, getApplicationContext());
                recyclerView.setAdapter(adapter);
            } else {
                final List<CategoryProduct> categoriesProduct2 = inventory.getAllCategoriesProduct();
                products = inventory.searchProductsForCategory(search_text, categoriesProduct2.get(PosicionSpinner - 1).getId());
                adapter = new ProductsAdapter(products, getApplicationContext());
                  recyclerView.setAdapter(adapter);
            }
        }


    }


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
                // Codigo prueba

                final AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
                final View mView = getLayoutInflater().inflate(R.layout.products_add,null);
                final EditText mDescription = (EditText)mView.findViewById(R.id.etDescription);
                final EditText mPrecio = (EditText)mView.findViewById(R.id.etprecio);
                final Spinner spinner = (Spinner)mView.findViewById(R.id.SpinnerCategory);
                Button mGuardar = (Button) mView.findViewById(R.id.btnGuardar);
                Button mCancelar = (Button) mView.findViewById(R.id.btnCancelar);
                final ArrayAdapter<String> spinner_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item);


                // equivalente a for each
                final List<CategoryProduct> categoriesProduct = inventory.getAllCategoriesProduct();
                for (CategoryProduct category : categoriesProduct) {
                    spinner_adapter.add(category.getDescription());
                }


                spinner.setAdapter(spinner_adapter);

                mBuilder.setView(mView);
                dialogShow = mBuilder.create();
                dialogShow.show();




                mGuardar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if ( mDescription.getText().toString().equals("")|| mPrecio.getText().toString().equals("") )
                        {
                            Toast.makeText(getApplicationContext(), "¡Error! Campo Vacío", Toast.LENGTH_SHORT).show();
                        }
                        else {


                            if( inventory.NameValidationProducts(mDescription.getText().toString()) >= 1 )
                            {
                                Toast.makeText(getApplicationContext(), "Ya existe una categoria con ese nombre", Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_SHORT).show();
                                inventory.AddProduct(categoriesProduct.get(spinner.getSelectedItemPosition()).getId(),inventory.getLastId(InventoryDbSchema.Products_Table.NAME) + 1,mDescription.getText().toString(),(Double.parseDouble(mPrecio.getText().toString()))*100);
                                dialogShow.dismiss();
                                updateRecycler();

                            }
                        }
                    }
                });


                mCancelar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CancelarshowAlert();
                    }
                });





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
                .setTitle("Product")
                .setIcon(R.drawable.ic_shortcut_warning)
                .create();
        myAlert.show();

    }


    public void updateRecycler(){
        inventory= new Inventory(getApplicationContext());
        final List<Products> products = inventory.products_alfabetic();
        adapter= new ProductosActivity.ProductsAdapter(products,this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if ( (requestCode==request_code2 && resultCode== RESULT_OK)) {
            int aux = categoriesSpinner.getSelectedItemPosition();
            inventory = new Inventory(getApplicationContext());
            if (aux == 0) {
                final List<Products> products = inventory.products_alfabetic();
                adapter= new ProductosActivity.ProductsAdapter(products,getApplicationContext());
            } else
            {
               // products
                final List<CategoryProduct> categoriesProduct = inventory.getAllCategoriesProduct();
                final List<Products> products = inventory.categoryFilters( String.valueOf(categoriesProduct.get(aux-1).getId()));
                adapter= new ProductosActivity.ProductsAdapter(products,getApplicationContext());
                //Toast.makeText(getApplicationContext(), categoriesProduct.get(aux+1).getDescription() , Toast.LENGTH_SHORT).show();
            }
            recyclerView.setAdapter(adapter);

        }

        else {}
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
        search_text=newText;

        if(newText != null) {
            if(PosicionSpinner == 0){
                final List<Products> products = inventory.searchProducts(newText);
                adapter = new ProductosActivity.ProductsAdapter(products, getApplicationContext());
                recyclerView.setAdapter(adapter);
            }
            else{
                final List<CategoryProduct> categoriesProduct = inventory.getAllCategoriesProduct();
                final List<Products> products = inventory.searchProductsForCategory(newText,categoriesProduct.get(PosicionSpinner-1).getId());
                adapter = new ProductosActivity.ProductsAdapter(products, getApplicationContext());
                recyclerView.setAdapter(adapter);
            }

        }

        return false;
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(KEY_SEARCH,search_text);
        outState.putInt(KEY_POSITION,PosicionSpinner);



    }

    }
