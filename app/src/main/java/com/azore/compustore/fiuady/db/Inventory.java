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

// ASSEMBLIES CURSOR
class AssembliesCursor extends  CursorWrapper{
    public AssembliesCursor(Cursor cursor) {super(cursor);}

  public Assemblies getAssemblies  () {
      Cursor cursor = getWrappedCursor();
      return new Assemblies(cursor.getInt(cursor.getColumnIndex(InventoryDbSchema.Assemblies_Table.Columns.ID)),
              cursor.getString(cursor.getColumnIndex(InventoryDbSchema.Assemblies_Table.Columns.DESCRIPTION)));
  }
}

//***************************************************************************************************
//***************************************************************************************************
//***************************************************************************************************

// ASSEMBLIES CURSOR
class AssemblyProductCursor extends  CursorWrapper{
    public AssemblyProductCursor(Cursor cursor) {super(cursor);}

    public Assembly_Products getAssemblyProduct  () {
        Cursor cursor = getWrappedCursor();
        return new Assembly_Products (getInt(cursor.getColumnIndex(InventoryDbSchema.AssemblyProducts_Table.Columns.ID)),
                cursor.getInt(cursor.getColumnIndex(InventoryDbSchema.AssemblyProducts_Table.Columns.PRODUCT_ID)),
                cursor.getInt(cursor.getColumnIndex(InventoryDbSchema.AssemblyProducts_Table.Columns.QUANTITY)));
    }
}

//***************************************************************************************************
//***************************************************************************************************
//***************************************************************************************************

//***************************************************************************************************
//***************************************************************************************************
//***************************************************************************************************

// ASSEMBLIES CURSOR
class CustomersCursor extends  CursorWrapper{
    public CustomersCursor(Cursor cursor) {super(cursor);}

    public Customers getCustomers  () {
        Cursor cursor = getWrappedCursor();
        return new Customers(cursor.getInt(cursor.getColumnIndex(InventoryDbSchema.Customers_Table.Columns.ID)),
                cursor.getString(cursor.getColumnIndex(InventoryDbSchema.Customers_Table.Columns.FIRST_NAME)),
                cursor.getString(cursor.getColumnIndex(InventoryDbSchema.Customers_Table.Columns.LAST_NAME)),
                cursor.getString(cursor.getColumnIndex(InventoryDbSchema.Customers_Table.Columns.ADDRESS)),
                cursor.getString(cursor.getColumnIndex(InventoryDbSchema.Customers_Table.Columns.PHONE1)),
                cursor.getString(cursor.getColumnIndex(InventoryDbSchema.Customers_Table.Columns.PHONE2)),
                cursor.getString(cursor.getColumnIndex(InventoryDbSchema.Customers_Table.Columns.PHONE3)),
                cursor.getString(cursor.getColumnIndex(InventoryDbSchema.Customers_Table.Columns.EMAIL)));
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

    public List<Products> searchProductsForCategory(String input,int category_id) {
        List<Products> list = new ArrayList<Products>();


        ProductCursor cursor = new ProductCursor((db.rawQuery("SELECT * FROM (SELECT * FROM products WHERE description LIKE '%" +input+"%'  ORDER BY description ASC) WHERE category_id=" + category_id , null)));

        while (cursor.moveToNext()) {
            list.add((cursor.getProduct()));  // metodo wrappcursor

        }
        cursor.close();
        return list;
    }

    public List<Products> searchProducts(String input) {
        List<Products> list = new ArrayList<Products>();


        ProductCursor cursor = new ProductCursor((db.rawQuery("SELECT * FROM products WHERE description LIKE '%" +input+"%'  ORDER BY description ASC" , null)));

        while (cursor.moveToNext()) {
            list.add((cursor.getProduct()));  // metodo wrappcursor

        }
        cursor.close();
        return list;
    }

    // Validación de nombre categorias para que no haya un nombre repetido


    public int NameValidationProducts(String name)
    {
        int i=0;

        ProductCursor cursor = new ProductCursor(db.query(InventoryDbSchema.Products_Table.NAME,
                null,
                "UPPER("+ InventoryDbSchema.Products_Table.Columns.DESCRITPION + ")=?",
                new String[] {name.toUpperCase()},
                null,
                null,
                null));

        i = cursor.getCount();
        return i;
    }

    // Añadir Producto

    public void AddProduct(int category_id,int id, String description,double price)
    {
        ///Agregar un elemento a la base de datos

        db =inventoryHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(InventoryDbSchema.Products_Table.Columns.CATEGORY_ID, category_id);
        contentValues.put(InventoryDbSchema.Products_Table.Columns.ID, id);
        contentValues.put(InventoryDbSchema.Products_Table.Columns.DESCRITPION, description);
        contentValues.put(InventoryDbSchema.Products_Table.Columns.PRICE, price);
        contentValues.put(InventoryDbSchema.Products_Table.Columns.QUANTITY, 0);
        db.insert(InventoryDbSchema.Products_Table.NAME, null, contentValues);

        // Cursor cursor = new CategoryCursor((db.insert("categories", null , contentValues )));
    }


    //SELECT products.qty FROM products where products.id==1
 // SABER LA CANTIDAD DE STOCK PARA UN PRODUCTO
    public List<Products> QtyProducts(String id)
    {

        List<Products> list = new ArrayList<Products>();

        ProductCursor cursor = new ProductCursor(db.query(InventoryDbSchema.Products_Table.NAME,
                null,
                 InventoryDbSchema.Products_Table.Columns.ID + "=?",
                new String[] {id},
                null,
                null,
                null));

        while (cursor.moveToNext()) {
            list.add((cursor.getProduct()));  // metodo wrappcursor

        }

        return list;
    }

// SIRVE PARA ACTUALIZAR EL STOCK

    public  void  updateQuantityProducts(String id,  String qty )
    {
        ContentValues values = new ContentValues();
        values.put(InventoryDbSchema.Products_Table.Columns.QUANTITY, Integer.valueOf(qty));// asegura que siempre da correcto

        db.update(InventoryDbSchema.Products_Table.NAME, values, InventoryDbSchema.Products_Table.Columns.ID + " = ?", new String[]{id});
    }


    // PARA ELIMINAR UN PRODUCTO


    public void delteProduct(String tableName, String i) {

        db.delete(tableName, "id = ?", new String[] {i});
    }

    // SIRVE PARA MODIFICAR EL PRODUCTO

    public  void  updateProducts(String id,int category_id,  String description, double price )
    {
        ContentValues values = new ContentValues();
        values.put(InventoryDbSchema.Products_Table.Columns.DESCRITPION, description);// asegura que siempre da correcto
        values.put(InventoryDbSchema.Products_Table.Columns.PRICE, price);
        values.put(InventoryDbSchema.Products_Table.Columns.CATEGORY_ID, category_id);
        db.update(InventoryDbSchema.Products_Table.NAME, values, InventoryDbSchema.Products_Table.Columns.ID + " = ?", new String[]{id});
    }

    // FUNCION PARA SABER SI HAY CATEGORIAS ASIGNADAS A PRODUCTOS (PARA SABER SI PUEDEN ELIMINARSE O NO)

    public int ExistAssemblyWhitProduct(String id_product){

        int i=0;
        ProductCursor cursor = new ProductCursor(db.query(InventoryDbSchema.AssemblyProducts_Table.NAME,
                null,
                InventoryDbSchema.AssemblyProducts_Table.Columns.PRODUCT_ID + "=?",
                new String[] {id_product},
                null,
                null,
                null));

        i = cursor.getCount();
        return i;

    }
// ______________________________________END PRODUCTS_________________________________________________________
//***************************************************************************************************
//***************************************************************************************************
//***************************************************************************************************
//***************************************************************************************************
//_________________________ FUNCIONES ASSEMBLIES _________________________________________________________________________________________


    // PRODUCTOS ALFABETICAMENTE

    public List getAssemblies_alfabetic()
    {
        List<Assemblies> list = new ArrayList<Assemblies>();

        AssembliesCursor cursor = new AssembliesCursor(db.query(InventoryDbSchema.Assemblies_Table.NAME,
                null,
                null,
                null,
                null,
                null,
                "description COLLATE NOCASE ASC"));

        while (cursor.moveToNext()){

            list.add((cursor.getAssemblies()));  // metodo wrappcursor
        }

        cursor.close();
        return list;
    }


       //
        Cursor cursor= (db.rawQuery("SELECT  MAX(id) FROM "+TableName, null));

       cursor.moveToFirst();

        //List<Products> list = new ArrayList<Products>();
        int maxid= cursor.getInt(cursor.getColumnIndex("MAX(id)"));
        return maxid;

    }

// FUNCION PARA AÑADIR NUEVOS ENSAMBLES
    public void AddAssemblies(int id, String description)
    {
        ///Agregar un elemento a la base de datos

        db =inventoryHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(InventoryDbSchema.Assemblies_Table.Columns.ID, id);
        contentValues.put(InventoryDbSchema.Assemblies_Table.Columns.DESCRIPTION, description);
        db.insert(InventoryDbSchema.Assemblies_Table.NAME, null, contentValues);

        // Cursor cursor = new CategoryCursor((db.insert("categories", null , contentValues )));
    }


    // FUNCION GENERICA PARA SABER SI YA EXISTE ALGUNO ELEMENTO CON ESE NOMBRE (DESCRIPTION)
    public int NameValidationGeneric(String TableName, String name_compare)
    {
        int i=0;

        ProductCursor cursor = new ProductCursor(db.query(TableName,
                null,
                "UPPER("+ "description" + ")=?",
                new String[] {name_compare.toUpperCase()},
                null,
                null,
                null));

        i = cursor.getCount();
        return i;
    }

    // GENERAR LISTA DE PRODUCTOS QUE CONTIENE CADA ENSAMBLE

    public  List<Products> getProductsAssembly(String id_ensamble) {

        List<Products> list = new ArrayList<Products>();

        //  Cursor cursor = db.rawQuery("SELECT * FROM categories ORDER BY id", null);

        String query_macizo = "SELECT p.id, p.category_id, p.description, p.price, ap.qty FROM assemblies a " +
                "INNER JOIN assembly_products ap ON (a.id = ap.id) " +
                "INNER JOIN products p ON (p.id=ap.product_id) " +
                "WHERE a.id=" + id_ensamble;
        ProductCursor cursor = new ProductCursor(db.rawQuery(query_macizo, null));


        while (cursor.moveToNext()) {
            list.add((cursor.getProduct()));
        }

        return list;
    }

    // QUERY QUE DEVULVE CUANTOS PRODUCTOS DE UN TIPO NECESITA PARA CADA ENSAMBLE


    public  int getNumberOfProductsOnEnsambly(String id_product) {


        //  Cursor cursor = db.rawQuery("SELECT * FROM categories ORDER BY id", null);

        String query_macizo = "SELECT ap.qty " +
                "FROM assemblies a INNER JOIN assembly_products ap ON (a.id = ap.id) " +
                "INNER JOIN products p ON (p.id=ap.product_id) " +
                "where p.id="+ id_product;


        Cursor cursor = (db.rawQuery(query_macizo, null));
        cursor.moveToFirst();

        //List<Products> list = new ArrayList<Products>();
        int qty_product= cursor.getInt(cursor.getColumnIndex("qty"));
        return qty_product;


    }


        // PARA ACTUALIZAR LA CANTIDAD DE PRODUCTO EN UN ENSAMBLE (MODIFICAR ASSEMBLY_PRODUCTS

    public  void  updateAssemblyProduct( String id, String product_id,  int qty )
    {
        db =inventoryHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(InventoryDbSchema.AssemblyProducts_Table.Columns.QUANTITY,qty );
        //db.update(InventoryDbSchema.AssemblyProducts_Table.NAME, values, InventoryDbSchema.AssemblyProducts_Table.Columns.PRODUCT_ID + "= ?", new String[]{product_id});
         db.update(InventoryDbSchema.AssemblyProducts_Table.NAME, values, InventoryDbSchema.AssemblyProducts_Table.Columns.PRODUCT_ID + " = ? AND " + InventoryDbSchema.AssemblyProducts_Table.Columns.ID + "= ?", new String[]{product_id, id});
    db.close();
    }

    public void deleteProductFromEnsambly( String id_ensamble, String id_product) {

        db.delete(InventoryDbSchema.AssemblyProducts_Table.NAME, InventoryDbSchema.AssemblyProducts_Table.Columns.ID +" = ? AND " + InventoryDbSchema.AssemblyProducts_Table.Columns.PRODUCT_ID +" = ?", new String[] {id_ensamble,id_product});
    }

    public void updateAssemblies( String id, String new_name) {

        db =inventoryHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(InventoryDbSchema.Assemblies_Table.Columns.DESCRIPTION,new_name );
        //db.update(InventoryDbSchema.AssemblyProducts_Table.NAME, values, InventoryDbSchema.AssemblyProducts_Table.Columns.PRODUCT_ID + "= ?", new String[]{product_id});
        db.update(InventoryDbSchema.Assemblies_Table.NAME, values, InventoryDbSchema.Assemblies_Table.Columns.ID + "= ?", new String[]{ id});
    }


//_________________________END FUNCIONES ASSEMBLIES_________________________________
    //***************************************************************************************************
    //***************************************************************************************************
    //***************************************************************************************************
    //***************************************************************************************************
    //_________________________ FUNCIONES CUSTOMERS_________________________________________________________________________________________
    // PRODUCTOS ALFABETICAMENTE

    public List customers_alfabetic()
    {

        List<Customers> list = new ArrayList<Customers>();

        //  Cursor cursor = db.rawQuery("SELECT * FROM categories ORDER BY id", null);

        CustomersCursor cursor = new CustomersCursor(db.query("customers",
                null,
                null,
                null,
                null,
                null,
                "first_name COLLATE NOCASE ASC"));


        while (cursor.moveToNext()){

            //list.add(new Category(cursor.getInt(cursor.getColumnIndex((InventoryDBSchema.CategoriesTable.Columns.ID))),
            //   cursor.getString(cursor.getColumnIndex((InventoryDBSchema.CategoriesTable.Columns.DESCRIPTION)))));

            list.add((cursor.getCustomers()));  // metodo wrappcursor

        }
        cursor.close();

        return list;

    }

    // Añadir Cliente

    public void AddCustomer(int id, String firstname,String lastname,String address,String Phone1,String Phone2,String Phone3,String email )
    {
        ///Agregar un elemento a la base de datos

        db =inventoryHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(InventoryDbSchema.Customers_Table.Columns.ID, id);
        contentValues.put(InventoryDbSchema.Customers_Table.Columns.FIRST_NAME, firstname);
        contentValues.put(InventoryDbSchema.Customers_Table.Columns.LAST_NAME, lastname);
        contentValues.put(InventoryDbSchema.Customers_Table.Columns.ADDRESS, address);
        contentValues.put(InventoryDbSchema.Customers_Table.Columns.PHONE1, Phone1);
        contentValues.put(InventoryDbSchema.Customers_Table.Columns.PHONE2, Phone2);
        contentValues.put(InventoryDbSchema.Customers_Table.Columns.PHONE3,Phone3);
        contentValues.put(InventoryDbSchema.Customers_Table.Columns.EMAIL, email);


        db.insert(InventoryDbSchema.Customers_Table.NAME, null, contentValues);

        // Cursor cursor = new CategoryCursor((db.insert("categories", null , contentValues )));
    }
    // PARA ELIMINAR UN Cliente


    public void deleteCustomer(String tableName, String i) {

        db.delete(tableName, "id = ?", new String[] {i});
    }

    // ACTUALIZAR O MODIFICAR CUSTOMER
    public  void  updateCustomer(String id, String firstname,String lastname,String address,String Phone1,String Phone2,String Phone3,String email )
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put(InventoryDbSchema.Customers_Table.Columns.ID, id);
        contentValues.put(InventoryDbSchema.Customers_Table.Columns.FIRST_NAME, firstname);
        contentValues.put(InventoryDbSchema.Customers_Table.Columns.LAST_NAME, lastname);
        contentValues.put(InventoryDbSchema.Customers_Table.Columns.ADDRESS, address);
        contentValues.put(InventoryDbSchema.Customers_Table.Columns.PHONE1, Phone1);
        contentValues.put(InventoryDbSchema.Customers_Table.Columns.PHONE2, Phone2);
        contentValues.put(InventoryDbSchema.Customers_Table.Columns.PHONE3,Phone3);
        contentValues.put(InventoryDbSchema.Customers_Table.Columns.EMAIL, email);

        db.update(InventoryDbSchema.Customers_Table.NAME, contentValues, InventoryDbSchema.Customers_Table.Columns.ID + " = ?", new String[]{id});

    }




    //***************************************************************************************************
//***************************************************************************************************
//***************************************************************************************************
//***************************************************************************************************



} // END FINAL DEL MUNDO UNIVERSAL DEL COSMOS