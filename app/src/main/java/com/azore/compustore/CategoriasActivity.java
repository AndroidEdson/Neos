package com.azore.compustore;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.azore.compustore.fiuady.db.*;

import java.util.Arrays;
import java.util.List;
import java.util.zip.Inflater;

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

          //  Toast.makeText(getApplicationContext(), categoryProduct.getDescription()+" "+Integer.toString(categoryProduct.getId()) , Toast.LENGTH_LONG).show();

          Intent intent = new Intent(getApplicationContext(), Pop.class);
          intent.putExtra(Pop.EXTRA_DESCRIPTION, categoryProduct.getDescription());
          intent.putExtra(Pop.EXTRA_ID, Integer.toString(categoryProduct.getId()));
          startActivityForResult(intent, request_code2);

            // CONSTRUICCION DEL POP UP
      //     constraintLayout= (ConstraintLayout) findViewById(R.id.popup_category);
      //     layoutInflater=(LayoutInflater)getApplicationContext().getSystemService(LAYOUT_INFLATER_SERVICE);
      //     ViewGroup container =( ViewGroup) layoutInflater.inflate(R.layout.pop_up_category, null);
      //     popupWindow= new PopupWindow(container, 400,400, true);
      //     popupWindow.showAtLocation(constraintLayout, Gravity.NO_GRAVITY, 500,500);

        //    PopupMenu popupMenu= new PopupMenu(CategoriasActivity.this,recyclerView);
        //    popupMenu.getMenuInflater().inflate(R.menu.my_popup, popupMenu.getMenu());
        //    popupMenu.setGravity();
        //    popupMenu.show();
            //  textView_name.setText(categoryProduct.getDescription().toString());
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


    private PopupWindow  popupWindow;
   // private PopupMenu popupMenu;
    private LayoutInflater layoutInflater;
    private ConstraintLayout constraintLayout;

    private final int request_code=0;
    private final int request_code2=1;


//__________________________________________________________

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categorias);
        //Cambiar texto de App bar
        getSupportActionBar().setTitle("Categorías");


        recyclerView=(RecyclerView) findViewById(R.id.recycler_categories);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
      //  edit_popup= (ImageButton)findViewById(R.id.popup_image_categori_edit);
        //delete_popup= (ImageButton)findViewById(R.id.popup_image_categori_delete);


        inventory= new Inventory(getApplicationContext());
        //List<CategoryProduct> categories= Arrays.asList(new CategoryProduct(1,"Armin"));
        final List<CategoryProduct> categories = inventory.category_alfabetic();

      //  Toast.makeText(this,categories.get(0).getDescription()+"  "+categories.get(1).getDescription(),Toast.LENGTH_SHORT).show();
       // Toast.makeText(this,Integer.toString(categories.size()),Toast.LENGTH_SHORT).show();

        adapter= new CategoriesAdapter(categories,this);
        recyclerView.setAdapter(adapter);


    }

 public void ShowPopUp(View v) {

     PopupMenu popupMenu= new PopupMenu(getApplicationContext(),v);
     MenuInflater inflater=  popupMenu.getMenuInflater();
     inflater.inflate(R.menu.my_popup, popupMenu.getMenu());
     popupMenu.show();

 }

 public void item_edit_menu(){
     Toast.makeText(getApplicationContext(),"Hola",Toast.LENGTH_SHORT).show();
 }



    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);




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

                Intent i = new Intent(this,Add_categorie_product.class);
                startActivityForResult(i, request_code);

                return true;

        }
        return super.onOptionsItemSelected(item);

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
