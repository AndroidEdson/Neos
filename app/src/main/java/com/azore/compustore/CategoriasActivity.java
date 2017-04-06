package com.azore.compustore;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.azore.compustore.fiuady.db.*;

import java.util.List;

public class CategoriasActivity extends AppCompatActivity   {

    private class CategoryHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView txtDescription;
        Context context;
        private List<CategoryProduct> categoryProducts;

        public CategoryHolder(View itemView, Context context, List<CategoryProduct> categoryProducts){

            super(itemView);
            this.context= context;
            this.categoryProducts= categoryProducts;
            itemView.setOnClickListener(this);
            txtDescription= (TextView) itemView.findViewById(R.id.txt_categories_description);

        }

        public void bindCategories(CategoryProduct categoriesProduct){
            txtDescription.setText(categoriesProduct.getDescription());
        }


        @Override
        public void onClick(View v) {
            int position = getAdapterPosition() ;
            CategoryProduct categoryProduct=this.categoryProducts.get(position);


          Intent intent = new Intent(getApplicationContext(), Pop.class);
          intent.putExtra(Pop.EXTRA_DESCRIPTION, categoryProduct.getDescription());
          intent.putExtra(Pop.EXTRA_ID, Integer.toString(categoryProduct.getId()));

          startActivityForResult(intent, request_code2);


        }

    }


    private  class CategoriesAdapter extends  RecyclerView.Adapter<CategoryHolder>{
        private List<CategoryProduct> categories_product;
        Context context;

        public CategoriesAdapter(List<CategoryProduct> categories_product, Context context){
            this.categories_product=categories_product;
            this.context=context;
        }


        @Override
        public CategoryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = getLayoutInflater().inflate(R.layout.categories_list_item,parent,false);
            return new CategoryHolder(view,context,categories_product);
        }

        @Override
        public void onBindViewHolder(CategoryHolder holder, int position) {
            holder.bindCategories(categories_product.get(position));
        }

        @Override
        public int getItemCount() {
            return categories_product.size();
        }
    }

    private RecyclerView recyclerView;
    private CategoriesAdapter adapter;
    private Inventory inventory;



    private final int request_code=0;
    private final int request_code2=1;
    private AlertDialog dialogShow ;



//__________________________________________________________

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categorias);
        //Cambiar texto de App bar
        getSupportActionBar().setTitle("Categorías");
        inventory= new Inventory(getApplicationContext());


        recyclerView=(RecyclerView) findViewById(R.id.recycler_categories);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));



        inventory= new Inventory(getApplicationContext());

        final List<CategoryProduct> categories = inventory.category_alfabetic();



        adapter= new CategoriesAdapter(categories,this);
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

                final AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
                final View mView = getLayoutInflater().inflate(R.layout.category_add,null);
                final EditText mName = (EditText)mView.findViewById(R.id.etName);
                Button mGuardar = (Button) mView.findViewById(R.id.btnGuardar);
                Button mCancelar = (Button) mView.findViewById(R.id.btnCancelar);

                mBuilder.setView(mView);
                dialogShow = mBuilder.create();
                dialogShow.show();
                mGuardar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (  mName.getText().toString().equals("") )
                        {
                            Toast.makeText(getApplicationContext(), "¡Error! Campos Vacíos", Toast.LENGTH_SHORT).show();
                        }
                        else {


                            if( inventory.NameValidation(mName.getText().toString()) >= 1 )
                            {
                                Toast.makeText(getApplicationContext(), "Ya existe un producto con ese nombre", Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_SHORT).show();
                                inventory.AddCategory(inventory.getLastId(InventoryDbSchema.Categories_Table.NAME) + 1, mName.getText().toString());
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
        final List<CategoryProduct> categories = inventory.category_alfabetic();
        adapter= new CategoriesAdapter(categories,this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if ((requestCode==request_code && resultCode== RESULT_OK) || (requestCode==request_code2 && resultCode== RESULT_OK))
        {

            //  Toast.makeText(getApplicationContext(), " OK :(", Toast.LENGTH_SHORT).show();

            inventory= new Inventory(getApplicationContext());
            final List<CategoryProduct> categories = inventory.category_alfabetic();
            adapter= new CategoriesAdapter(categories,this);
            recyclerView.setAdapter(adapter);

        }
        else
        {
            //Toast.makeText(getApplicationContext(), "No OK :(", Toast.LENGTH_SHORT).show();

        }



    }
}
