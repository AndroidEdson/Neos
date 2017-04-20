package com.azore.compustore.fiuady.db;

/**
 * Created by Armín on 28/03/2017.
 */

public final class InventoryDbSchema {

// SECCION CREADA PARA CONOCER TODOS LOS PARAMETROS DE LA TABLA PARA HACER MAS FACIL LA PROGRMACION DE SUS VARIABLES
    //______________________________________________________________________________________

    public static final class Categories_Table{
        public static final String NAME = "product_categories";

        public static final class Columns
        {
            public static final String ID = "id";
            public static final String DESCRIPTION = "description";
        }

    }

    // ____________________________________________________________

    public static final class Products_Table {
        public static final String NAME = "products";

        public static final class Columns {
            public static final String ID = "id";
            public static final String CATEGORY_ID = "category_id";
            public static final String DESCRITPION = "description";
            public static final String PRICE = "price";
            public static final String QUANTITY = "qty";

        }
    }

    // _____________________________________________________________________________

        public static final class Assemblies_Table{
            public static final String NAME = "assemblies";

            public static final class Columns
            {
                public static final String ID = "id";
                public static final String DESCRIPTION = "description";
            }

    }


    // _____________________________________________________________________________


    public static final class AssemblyProducts_Table {
        public static final String NAME = "assembly_products";

        public static final class Columns {
            public static final String ID = "id";
            public static final String PRODUCT_ID = "product_id";
            public static final String QUANTITY = "qty";

        }
    }

    // _____________________________________________________________________________


    public static final class Customers_Table {
        public static final String NAME = "customers";

        public static final class Columns {
            public static final String ID = "id";
            public static final String FIRST_NAME = "first_name";
            public static final String LAST_NAME = "last_name";
            public static final String ADDRESS = "address";
            public static final String PHONE1 = "phone1";
            public static final String PHONE2 = "phone2";
            public static final String PHONE3 = "phone3";
            public static final String EMAIL = "e_mail";

        }
    }

    // _____________________________________________________________________________


    public static final class OrderStatus_Table {
        public static final String NAME = "order_status";

        public static final class Columns {
            public static final String ID = "id";
            public static final String DESCRIPTION = "description";
            public static final String EDITABLE = "editable";
            public static final String PREVIOUS = "previous";
            public static final String NEXT = "next";

        }
    }

    // _____________________________________________________________________________

    public static final class Orders_Table {
        public static final String NAME = "orders";

        public static final class Columns {
            public static final String ID = "id";
            public static final String STATUS_ID = "status_id";
            public static final String COSTUMER_ID = "customer_id";
            public static final String DATE = "date";
            public static final String CHANGE_LOG = "change_log";

        }
    }


    // _____________________________________________________________________________
    public static final class Order_Assemblies_Table {
        public static final String NAME = "order_assemblies";

        public static final class Columns {
            public static final String ID = "id";
            public static final String ASSEMBLY_ID = "assembly_id";
            public static final String QUANTITY = "qty";
        }
    }



    public static final class OrdenesUnion_Table {

        public static final class Columns {
            public static final String ID = "id";
            public static final String ID_STATUS = "id_status";
            public static final String STATUS_DESCRIPTION = "status_description";
            public static final String ID_CUSTOMER = "id_customer";
            public static final String FIRST_NAME = "first_name";
            public static final String LAST_NAME = "last_name";
            public static final String COSTO = "costo";
            public static final String DATE = "date";

        }
    }

    public static final class AssemblieOrders_Union_Table {

        public static final class Columns {
            public static final String ID = "id";
            public static final String ASSEMBLY_ID = "assembly_id";
            public static final String DESCRIPTION = "description";
            public static final String QTY = "qty";
            public static final String TOTAL = "total";

        }
    }

    public static final class SalesMonth {

        public static final class Columns {
            public static final String COUNT = "sale";
            public static final String GAIN = "total";
        }
    }



//FIN DE LAS TABLAS______________________________________________________________________________
}
