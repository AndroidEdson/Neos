package com.azore.compustore;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.azore.compustore.fiuady.db.CategoryProduct;
import com.azore.compustore.fiuady.db.Inventory;
import com.azore.compustore.fiuady.db.InventoryDbSchema;
import com.azore.compustore.fiuady.db.InventoryHelper;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    public ImageButton btncategorias;
    public ImageButton btnproductos;
    public ImageButton btnensambles;
    public ImageButton btnclientes;
    public ImageButton btnordenes;
    public ImageButton btnreportes;

    private Inventory inventory;
    private SQLiteDatabase db;
    private  InventoryHelper inventoryHelper;

    private RecyclerView recyclerView;

// Valiendo verga 3 am
    //arcique chupala haha xD

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // DECLARANDO BOTONES
        btncategorias= (ImageButton)findViewById(R.id.btnCategory);
        btnproductos= (ImageButton)findViewById(R.id.btnProductos);
        btnensambles= (ImageButton)findViewById(R.id.btnEnsambles);
        btnclientes= (ImageButton)findViewById(R.id.btnClientes);
        btnordenes= (ImageButton)findViewById(R.id.btnOrdenes);
        btnreportes= (ImageButton)findViewById(R.id.btnReportes);
        // DECLARANDO BOTONES

        inventory= new Inventory(getApplicationContext());
     // InventoryHelper.backupDatabaseFile(getApplicationContext());

        //final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item);


       final List<CategoryProduct> categoryProductList= inventory.getAllCategoriesProduct();

       CategoryProduct c= new CategoryProduct(categoryProductList.get(1).getId(),categoryProductList.get(1).getDescription() );
        //Toast.makeText(getApplicationContext(), c.getDescription() , Toast.LENGTH_LONG).show();

       // Toast.makeText(this,Integer.toString(categoryProductList.size()),Toast.LENGTH_SHORT).show();


    }

    // Boton categorias
    public void BtnCategorias(View v){
        Intent i = new Intent(this,CategoriasActivity.class);
        startActivity(i);
    }
    // Boton productos
    public void BtnProductos(View v){
        Intent i = new Intent(this,ProductosActivity.class);
        startActivity(i);
    }
    // Boton ensambles
    public void BtnEnsambles(View v){
        Intent i = new Intent(this,EnsamblesActivity.class);
        startActivity(i);
    }
    // Boton clientes
    public void BtnClientes(View v){
        Intent i = new Intent(this,ClientesActivity.class);
        startActivity(i);
    }
    // Boton ordenes
    public void BtnOrdenes(View v){
        Intent i = new Intent(this,OrdenesActivity.class);
        startActivity(i);
    }
    // Boton reportes
    public void BtnReportes(View v){
        //Intent i = new Intent(this,ReportesActivity.class);
       // startActivity(i);


    }


}
