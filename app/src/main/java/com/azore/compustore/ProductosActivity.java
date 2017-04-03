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
import android.view.Menu;
import android.view.MenuItem;
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
import com.azore.compustore.fiuady.db.Products;

import java.util.List;

public class ProductosActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {


    private class ProductsHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView txtDescription;
        private TextView txtPrice;

        Context context;
        private List<Products> Products;

        public ProductsHolder(View itemView, Context context, List<Products> Products){

            super(itemView);
            this.context= context;
            this.Products= Products;
            itemView.setOnClickListener(this);
            txtDescription= (TextView) itemView.findViewById(R.id.txt_product_description);
            txtPrice= (TextView) itemView.findViewById(R.id.txt_product_price);


        }

        public void bindCategories(Products product){
            txtDescription.setText(product.getDescription());
            txtPrice.setText(Integer.toString(product.getPrice())) ;
        }


        @Override
        public void onClick(View v) {
            int position = getAdapterPosition() ;
            Products product=this.Products.get(position);


            Intent intent = new Intent(getApplicationContext(), Pop.class);
            intent.putExtra(Pop.EXTRA_DESCRIPTION, product.getDescription());
            intent.putExtra(Pop.EXTRA_ID, Integer.toString(product.getId()));

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



    private final int request_code=0;
    private final int request_code2=1;
    private AlertDialog dialogShow ;
    public int PosicionSpinner;



//__________________________________________________________

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productos);
        //Cambiar texto de App bar
        getSupportActionBar().setTitle("Productos");
        inventory = new Inventory(getApplicationContext());


        recyclerView = (RecyclerView) findViewById(R.id.recycler_products);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        categoriesSpinner = (Spinner) findViewById(R.id.spinner_products);


        inventory = new Inventory(getApplicationContext());

        final List<Products> products = inventory.products_alfabetic();

        final ArrayAdapter<String> spinner_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item);


        // equivalente a for each
        spinner_adapter.add("Todos");
        final List<CategoryProduct> categoriesProduct = inventory.getAllCategoriesProduct();
        for (CategoryProduct category : categoriesProduct) {
                spinner_adapter.add(category.getDescription());
        }


        categoriesSpinner.setAdapter(spinner_adapter);

        adapter = new ProductosActivity.ProductsAdapter(products, this);
        recyclerView.setAdapter(adapter);


        categoriesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

               // int j = Integer.valueOf(spinner_adapter.getItem(position));
                //Toast.makeText(getApplicationContext(), categoriesProduct.get(position).getDescription(), Toast.LENGTH_SHORT).show();

                PosicionSpinner = position;
                if (position==0 ) {

                    final List<Products> products = inventory.products_alfabetic();
                    adapter = new ProductosActivity.ProductsAdapter(products, getApplicationContext());
                    recyclerView.setAdapter(adapter);

                }
                else{

                    final List<Products> products =inventory.categoryFilters(String.valueOf(categoriesProduct.get(position-1).getId()));
                    adapter = new ProductosActivity.ProductsAdapter(products, getApplicationContext());
                    recyclerView.setAdapter(adapter);


                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
//
            }
        });


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
                final View mView = getLayoutInflater().inflate(R.layout.dialog_add,null);
                final EditText mId = (EditText)mView.findViewById(R.id.etId);
                final EditText mName = (EditText)mView.findViewById(R.id.etName);
                Button mGuardar = (Button) mView.findViewById(R.id.btnGuardar);
                Button mCancelar = (Button) mView.findViewById(R.id.btnCancelar);

                mBuilder.setView(mView);
                dialogShow = mBuilder.create();
                dialogShow.show();
                mGuardar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if ( mId.getText().toString().equals("") || mName.getText().toString().equals("") )
                        {
                            Toast.makeText(getApplicationContext(), "¡Error! Campos Vacíos", Toast.LENGTH_SHORT).show();
                        }
                        else {


                            if( inventory.NameValidation(mName.getText().toString()) >= 1 )
                            {
                                Toast.makeText(getApplicationContext(), "Ya existe una categoria con ese nombre", Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_SHORT).show();
                                inventory.AddCategory(Integer.parseInt(mId.getText().toString()), mName.getText().toString());
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
                .setTitle("Product Categories")
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

        if ((requestCode==request_code && resultCode== RESULT_OK) || (requestCode==request_code2 && resultCode== RESULT_OK))
        {


            inventory= new Inventory(getApplicationContext());
            final List<Products> products = inventory.products_alfabetic();
            adapter= new ProductosActivity.ProductsAdapter(products,this);
            recyclerView.setAdapter(adapter);

        }
        else
        {
            //Toast.makeText(getApplicationContext(), "No OK :(", Toast.LENGTH_SHORT).show();

        }



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

    }
