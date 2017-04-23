package com.azore.compustore.fiuady.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.CursorWrapper;
import android.database.sqlite.SQLiteDatabase;
import android.content.Context;
import android.support.design.widget.TabLayout;
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

// ORDERS CURSOR
class OrdersCursor extends  CursorWrapper{
    public OrdersCursor(Cursor cursor) {super(cursor);}

    public Orders getOrders  () {
        Cursor cursor = getWrappedCursor();
        return new Orders (getInt(cursor.getColumnIndex(InventoryDbSchema.Orders_Table.Columns.ID)),
                cursor.getInt(cursor.getColumnIndex(InventoryDbSchema.Orders_Table.Columns.STATUS_ID)),
                cursor.getInt(cursor.getColumnIndex(InventoryDbSchema.Orders_Table.Columns.COSTUMER_ID)),
                cursor.getString(cursor.getColumnIndex(InventoryDbSchema.Orders_Table.Columns.DATE)),
                cursor.getString(cursor.getColumnIndex(InventoryDbSchema.Orders_Table.Columns.CHANGE_LOG)));
    }
}

//***************************************************************************************************
//***************************************************************************************************
//***************************************************************************************************

// ORDERS ASSEMBLIES CURSOR
class OrderAssembliesCursor extends  CursorWrapper{
    public OrderAssembliesCursor(Cursor cursor) {super(cursor);}

    public Order_Assemblies getOrders  () {
        Cursor cursor = getWrappedCursor();
        return new Order_Assemblies (getInt(cursor.getColumnIndex(InventoryDbSchema.Order_Assemblies_Table.Columns.ID)),
                cursor.getInt(cursor.getColumnIndex(InventoryDbSchema.Order_Assemblies_Table.Columns.ASSEMBLY_ID)),
                cursor.getInt(cursor.getColumnIndex(InventoryDbSchema.Order_Assemblies_Table.Columns.QUANTITY)));
    }
}

//***************************************************************************************************
//***************************************************************************************************
//***************************************************************************************************

// ORDERS STATUS CURSOR
class OrderStatusCursor extends  CursorWrapper{
    public OrderStatusCursor(Cursor cursor) {super(cursor);}

    public Order_Status getOrderStatus  () {
        Cursor cursor = getWrappedCursor();
        return new Order_Status (getInt(cursor.getColumnIndex(InventoryDbSchema.OrderStatus_Table.Columns.ID)),
                cursor.getString(cursor.getColumnIndex(InventoryDbSchema.OrderStatus_Table.Columns.DESCRIPTION)),
                cursor.getInt(cursor.getColumnIndex(InventoryDbSchema.OrderStatus_Table.Columns.EDITABLE)),
                cursor.getString(cursor.getColumnIndex(InventoryDbSchema.OrderStatus_Table.Columns.PREVIOUS)),
                cursor.getString(cursor.getColumnIndex(InventoryDbSchema.OrderStatus_Table.Columns.NEXT)));
    }
}




//***************************************************************************************************
//***************************************************************************************************
//***************************************************************************************************



// UNION DE ORDENES MACIZO FULL  CURSOR
class OrderUnionCursor extends  CursorWrapper{
    public OrderUnionCursor(Cursor cursor) {super(cursor);}

    public OrdenesUnion getOrdenesUnion  () {
        Cursor cursor = getWrappedCursor();
        return new OrdenesUnion (getInt(cursor.getColumnIndex(InventoryDbSchema.OrdenesUnion_Table.Columns.ID)),
                cursor.getInt(cursor.getColumnIndex(InventoryDbSchema.OrdenesUnion_Table.Columns.ID_STATUS)),
                cursor.getString(cursor.getColumnIndex(InventoryDbSchema.OrdenesUnion_Table.Columns.STATUS_DESCRIPTION)),
                cursor.getInt(cursor.getColumnIndex(InventoryDbSchema.OrdenesUnion_Table.Columns.ID_CUSTOMER)),
                cursor.getString(cursor.getColumnIndex(InventoryDbSchema.OrdenesUnion_Table.Columns.FIRST_NAME)),
                cursor.getString(cursor.getColumnIndex(InventoryDbSchema.OrdenesUnion_Table.Columns.LAST_NAME)),
                cursor.getDouble(cursor.getColumnIndex(InventoryDbSchema.OrdenesUnion_Table.Columns.COSTO)),
                cursor.getString(cursor.getColumnIndex(InventoryDbSchema.OrdenesUnion_Table.Columns.DATE)));
    }
}






//***************************************************************************************************
//***************************************************************************************************
//***************************************************************************************************

//***************************************************************************************************
//***************************************************************************************************
//***************************************************************************************************




// CUSTOMERS CURSOR
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

// ORDERS IN ASSEMBLIES UNION CURSOR ES USADO PARA OBTENER INFORMACION DE UN ENSAMBLE RESPPECTO A LA ORDEN
class OrderAssembliesUnionCursor extends  CursorWrapper{
    public OrderAssembliesUnionCursor(Cursor cursor) {super(cursor);}

    public AssemblieOrders_Union getAssemblieOrdersUnion  () {
        Cursor cursor = getWrappedCursor();
        return new AssemblieOrders_Union (getInt(cursor.getColumnIndex(InventoryDbSchema.AssemblieOrders_Union_Table.Columns.ID)),
                cursor.getInt(cursor.getColumnIndex(InventoryDbSchema.AssemblieOrders_Union_Table.Columns.ASSEMBLY_ID)),
                cursor.getString(cursor.getColumnIndex(InventoryDbSchema.AssemblieOrders_Union_Table.Columns.DESCRIPTION)),
                cursor.getInt(cursor.getColumnIndex(InventoryDbSchema.AssemblieOrders_Union_Table.Columns.QTY)),
                cursor.getDouble(cursor.getColumnIndex(InventoryDbSchema.AssemblieOrders_Union_Table.Columns.TOTAL)));
    }
}

//***************************************************************************************************
//***************************************************************************************************
//***************************************************************************************************

// PARA REPORTES TENER LA GANANCIA POR MES

class SalesMonthCursor extends  CursorWrapper{
    public SalesMonthCursor(Cursor cursor) {super(cursor);}

    public SalesMonth getSalesMonth  () {
        Cursor cursor = getWrappedCursor();
        return new SalesMonth (getInt(cursor.getColumnIndex(InventoryDbSchema.SalesMonth.Columns.COUNT)),
                cursor.getDouble(cursor.getColumnIndex(InventoryDbSchema.SalesMonth.Columns.GAIN)));
    }
}

//***************************************************************************************************
//***************************************************************************************************
//***************************************************************************************************

// PARA ENSAMBLES VENDIDOS DEL MES

class EnsamblesVendidosCursor extends  CursorWrapper{
    public EnsamblesVendidosCursor(Cursor cursor) {super(cursor);}

    public Ensambles_vendidos_Mes getEnsamblesVendidosMes  () {
        Cursor cursor = getWrappedCursor();
        return new Ensambles_vendidos_Mes (getInt(cursor.getColumnIndex(InventoryDbSchema.Ensambles_vendidos_mes.Columns.ID)),
                cursor.getString(cursor.getColumnIndex(InventoryDbSchema.Ensambles_vendidos_mes.Columns.DESCRIPTION)),
                cursor.getInt(cursor.getColumnIndex(InventoryDbSchema.Ensambles_vendidos_mes.Columns.QTY)),
                cursor.getString(cursor.getColumnIndex(InventoryDbSchema.Ensambles_vendidos_mes.Columns.DATE)),
                cursor.getDouble(cursor.getColumnIndex(InventoryDbSchema.Ensambles_vendidos_mes.Columns.PRICE_ASSEMBLY)));
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

    // FUNCION PARA SABER SI HAY ENSAMBLES ASIGNADAS A PRODUCTOS (PARA SABER SI PUEDEN ELIMINARSE O NO)

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

// FUNCION GENÉRICA PARA OBTENER EL MAXIMO ID DE CUALQUIER TABLA
    public int getLastId(String TableName)
    {

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
                "WHERE a.id=" + id_ensamble + " ORDER BY p.description ASC";
        ProductCursor cursor1 = new ProductCursor(db.rawQuery(query_macizo, null));
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


    public  int getNumberOfProductsOnEnsamblyActual(String id_ensamble, String id_product) {

        //  Cursor cursor = db.rawQuery("SELECT * FROM categories ORDER BY id", null);
        int qty_product=0;
        String query_macizo = "SELECT qty FROM   assembly_products WHERE id= " + id_ensamble + " AND product_id=" + id_product;

        Cursor cursor = (db.rawQuery(query_macizo, null));

        if (cursor.getCount() >= 1) {
            cursor.moveToFirst();
             qty_product= cursor.getInt(cursor.getColumnIndex("qty"));

        }
        else{
            qty_product=0;
        }
        //List<Products> list = new ArrayList<Products>();
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

    public void deleteAllProductsFromAssembly( String id_ensamble) {

        db.delete(InventoryDbSchema.AssemblyProducts_Table.NAME, InventoryDbSchema.AssemblyProducts_Table.Columns.ID +" = ? ", new String[] {id_ensamble});
    }

    public void updateAssemblies( String id, String new_name) {

        db =inventoryHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(InventoryDbSchema.Assemblies_Table.Columns.DESCRIPTION,new_name );
        //db.update(InventoryDbSchema.AssemblyProducts_Table.NAME, values, InventoryDbSchema.AssemblyProducts_Table.Columns.PRODUCT_ID + "= ?", new String[]{product_id});
        db.update(InventoryDbSchema.Assemblies_Table.NAME, values, InventoryDbSchema.Assemblies_Table.Columns.ID + "= ?", new String[]{ id});
    }


    public int ExistAssembliesInOrders(String id)
    {

        int i=0;

        OrderAssembliesCursor cursor = new OrderAssembliesCursor(db.query(InventoryDbSchema.Order_Assemblies_Table.NAME,
                null,
                InventoryDbSchema.Order_Assemblies_Table.Columns.ASSEMBLY_ID +" =?",
                new String[] {id},
                null,
                null,
                null));

        i = cursor.getCount();
        return i;

    }

    public void deleteAssemblies (String id)
    {
        //elimina primero los products internos
        db.delete(InventoryDbSchema.AssemblyProducts_Table.NAME , InventoryDbSchema.AssemblyProducts_Table.Columns.ID+" = ?", new String[] {id});
        //ELIMINA LA CATEGORIA
        db.delete(InventoryDbSchema.Assemblies_Table.NAME , InventoryDbSchema.Assemblies_Table.Columns.ID+" = ?", new String[] {id});


    }



    //FUNCION PARA AÑADIR PRODUCTOS A LOS ENSAMBLES NO FAKE 100 REAL

    // FUNCION PARA AÑADIR NUEVOS ENSAMBLES
    public void AddAssemblyProduct(String id_ensamble, String product_id)
    {
        ///Agregar un elemento a la base de datos
        db =inventoryHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(InventoryDbSchema.AssemblyProducts_Table.Columns.ID, Integer.valueOf(id_ensamble));
        contentValues.put(InventoryDbSchema.AssemblyProducts_Table.Columns.PRODUCT_ID, Integer.valueOf(product_id));
        contentValues.put(InventoryDbSchema.AssemblyProducts_Table.Columns.QUANTITY, 1);

        db.insert(InventoryDbSchema.AssemblyProducts_Table.NAME, null, contentValues);
        // Cursor cursor = new CategoryCursor((db.insert("categories", null , contentValues )));
    }

    public void AddAssemblyProductQty(String id_ensamble, String product_id, String qty)
    {
        ///Agregar un elemento a la base de datos
        db =inventoryHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(InventoryDbSchema.AssemblyProducts_Table.Columns.ID, Integer.valueOf(id_ensamble));
        contentValues.put(InventoryDbSchema.AssemblyProducts_Table.Columns.PRODUCT_ID, Integer.valueOf(product_id));
        contentValues.put(InventoryDbSchema.AssemblyProducts_Table.Columns.QUANTITY, Integer.valueOf(qty));

        db.insert(InventoryDbSchema.AssemblyProducts_Table.NAME, null, contentValues);
        // Cursor cursor = new CategoryCursor((db.insert("categories", null , contentValues )));
    }

    // PARA EVITAR QUE SE AGREGUE UN PRODUCTO QUE YA EXISTE A UN MISMO ENSAMBLE

    public int ExistThisProductInAssembly(String id, String id_product)
    {

        int i=0;

        AssemblyProductCursor cursor = new AssemblyProductCursor(db.query(InventoryDbSchema.AssemblyProducts_Table.NAME,
                null,
                InventoryDbSchema.AssemblyProducts_Table.Columns.ID +" = ? AND "+ InventoryDbSchema.AssemblyProducts_Table.Columns.PRODUCT_ID +" = ?",
                new String[] {id, id_product},
                null,
                null,
                null));

        i = cursor.getCount();
        return i;

    }

    //BUSCAR ENSAMBLE

    public List<Assemblies> searchEnsamble(String input) {
        List<Assemblies> list = new ArrayList<Assemblies>();


        AssembliesCursor cursor = new AssembliesCursor((db.rawQuery("SELECT * FROM assemblies WHERE description LIKE '%"+ input +"%'  ORDER BY description ASC" , null)));

        while (cursor.moveToNext()) {
            list.add((cursor.getAssemblies()));  // metodo wrappcursor

        }
        cursor.close();
        return list;
    }


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

//buscar
public List<Customers> searchCustomers(String input,boolean first_name,boolean last_name,boolean address
        ,boolean phone,boolean email) {
    List<Customers> list = new ArrayList<Customers>();
    String first_name_str;
    String last_name_str;
    String address_name_str;
    String phone_name_str;
    String email_str;

        if (first_name == true) first_name_str = "first_name LIKE '%" + input + "%'"; else first_name_str="id < 0 ";
        if (last_name == true) last_name_str = "OR last_name LIKE '%" + input + "%'"; else last_name_str="";
        if (address == true) address_name_str = "OR address LIKE '%" + input + "%'"; else address_name_str="";
        if (phone == true) phone_name_str = "OR phone1 LIKE '%" + input + "%' OR phone2 LIKE '%" + input + "%' OR phone3 LIKE '%" + input + "%'"; else phone_name_str="";
        if (email == true) email_str = "OR e_mail LIKE '%" + input + "%'"; else email_str="";
        if((first_name||last_name||address||phone||email) != true)  first_name_str="id > 0 ";


        CustomersCursor cursor = new CustomersCursor((db.rawQuery("SELECT * FROM customers WHERE ("
                + first_name_str + last_name_str + address_name_str + phone_name_str
                + email_str +
                ") ORDER BY first_name ASC", null))
        );



    while (cursor.moveToNext()) {
        list.add((cursor.getCustomers()));  // metodo wrappcursor

    }
    cursor.close();
    return list;
}


 //***************************************************************************************************
//***************************************************************************************************
//***************************************************************************************************
// ______________________________________END CUSTOMERS_________________________________________________________
//***************************************************************************************************
//***************************************************************************************************
//***************************************************************************************************
//***************************************************************************************************
//_________________________ FUNCIONES ORDERS _________________________________________________________________________________________
 public int ExistCustomersWhitOrders(String id_customer){

     int i=0;
     CustomersCursor cursor = new CustomersCursor(db.query(InventoryDbSchema.Orders_Table.NAME,
             null,
             InventoryDbSchema.Orders_Table.Columns.COSTUMER_ID + "=?",
             new String[] {id_customer},
             null,
             null,
             null));
     i = cursor.getCount();
     return i;

 }

 // PARA OBTENER LA LISTA DEL ORDENES CON LOS DATOS  DE LA INTERFAZ



    public  List<OrdenesUnion> getOrdersUnion() {

        List<OrdenesUnion> list = new ArrayList<OrdenesUnion>();

        //  Cursor cursor = db.rawQuery("SELECT * FROM categories ORDER BY id", null);

        String query_macizox2 = "SELECT a.id,b.id as id_status,  b.description as status_description , e.id as id_customer,  e.first_name, e.last_name, sum(c.qty * p.price* ap.qty) as costo, a.date \n" +
                "FROM orders                 a\n" +
                "INNER JOIN  order_status    b ON ( a.status_id = b.id )  \n" +
                "INNER JOIN order_assemblies c ON ( a.id= c.id)\n" +
                "INNER JOIN assemblies       d ON ( c.assembly_id = d.id)\n" +
                "INNER JOIN customers        e ON ( a.customer_id = e.id) \n" +
                "INNER JOIN assembly_products ap ON (d.id = ap.id)\n" +
                "INNER JOIN products p ON     (p.id=ap.product_id)\n" +
                "GROUP BY a.id ORDER BY date(a.date) DESC";

       OrderUnionCursor cursor = new OrderUnionCursor(db.rawQuery(query_macizox2, null));

        while (cursor.moveToNext()) {
            list.add((cursor.getOrdenesUnion()));
        }

        return list;
    }

    // PARA OBTENER LA LISTA DEL ORDENES PENDIENTES CON LOS DATOS  DE LA INTERFAZ



    public  List<OrdenesUnion> getOrdersUnionPendientes() {

        List<OrdenesUnion> list = new ArrayList<OrdenesUnion>();

        //  Cursor cursor = db.rawQuery("SELECT * FROM categories ORDER BY id", null);

        String query = "SELECT a.id,b.id as id_status,  b.description as status_description , e.id as id_customer,  e.first_name, e.last_name, sum(c.qty * p.price* ap.qty) as costo, a.date \n" +
                "\n" +
                "                FROM orders                 a\n" +
                "                INNER JOIN  order_status    b ON ( a.status_id = b.id )  \n" +
                "                INNER JOIN order_assemblies c ON ( a.id= c.id)\n" +
                "                INNER JOIN assemblies       d ON ( c.assembly_id = d.id)\n" +
                "                INNER JOIN customers        e ON ( a.customer_id = e.id) \n" +
                "                INNER JOIN assembly_products ap ON (d.id = ap.id)\n" +
                "                INNER JOIN products p ON     (p.id=ap.product_id)\n" +
                "                GROUP BY a.id HAVING a.status_id = 0 ORDER BY date(a.date) DESC";

        OrderUnionCursor cursor = new OrderUnionCursor(db.rawQuery(query, null));

        while (cursor.moveToNext()) {
            list.add((cursor.getOrdenesUnion()));
        }

        return list;
    }

    //buscar clientes
    public List<OrdenesUnion> searchCustomerswithOrders(String input) {
        List<OrdenesUnion> list = new ArrayList<OrdenesUnion>();

        String querysearchOrders = "SELECT * FROM (SELECT a.id,b.id as id_status,  b.description as status_description , e.id as id_customer,  e.first_name, e.last_name, sum(c.qty * p.price* ap.qty) as costo, a.date \n" +
                "FROM orders                 a\n" +
                "INNER JOIN  order_status    b ON ( a.status_id = b.id )  \n" +
                "INNER JOIN order_assemblies c ON ( a.id= c.id)\n" +
                "INNER JOIN assemblies       d ON ( c.assembly_id = d.id)\n" +
                "INNER JOIN customers        e ON ( a.customer_id = e.id) \n" +
                "INNER JOIN assembly_products ap ON (d.id = ap.id)\n" +
                "INNER JOIN products p ON     (p.id=ap.product_id)\n" +
                "GROUP BY a.id ORDER BY date(a.date) DESC) WHERE (first_name LIKE '%"+input+"%' OR last_name LIKE '%"+input+"%')";

        OrderUnionCursor cursor = new OrderUnionCursor((db.rawQuery(querysearchOrders, null))
        );

        while (cursor.moveToNext()) {
            list.add((cursor.getOrdenesUnion()));  // metodo wrappcursor

        }
        cursor.close();
        return list;
    }
    //buscar clientes con ordenes pendientes
    public List<OrdenesUnion> searchCustomerswithOrdersPendientes(String input) {
        List<OrdenesUnion> list = new ArrayList<OrdenesUnion>();

        String querysearchOrders = "SELECT * FROM (SELECT a.id,b.id as id_status,  b.description as status_description , e.id as id_customer,  e.first_name, e.last_name, sum(c.qty * p.price* ap.qty) as costo, a.date \n" +
                "FROM orders                 a\n" +
                "INNER JOIN  order_status    b ON ( a.status_id = b.id )  \n" +
                "INNER JOIN order_assemblies c ON ( a.id= c.id)\n" +
                "INNER JOIN assemblies       d ON ( c.assembly_id = d.id)\n" +
                "INNER JOIN customers        e ON ( a.customer_id = e.id) \n" +
                "INNER JOIN assembly_products ap ON (d.id = ap.id)\n" +
                "INNER JOIN products p ON     (p.id=ap.product_id)\n" +
                "GROUP BY a.id HAVING a.status_id = 0 ORDER BY date(a.date) DESC) WHERE (first_name LIKE '%"+input+"%' OR last_name LIKE '%"+input+"%')";

        OrderUnionCursor cursor = new OrderUnionCursor((db.rawQuery(querysearchOrders, null))
        );

        while (cursor.moveToNext()) {
            list.add((cursor.getOrdenesUnion()));  // metodo wrappcursor

        }
        cursor.close();
        return list;
    }



    public  List<Order_Status> getAllOrderStatus() {

        List<Order_Status> list = new ArrayList<Order_Status>();

        OrderStatusCursor cursor = new OrderStatusCursor(db.query(InventoryDbSchema.OrderStatus_Table.NAME,
                null,
                null,
                null,
                null,
                null,
                "id  ASC"));


        while (cursor.moveToNext()){
            list.add((cursor.getOrderStatus()));  // metodo wrappcursor
        }
        return list;
    }

    public  List<OrdenesUnion> getFiltersOrder(String id_status) {

        List<OrdenesUnion> list = new ArrayList<OrdenesUnion>();

        //  Cursor cursor = db.rawQuery("SELECT * FROM categories ORDER BY id", null);

        String query_macizox2 = "SELECT a.id,b.id as id_status,  b.description as status_description , e.id as id_customer,  e.first_name, e.last_name, sum(c.qty * p.price* ap.qty) as costo, a.date \n" +
                "FROM orders                 a\n" +
                "INNER JOIN  order_status    b ON ( a.status_id = b.id )  \n" +
                "INNER JOIN order_assemblies c ON ( a.id= c.id)\n" +
                "INNER JOIN assemblies       d ON ( c.assembly_id = d.id)\n" +
                "INNER JOIN customers        e ON ( a.customer_id = e.id) \n" +
                "INNER JOIN assembly_products ap ON (d.id = ap.id)\n" +
                "INNER JOIN products p ON     (p.id=ap.product_id)\n" +
                "GROUP BY a.id HAVING id_status= " +  id_status+" ORDER BY date(a.date) DESC";

        OrderUnionCursor cursor = new OrderUnionCursor(db.rawQuery(query_macizox2, null));

        while (cursor.moveToNext()) {
            list.add((cursor.getOrdenesUnion()));
        }

        return list;
    }

    // SIRVE PARA OBTENER EL FILTRO ENTRE DOS FECHAS Y DENTRO DE UNA CATEGORIA STATUS

    public List<OrdenesUnion> getOrderFilterDate(String date_begin, String date_end, String status_id) {
        List<OrdenesUnion> list = new ArrayList<OrdenesUnion>();

        String between_two_Dates = "SELECT a.id,b.id as id_status,  b.description as status_description , e.id as id_customer,  e.first_name, e.last_name, sum(c.qty * p.price* ap.qty) as costo, a.date \n" +
                "FROM orders                 a\n" +
                "INNER JOIN  order_status    b ON ( a.status_id = b.id )  \n" +
                "INNER JOIN order_assemblies c ON ( a.id= c.id)\n" +
                "INNER JOIN assemblies       d ON ( c.assembly_id = d.id)\n" +
                "INNER JOIN customers        e ON ( a.customer_id = e.id) \n" +
                "INNER JOIN assembly_products ap ON (d.id = ap.id)\n" +
                "INNER JOIN products p ON     (p.id=ap.product_id)\n" +
                "GROUP BY a.id HAVING a.date BETWEEN date('"+ date_begin+ "') AND date('"+ date_end +"') AND id_status="+ status_id +" ORDER BY date(a.date) DESC";
        OrderUnionCursor cursor = new OrderUnionCursor((db.rawQuery(between_two_Dates, null))
        );

        while (cursor.moveToNext()) {
            list.add((cursor.getOrdenesUnion()));  // metodo wrappcursor

        }
        cursor.close();
        return list;
    }


    // SIRVE PARA OBTENER EL FILTRO ENTRE DOS FECHAS Y INCLUYENDO TODOS LOS STATUS

    public List<OrdenesUnion> getOrderFilterDateAll(String date_begin, String date_end ) {
        List<OrdenesUnion> list = new ArrayList<OrdenesUnion>();

        String between_two_Dates = "SELECT a.id,b.id as id_status,  b.description as status_description , e.id as id_customer,  e.first_name, e.last_name, sum(c.qty * p.price* ap.qty) as costo, a.date \n" +
                "FROM orders                 a\n" +
                "INNER JOIN  order_status    b ON ( a.status_id = b.id )  \n" +
                "INNER JOIN order_assemblies c ON ( a.id= c.id)\n" +
                "INNER JOIN assemblies       d ON ( c.assembly_id = d.id)\n" +
                "INNER JOIN customers        e ON ( a.customer_id = e.id) \n" +
                "INNER JOIN assembly_products ap ON (d.id = ap.id)\n" +
                "INNER JOIN products p ON     (p.id=ap.product_id)\n" +
                "GROUP BY a.id HAVING a.date BETWEEN date('"+ date_begin+ "') AND date('"+ date_end +"') ORDER BY date(a.date) DESC";
        OrderUnionCursor cursor = new OrderUnionCursor((db.rawQuery(between_two_Dates, null))
        );

        while (cursor.moveToNext()) {
            list.add((cursor.getOrdenesUnion()));  // metodo wrappcursor

        }
        cursor.close();
        return list;
    }


    // ACTUALIZAR O MODIFICAR la fecha (
    public  void  updateOrderDate(String date, String id)
    {


        ContentValues values = new ContentValues();
        values.put(InventoryDbSchema.Orders_Table.Columns.DATE, date);// asegura que siempre da correcto

        db.update(InventoryDbSchema.Orders_Table.NAME, values, InventoryDbSchema.Orders_Table.Columns.ID + " = ?", new String[]{id});

    }

    // PARA OBTENER UNA UNICA ORDEN
    public  OrdenesUnion getOneOrder(String id_order) {

        OrdenesUnion ordenesUnion;
        //  Cursor cursor = db.rawQuery("SELECT * FROM categories ORDER BY id", null);

        String query_macizox2 = "SELECT a.id,b.id as id_status,  b.description as status_description , e.id as id_customer,  e.first_name, e.last_name, sum(c.qty * p.price* ap.qty) as costo, a.date \n" +
                "FROM orders                 a\n" +
                "INNER JOIN  order_status    b ON ( a.status_id = b.id )  \n" +
                "INNER JOIN order_assemblies c ON ( a.id= c.id)\n" +
                "INNER JOIN assemblies       d ON ( c.assembly_id = d.id)\n" +
                "INNER JOIN customers        e ON ( a.customer_id = e.id) \n" +
                "INNER JOIN assembly_products ap ON (d.id = ap.id)\n" +
                "INNER JOIN products p ON     (p.id=ap.product_id)\n" +
                "GROUP BY a.id HAVING a.id= "+ id_order;

        OrderUnionCursor cursor = new OrderUnionCursor(db.rawQuery(query_macizox2, null));

        cursor.moveToNext();
      ordenesUnion= cursor.getOrdenesUnion();

        return ordenesUnion;
    }

//PARA OBTENER UNICA ORDEN_STATUS DEL ID
    public  Order_Status getOneOrderStatus(String id_status) {

        Order_Status order_status;

        OrderStatusCursor cursor = new OrderStatusCursor(db.query(InventoryDbSchema.OrderStatus_Table.NAME,
                null,
                InventoryDbSchema.OrderStatus_Table.Columns.ID + "=?",
                new String[] {id_status},
                null,
                null,
                null));

     cursor.moveToNext();
        order_status= cursor.getOrderStatus();
        return order_status;
    }

    public  void  updateStatusOrderId(String id_order, String new_status)
    {


        ContentValues values = new ContentValues();
        values.put(InventoryDbSchema.Orders_Table.Columns.STATUS_ID, Integer.valueOf(new_status));// asegura que siempre da correcto

        db.update(InventoryDbSchema.Orders_Table.NAME, values, InventoryDbSchema.Orders_Table.Columns.ID + " = ? ", new String[]{id_order});

    }

    public  void  AddChangeLog(String id_order, String new_changelog)
    {


        ContentValues values = new ContentValues();
        values.put(InventoryDbSchema.Orders_Table.Columns.CHANGE_LOG, new_changelog);// asegura que siempre da correcto

        db.update(InventoryDbSchema.Orders_Table.NAME, values, InventoryDbSchema.Orders_Table.Columns.ID + " = ?", new String[]{id_order});

    }


    public  String getOneOrderTable(String id_order) {

        String orders;
        //  Cursor cursor = db.rawQuery("SELECT * FROM categories ORDER BY id", null);

        String query_macizox2 = "SELECT change_log FROM orders where id= "+ id_order;

        OrdersCursor cursor = new OrdersCursor(db.rawQuery(query_macizox2, null));

        cursor.moveToNext();
        orders= cursor.getString(cursor.getColumnIndex("change_log"));


        return orders;
    }



    public List<AssemblieOrders_Union> getEnsambliesInOrder(String id_order ) {
            List<AssemblieOrders_Union> list = new ArrayList<AssemblieOrders_Union>();

            String new_macizo =
                    "SELECT a.id, a.assembly_id, ap.description, a.qty, (costo*a.qty) as total \n" +
                    "FROM order_assemblies a\n" +
                    "INNER JOIN assemblies ap ON (a.assembly_id = ap.id)\n" +
                    "INNER JOIN (SELECT ap.id, ap.description, sum(ac.price*dc.qty) as costo \n" +
                    "           FROM assemblies ap \n" +
                    "           INNER JOIN assembly_products dc  ON (dc.id= ap.id)\n" +
                    "           INNER JOIN products         ac  ON (ac.id= dc.product_id)\n" +
                    "           group by ap.id) z\n" +
                    "           ON (z.id= ap.id)     \n" +
                    "where a.id= "+ id_order + " ORDER BY ap.description ASC";


            OrderAssembliesUnionCursor cursor = new OrderAssembliesUnionCursor((db.rawQuery(new_macizo, null))
            );

            while (cursor.moveToNext()) {
                list.add((cursor.getAssemblieOrdersUnion()));  // metodo wrappcursor

            }
            cursor.close();
            return list;
        }


        // PARA ACTUALIZAR EL QTY DEL ENSAMBLE EN LA ORDEN
    public  void  updateQtyAssemblyFromOrder(String id_order, String id_assembly, int new_qty)
    {


        ContentValues values = new ContentValues();
        values.put(InventoryDbSchema.Order_Assemblies_Table.Columns.QUANTITY, new_qty);// asegura que siempre da correcto

        db.update(InventoryDbSchema.Order_Assemblies_Table.NAME, values, InventoryDbSchema.Order_Assemblies_Table.Columns.ID + " = ? AND " + InventoryDbSchema.Order_Assemblies_Table.Columns.ASSEMBLY_ID + " = ? " , new String[]{id_order,id_assembly});

    }

    // PARA ACTUALIZAR EL QTY DEL ENSAMBLE EN LA ORDEN
    public  void  DeleteAssemblyFromOrder(String id_order, String id_assembly)
    {

        db.delete(InventoryDbSchema.Order_Assemblies_Table.NAME, "id = ? AND assembly_id =?", new String[] {id_order,id_assembly});

    }

    public  int getNumberOfEnsamblesOnActualOrder(String id_order, String id_assembly) {

        //  Cursor cursor = db.rawQuery("SELECT * FROM categories ORDER BY id", null);
        int qty_product=0;
        String query = "SELECT qty FROM order_assemblies WHERE id= " + id_order + " AND assembly_id=" + id_assembly;

        Cursor cursor = (db.rawQuery(query, null));

        if (cursor.getCount() >= 1) {
            cursor.moveToFirst();
            qty_product= cursor.getInt(cursor.getColumnIndex("qty"));

        }
        else{
            qty_product=0;
        }
        //List<Products> list = new ArrayList<Products>();
        return qty_product;


    }

    // FUNCION PARA AÑADIR NUEVA ORDEN
    public void AddOrder(int order_id,int customer_id,String date)
    {
        ///Agregar un elemento a la base de datos

        db =inventoryHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(InventoryDbSchema.Orders_Table.Columns.ID, order_id);
        contentValues.put(InventoryDbSchema.Orders_Table.Columns.STATUS_ID, 0);
        contentValues.put(InventoryDbSchema.Orders_Table.Columns.COSTUMER_ID, customer_id);
        contentValues.put(InventoryDbSchema.Orders_Table.Columns.DATE, date);

        db.insert(InventoryDbSchema.Orders_Table.NAME, null, contentValues);

        // Cursor cursor = new CategoryCursor((db.insert("categories", null , contentValues )));
    }

    // FUNCION PARA AÑADIR NUEVOS ENSAMBLE A LA ORDEN
    public void AddOrderAssembly(String id_order,String assembly_id)
    {
        ///Agregar un elemento a la base de datos
        db =inventoryHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(InventoryDbSchema.Order_Assemblies_Table.Columns.ID, Integer.valueOf(id_order));
        contentValues.put(InventoryDbSchema.Order_Assemblies_Table.Columns.ASSEMBLY_ID, Integer.valueOf(assembly_id));
        contentValues.put(InventoryDbSchema.Order_Assemblies_Table.Columns.QUANTITY, 1);

        db.insert(InventoryDbSchema.Order_Assemblies_Table.NAME, null, contentValues);
        // Cursor cursor = new CategoryCursor((db.insert("categories", null , contentValues )));
    }

    public void deleteOrders (String id)
    {
        //ELIMINA LA CATEGORIA
        db.delete(InventoryDbSchema.Order_Assemblies_Table.NAME , InventoryDbSchema.Order_Assemblies_Table.Columns.ID+" = ?", new String[] {id});

        //elimina primero los products internos
        db.delete(InventoryDbSchema.Orders_Table.NAME , InventoryDbSchema.Orders_Table.Columns.ID+" = ?", new String[] {id});


    }



    public void AddAssemblyOrder(int id_order, int id_ensamble, int qty)
    {
        ///Agregar un elemento a la base de datos
        db =inventoryHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(InventoryDbSchema.Order_Assemblies_Table.Columns.ID, id_order);
        contentValues.put(InventoryDbSchema.Order_Assemblies_Table.Columns.ASSEMBLY_ID, id_ensamble);
        contentValues.put(InventoryDbSchema.Order_Assemblies_Table.Columns.QUANTITY, qty);

        db.insert(InventoryDbSchema.Order_Assemblies_Table.NAME, null, contentValues);
        // Cursor cursor = new CategoryCursor((db.insert("categories", null , contentValues )));
    }

    public void deleteAllAssembliesFromOrder( String id_ensamble) {

        db.delete(InventoryDbSchema.Order_Assemblies_Table.NAME, InventoryDbSchema.Order_Assemblies_Table.Columns.ID +" = ? ", new String[] {id_ensamble});
    }



    public List<Products> getMissing_products()
    {

        List<Products> list = new ArrayList<Products>();

        String query = "SELECT new_id as id, category_id, description, abs(qty_stock-sum (qty_orders*qty_ensambles))*price_one as price , qty_stock-sum (qty_orders*qty_ensambles) as qty\n" +
                "FROM (\n" +
                "SELECT * FROM (\n" +
                "SELECT a.id as new_id, a.category_id,  a.description, a.price as price_one ,  c.id as id_order, b.qty as qty_ensambles, c.qty as qty_orders, a.qty as qty_stock, c.id as id_order\n" +
                "FROM  products a\n" +
                "INNER JOIN assembly_products b ON ( a.id = b.product_id) \n" +
                "INNER JOIN order_assemblies  c ON ( c.assembly_id = b.id)\n" +
                "order by a.id ) INNER JOIN orders d ON (id_order = d.id)\n" +
                "where status_id>=2 ) GROUP BY description HAVING qty<0 ORDER BY description ASC";

        ProductCursor cursor = new ProductCursor(db.rawQuery(query, null));

        while (cursor.moveToNext()){
            list.add((cursor.getProduct()));  // metodo wrappcursor
        }
        cursor.close();

        return list;

    }

    public  SalesMonth getSalesMonth(String fecha_inicio,String fecha_final) {

        SalesMonth salesMonth;
        //  Cursor cursor = db.rawQuery("SELECT * FROM categories ORDER BY id", null);

        String query_macizox3 = "SELECT COUNT(*) as sale, sum(costo) as total FROM (\n" +
                "SELECT a.id,b.id as id_status,  b.description as status_description , e.id as id_customer,  e.first_name, e.last_name, sum(c.qty * p.price* ap.qty) as costo, a.date  \n" +
                "FROM orders                 a\n" +
                "INNER JOIN  order_status    b ON ( a.status_id = b.id )  \n" +
                "INNER JOIN order_assemblies c ON ( a.id= c.id)\n" +
                "INNER JOIN assemblies       d ON ( c.assembly_id = d.id)\n" +
                "INNER JOIN customers        e ON ( a.customer_id = e.id) \n" +
                "INNER JOIN assembly_products ap ON (d.id = ap.id)\n" +
                "INNER JOIN products p ON     (p.id=ap.product_id)\n" +
                "GROUP BY a.id HAVING a.date BETWEEN date('"+fecha_inicio+"') AND date('"+fecha_final+"') AND  id_status>=2   ORDER BY date(a.date) DESC)";

        SalesMonthCursor cursor = new SalesMonthCursor(db.rawQuery(query_macizox3, null));

        cursor.moveToNext();

        salesMonth= cursor.getSalesMonth();


        return salesMonth;
    }


    public List<OrdenesUnion> getSalesMonthOrdersUnion(String date_begin, String date_end ) {
        List<OrdenesUnion> list = new ArrayList<OrdenesUnion>();

        String between_two_Dates = "SELECT a.id,b.id as id_status,  b.description as status_description , e.id as id_customer,  e.first_name, e.last_name, sum(c.qty * p.price* ap.qty) as costo, a.date  \n" +
                "FROM orders                 a\n" +
                "INNER JOIN  order_status    b ON ( a.status_id = b.id )  \n" +
                "INNER JOIN order_assemblies c ON ( a.id= c.id)\n" +
                "INNER JOIN assemblies       d ON ( c.assembly_id = d.id)\n" +
                "INNER JOIN customers        e ON ( a.customer_id = e.id) \n" +
                "INNER JOIN assembly_products ap ON (d.id = ap.id)\n" +
                "INNER JOIN products p ON     (p.id=ap.product_id)\n" +
                "GROUP BY a.id HAVING a.date BETWEEN date('"+date_begin+ "') AND date('" + date_end+"') AND  id_status>=2   ORDER BY date(a.date) DESC\n";
        OrderUnionCursor cursor = new OrderUnionCursor((db.rawQuery(between_two_Dates, null))
        );

        while (cursor.moveToNext()) {
            list.add((cursor.getOrdenesUnion()));  // metodo wrappcursor

        }
        cursor.close();
        return list;
    }


    public List<Ensambles_vendidos_Mes> getListEnsamblesVendidosMes(String date_begin, String date_end ) {
        List<Ensambles_vendidos_Mes> list = new ArrayList<Ensambles_vendidos_Mes>();

        String between_two_Dates = "SELECT a.id, d.description , c.qty, a.date, sum (c.qty*ap.qty*p.price) as price_assembly\n" +
                "FROM orders                 a\n" +
                "INNER JOIN  order_status    b ON ( a.status_id = b.id )  \n" +
                "INNER JOIN order_assemblies c ON ( a.id= c.id)\n" +
                "INNER JOIN assemblies       d ON ( c.assembly_id = d.id) \n" +
                "INNER JOIN assembly_products ap ON (d.id = ap.id)\n" +
                "INNER JOIN products p         ON  (p.id=ap.product_id)\n" +
                " where   a.date BETWEEN date('"+date_begin+"') AND date('"+ date_end+ "') AND a.status_id>= 2 GROUP BY d.description ORDER BY c.qty DESC" ;

        EnsamblesVendidosCursor cursor= new EnsamblesVendidosCursor((db.rawQuery(between_two_Dates, null))
        );

        while (cursor.moveToNext()) {
            list.add((cursor.getEnsamblesVendidosMes()));  // metodo wrappcursor

        }
        cursor.close();
        return list;
    }

    // SIMULACION

    public void create_actual_temporal_stock()
    {
        String query = "CREATE TABLE tabla1 AS \n" +
                "\n" +
                "SELECT a.id as id,a.category_id as category_id,a.description as description,\n" +
                "IFNULL(b.price,a.price)  as price,\n" +
                "IFNULL(b.qty,a.qty)  as qty  FROM \n" +
                "(  SELECT a.id as id, a.category_id as category_id,  a.description as description, a.price as price,a.qty as qty FROM  ( SELECT a.id as id, a.category_id,  a.description, a.price as price,a.qty as qty, c.id as id_order\n" +
                "                FROM  products a\n" +
                "                INNER JOIN assembly_products b ON ( a.id = b.product_id) \n" +
                "                INNER JOIN order_assemblies  c ON ( c.assembly_id = b.id)\n" +
                "                order by a.id )a INNER JOIN orders d ON (id_order = d.id)\n" +
                "                where status_id<2) a\n" +
                "\n" +
                "LEFT OUTER JOIN \n" +
                "(\n" +
                "SELECT new_id as id, category_id,id_order, description, price_one as price , qty_stock-sum (qty_orders*qty_ensambles) as qty\n" +
                "                FROM (\n" +
                "                SELECT * FROM  (\n" +
                "                SELECT a.id as new_id, a.category_id,  a.description, a.price as price_one ,  c.id as id_order, b.qty as qty_ensambles, c.qty as qty_orders, a.qty as qty_stock, c.id as id_order\n" +
                "                FROM  products a\n" +
                "                INNER JOIN assembly_products b ON ( a.id = b.product_id) \n" +
                "                INNER JOIN order_assemblies  c ON ( c.assembly_id = b.id)\n" +
                "                order by a.id ) INNER JOIN orders d ON (id_order = d.id)\n" +
                "                where status_id>=2 ) GROUP BY description  ORDER BY description ASC\n" +
                "                )b ON a.id = b.id";

        // where status_id>=2
//abs(qty_stock-sum (qty_orders*qty_ensambles))*price_one as price
        db.execSQL(query);
    }
    // CREAR TABLA TEMPORAL 2 VACIA (PRODUCTOS REQUERIDOS)

    public void crear_tabla_2()
    {
        String query = "CREATE TABLE tabla2 AS SELECT * from products where 0";

       db.execSQL(query);

    }



    // BORRAR TABLAS TEMPORALES

    public void drop1()
    {
        String query = "DROP TABLE IF EXISTS tabla1";

       db.execSQL(query);

    }
    public void drop2()
    {
        String query = "DROP TABLE IF EXISTS tabla2";

        db.execSQL(query);

    }

    public void insert_products(int order_id)
    {
        String query = "INSERT INTO tabla2 SELECT c.id as id,c.category_id as category_id, c.description as description,(sum (a.qty*b.qty))*c.price as price, sum (a.qty*b.qty) as qty\n" +
                "FROM order_assemblies a\n" +
                "INNER JOIN assembly_products b ON (a.assembly_id = b.id)\n" +
                "INNER JOIN products c ON (b.product_id = c.id)  WHERE a.id ="+ order_id + " GROUP BY description  ORDER BY description ASC\n";

        db.execSQL(query);

    }




    public List<Products> getMissing_products_of_order(int id_order)
    {

        List<Products> list = new ArrayList<Products>();

        String query = "SELECT c.id as id,c.category_id as category_id,c.description as description ,c.price as price,c.qty as qty\n" +
                "FROM order_assemblies a\n" +
                "INNER JOIN assembly_products b ON (a.assembly_id = b.id)\n" +
                "INNER JOIN (SELECT a.id,a.category_id,a.description,abs(IFNULL(b.qty,0) - IFNULL(a.qty,0))*b.price as price,IFNULL(b.qty,0) - IFNULL(a.qty,0) as qty\n" +
                "FROM (\n" +
                "SELECT id, category_id, description,price, sum(qty) as qty FROM tabla2 GROUP BY description\n" +
                ") a\n" +
                "LEFT OUTER JOIN tabla1 b ON a.id = b.id ) c ON (b.product_id = c.id) where a.id ="+id_order+ " AND c.qty<0 GROUP BY description";

        ProductCursor cursor = new ProductCursor(db.rawQuery(query, null));

        while (cursor.moveToNext()){
            list.add((cursor.getProduct()));  // metodo wrappcursor
        }
        cursor.close();

        return list;

    }

    public List<Products> getproductsofordervalidation(int id_order)
    {

        List<Products> list = new ArrayList<Products>();

        String query = "SELECT c.id as id,c.category_id as category_id, c.description as description,c.price as price, c.qty as qty\n" +
                "FROM order_assemblies a\n" +
                "INNER JOIN assembly_products b ON (a.assembly_id = b.id)\n" +
                "INNER JOIN products c ON (b.product_id = c.id) WHERE a.id ="+id_order+ " GROUP BY description  ORDER BY description ASC";

        ProductCursor cursor = new ProductCursor(db.rawQuery(query, null));

        while (cursor.moveToNext()){
            list.add((cursor.getProduct()));  // metodo wrappcursor
        }
        cursor.close();

        return list;

    }

//***************************************************************************************************
//***************************************************************************************************
//***************************************************************************************************
} // END FINAL DEL MUNDO UNIVERSAL DEL COSMOS