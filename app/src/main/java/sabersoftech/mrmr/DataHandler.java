package sabersoftech.mrmr;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataHandler {

    /**
     * CART_ITEM_TABLE Table fields
     ********************************************************************************************************/
    public static final String ID = "ID";
    public static final String ORDER_ID = "ORDER_ID";
    public static final String ORDER_DATE = "ORDER_DATE";
    public static final String PRODUCT_ID = "PRODUCT_ID";
    public static final String PRODUCT_NAME = "PRODUCT_NAME";
    public static final String PRODUCT_MRP = "PRODUCT_MRP";
    public static final String PRODUCT_QTY = "PRODUCT_QTY";


    /**
     * Database Details
     ********************************************************************************************************/
    public static final String DATABASE_NAME = "MR_DB";
    public static final int DATABASE_VERSION = 1;

    private static final String[] ALL_TABLES = {"CART_ITEM_TABLE"};

    /**
     * Table Names
     ********************************************************************************************************/
    public static final String CART_ITEM_TABLE = "CART_ITEM_TABLE";


    /**
     * Query to create Tables
     ********************************************************************************************************/
    public static final String CART_ITEM_TABLE_CREATE = "CREATE TABLE `CART_ITEM_TABLE` (`ID` INTEGER PRIMARY KEY AUTOINCREMENT,`ORDER_ID`	TEXT,`ORDER_DATE`	TEXT,`PRODUCT_ID`	TEXT,`PRODUCT_NAME`	TEXT,`PRODUCT_MRP`	TEXT,`PRODUCT_QTY`	TEXT);";

    DataBaseHelper dbhelper;
    Context ctx;

    SQLiteDatabase db;


    public DataHandler(Context ctx) {
        this.ctx = ctx;
        dbhelper = new DataBaseHelper(ctx);
    }


    private static class DataBaseHelper extends SQLiteOpenHelper {


        public DataBaseHelper(Context ctx) {
            // TODO Auto-generated constructor stub
            super(ctx, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            // Creating tables
            try {
                db.execSQL(CART_ITEM_TABLE_CREATE);

            } catch (SQLException e) {
                // TODO: handle exception
                e.printStackTrace();
            }

        }


        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // TODO Auto-generated method stub

            for (String table : ALL_TABLES) {
                db.execSQL("DROP TABLE IF EXISTS " + table);
            }
            onCreate(db);
        }

    }

    public DataHandler open() {
        db = dbhelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbhelper.close();
    }


    /**
     * Table Functions
     ********************************************************************************************************/

    public void InsertIntoCart(String ORDER_ID, String ORDER_DATE, String PRODUCT_ID, String PRODUCT_NAME, String PRODUCT_MRP, String PRODUCT_QTY) {
        ContentValues content = new ContentValues();
        content.put("ORDER_ID", ORDER_ID);
        content.put("ORDER_DATE", ORDER_DATE);
        content.put("PRODUCT_ID", PRODUCT_ID);
        content.put("PRODUCT_NAME", PRODUCT_NAME);
        content.put("PRODUCT_MRP", PRODUCT_MRP);
        content.put("PRODUCT_QTY", PRODUCT_QTY);
        db.insertOrThrow(CART_ITEM_TABLE, null, content);
    }


    public Cursor returnData() {
        return db.query(CART_ITEM_TABLE, new String[]{ORDER_ID, ORDER_DATE, PRODUCT_ID, PRODUCT_NAME, PRODUCT_MRP, PRODUCT_QTY}, null, null, null, null, null);
    }

    public int returnDataCount() {
        return db.rawQuery("select * from " + CART_ITEM_TABLE + ";", null).getCount();
    }

    public int check(String ORDER_ID, String ORDER_DATE, String PRODUCT_ID) {
        return db.rawQuery("SELECT* FROM CART_ITEM_TABLE where ORDER_ID = '"+ORDER_ID+"' and ORDER_DATE = '"+ORDER_DATE+"' and PRODUCT_ID ='"+PRODUCT_ID+"';", null).getCount();
    }

    public void delete(String mORDER_ID, String mORDER_DATE, String mPRODUCT_ID) {
        db.delete(CART_ITEM_TABLE, ORDER_ID + "=? AND " + ORDER_DATE + "=? AND " + PRODUCT_ID + "=?", new String[]{mORDER_ID, mORDER_DATE, mPRODUCT_ID});
    }

    public void deleteAll(){
        db.execSQL("delete from "+CART_ITEM_TABLE);
    }


}


