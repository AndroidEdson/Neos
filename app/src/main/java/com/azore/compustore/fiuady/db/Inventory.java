package com.azore.compustore.fiuady.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.CursorWrapper;
import android.database.sqlite.SQLiteDatabase;
import android.content.Context;
import android.text.Editable;

import java.util.ArrayList;
import java.util.List;


//***************************************************************************************************
// CATEGORY CURSOR
class CategoryProductCursor extends CursorWrapper{

    public CategoryProductCursor(Cursor cursor) {super(cursor);}


    public CategoryProduct getCategoryProduct(){

        Cursor cursor = getWrappedCursor();
        return  new CategoryProduct(cursor.getInt(cursor.getColumnIndex(InventoryDbSchema.Categories_Table.Columns.ID)),
        cursor.getString(cursor.getColumnIndex(InventoryDbSchema.Categories_Table.Columns.DESCRIPTION)));

    }

}

//***************************************************************************************************
// PRODUCT CURSOR
class ProductCursor extends  CursorWrapper{
    public ProductCursor(Cursor cursor) {super(cursor);}

    public Products getProduct  () {
        Cursor cursor = getWrappedCursor();
        return new Products(cursor.getInt(cursor.getColumnIndex(InventoryDbSchema.Products_Table.Columns.ID)),
                cursor.getInt(cursor.getColumnIndex(InventoryDbSchema.Products_Table.Columns.CATEGORY_ID)),
                cursor.getString(cursor.getColumnIndex(InventoryDbSchema.Products_Table.Columns.DESCRITPION)),
                cursor.getInt(cursor.getColumnIndex(InventoryDbSchema.Products_Table.Columns.PRICE)),
                cursor.getInt(cursor.getColumnIndex(InventoryDbSchema.Products_Table.Columns.QUANTITY)));
    }
}

//***************************************************************************************************
//***************************************************************************************************
//***************************************************************************************************
        // INICIO DE FUNCIONES INVENTORY

public final class Inventory {
    private InventoryHelper inventoryHelper;
    private SQLiteDatabase db;

    public  Inventory(Context context){
        inventoryHelper= new InventoryHelper(context);
        db= inventoryHelper.getWritableDatabase();

    }
    //***************************************************************************************************
    //***************************************************************************************************
    //***************************************************************************************************
    //_________________________ FUNCIONES CATEGORY_________________________________________________________________________________________

       public List<CategoryProduct> getAllCategoriesProduct() {
           List<CategoryProduct> list = new ArrayList<CategoryProduct>();

           //  Cursor cursor = db.rawQuery("SELECT * FROM categories ORDER BY id", null);

           CategoryProductCursor cursor = new CategoryProductCursor((db.rawQuery("SELECT * FROM product_categories ORDER BY id", null)));

           while (cursor.moveToNext()) {

               //list.add(new Category(cursor.getInt(cursor.getColumnIndex((InventoryDBSchema.CategoriesTable.Columns.ID))),
               //   cursor.getString(cursor.getColumnIndex((InventoryDBSchema.CategoriesTable.Columns.DESCRIPTION)))));
               list.add((cursor.getCategoryProduct()));  // metodo wrappcursor
           }
           cursor.close();
           return list;


   }

   // CATEGORIAS ALFABETICAMENTE

    public List category_alfabetic()
    {

        List<CategoryProduct> list = new ArrayList<CategoryProduct>();

        //  Cursor cursor = db.rawQuery("SELECT * FROM categories ORDER BY id", null);

        CategoryProductCursor cursor = new CategoryProductCursor(db.query("product_categories",
                null,
                null,
                null,
                null,
                null,
                "description COLLATE NOCASE ASC"));


        while (cursor.moveToNext()){

            //list.add(new Category(cursor.getInt(cursor.getColumnIndex((InventoryDBSchema.CategoriesTable.Columns.ID))),
            //   cursor.getString(cursor.getColumnIndex((InventoryDBSchema.CategoriesTable.Columns.DESCRIPTION)))));

            list.add((cursor.getCategoryProduct()));  // metodo wrappcursor

        }
        cursor.close();

        return list;

    }

    // Añadir Catgoria

    public void AddCategory(int id, String description)
    {
        ///Agregar un elemento a la base de datos

        db =inventoryHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(InventoryDbSchema.Categories_Table.Columns.ID, id);
        contentValues.put(InventoryDbSchema.Categories_Table.Columns.DESCRIPTION, description);
        db.insert(InventoryDbSchema.Categories_Table.NAME, null, contentValues);

        // Cursor cursor = new CategoryCursor((db.insert("categories", null , contentValues )));
    }

    // Validación de nombre categorias para que no haya un nombre repetido


    public int NameValidation(String name)
    {
        int i=0;

        CategoryProductCursor cursor = new CategoryProductCursor(db.query(InventoryDbSchema.Categories_Table.NAME,
                null,
              "UPPER("+ InventoryDbSchema.Categories_Table.Columns.DESCRIPTION + ")=?",
                new String[] {name.toUpperCase()},
                null,
                null,
                null));

         i = cursor.getCount();
        return i;
    }

    // PARA ELIMINAR UNA CATEGORIA


    public void deleteCategory(String tableName, String i) {

            db.delete(tableName, "id = ?", new String[] {i});
    }


    // ACTUALIZAR O MODIFICAR CATEGORIA
    public  void  updateCategory(String id, String new_name)
    {


        ContentValues values = new ContentValues();
        values.put(InventoryDbSchema.Categories_Table.Columns.DESCRIPTION, new_name);// asegura que siempre da correcto

        db.update(InventoryDbSchema.Categories_Table.NAME, values, InventoryDbSchema.Categories_Table.Columns.ID + " = ?", new String[]{id});

    }


    // FUNCION PARA SABER SI HAY CATEGORIAS ASIGNADAS A PRODUCTOS (PARA SABER SI PUEDEN ELIMINARSE O NO)

    public int ExistProductWhitCategory(String id_categorie){

      int i=0;
      CategoryProductCursor cursor = new CategoryProductCursor(db.query(InventoryDbSchema.Products_Table.NAME,
              null,
               InventoryDbSchema.Products_Table.Columns.CATEGORY_ID + "=?",
              new String[] {id_categorie},
              null,
              null,
              null));

      i = cursor.getCount();
      return i;

    }



    //_________________________END FUNCIONES CATEGORY_________________________________
    //***************************************************************************************************
    //***************************************************************************************************
    //***************************************************************************************************
    //***************************************************************************************************
    //_________________________ FUNCIONES Products_________________________________________________________________________________________

    public List<Products> getAllProducts() {
        List<Products> list = new ArrayList<Products>();


        ProductCursor cursor = new ProductCursor((db.rawQuery("SELECT * FROM products ORDER BY id", null)));

        while (cursor.moveToNext()) {
            list.add((cursor.getProduct()));  // metodo wrappcursor

        }
        cursor.close();
        return list;
}

    // PRODUCTOS ALFABETICAMENTE

    public List products_alfabetic()
    {

        List<Products> list = new ArrayList<Products>();

        //  Cursor cursor = db.rawQuery("SELECT * FROM categories ORDER BY id", null);

        ProductCursor cursor = new ProductCursor(db.query("products",
                null,
                null,
                null,
                null,
                null,
                "description COLLATE NOCASE ASC"));


        while (cursor.moveToNext()){

            //list.add(new Category(cursor.getInt(cursor.getColumnIndex((InventoryDBSchema.CategoriesTable.Columns.ID))),
            //   cursor.getString(cursor.getColumnIndex((InventoryDBSchema.CategoriesTable.Columns.DESCRIPTION)))));

            list.add((cursor.getProduct()));  // metodo wrappcursor

        }
        cursor.close();

        return list;

    }


    // PARA EL FILTRO POR CATEGORIAS EN PRODUCTOS

    public List<Products> categoryFilters(String id_categorie){

        List<Products> list = new ArrayList<Products>();
        ProductCursor cursor = new ProductCursor(db.query(InventoryDbSchema.Products_Table.NAME,
                null,
                InventoryDbSchema.Products_Table.Columns.CATEGORY_ID + "=?",
                new String[] {id_categorie},
                null,
                null,
                "description COLLATE NOCASE ASC"));

        while (cursor.moveToNext()){

            //list.add(new Category(cursor.getInt(cursor.getColumnIndex((InventoryDBSchema.CategoriesTable.Columns.ID))),
            //   cursor.getString(cursor.getColumnIndex((InventoryDBSchema.CategoriesTable.Columns.DESCRIPTION)))));

            list.add((cursor.getProduct()));  // metodo wrappcursor

        }

        return list;

    }

    public List<Products> searchProducts(String input) {
        List<Products> list = new ArrayList<Products>();


        ProductCursor cursor = new ProductCursor((db.rawQuery("SELECT * FROM products WHERE description LIKE '%"+input+"%'"+"  ORDER BY description ASC", null)));

        while (cursor.moveToNext()) {
            list.add((cursor.getProduct()));  // metodo wrappcursor

        }
        cursor.close();
        return list;
    }





}// ______________________________________END PRODUCTS_________________________________________________________
