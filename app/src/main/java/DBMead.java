import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;


/**
 * Created by ctilluma on 10/8/16.
 */

public class DBMead extends SQLiteOpenHelper {
    //Statics
    public static final String DATABASE_NAME = "MeadDB.db";
    private static final int DATABASE_VERSION      = 1;

    //Database Tests Table
    public static final String TESTS_TABLE_NAME = "tests";
    public static final String TESTS_COLUMN_ID = "id";
    public static final String TESTS_COLUMN_SG = "sg";
    public static final String TESTS_COLUMN_DATE = "date";
    public static final String TESTS_COLUMN_MEADID = "meadID";

    //Database Honey Table
    public static final String HONEY_TABLE_NAME = "honey";
    public static final String HONEY_COLUMN_ID = "id";
    public static final String HONEY_COLUMN_BRIX = "brix";
    public static final String HONEY_COLUMN_NAME = "name";
    public static final String HONEY_COLUMN_FLAVOUR = "flavor";

    //Database Honey Additions
    public static final String HONEY_ADD_TABLE_NAME = "honeyAdditions";
    public static final String HONEY_ADD_COLUMN_ID = "id";
    public static final String HONEY_ADD_COLUMN_HONEYID = "honeyID";
    public static final String HONEY_ADD_COLUMN_MEADID = "meadID";
    public static final String HONEY_ADD_COLUMN_VOLUME = "volume";

    //Database Mead Table
    public static final String MEAD_TABLE_NAME = "mead";
    public static final String MEAD_COLUMN_ID = "id";
    public static final String MEAD_COLUMN_NAME = "name";
    public static final String MEAD_COLUMN_OG = "og";
    public static final String MEAD_COLUMN_CAPACITY = "capacity";
    public static final String MEAD_COLUMN_VOLUME = "volume";
    public static final String MEAD_COLUMN_ALCOHOL = "abv";

    //Database Inventory
    public static final String INVENTORY_TABLE_NAME = "inventory";
    public static final String INVENTORY_COLUMN_ID = "id";
    public static final String INVENTORY_COLUMN_MEADID = "meadID";
    public static final String INVENTORY_COLUMN_VOLUME = "volume";
    public static final String INVENTORY_COLUMN_QUANTITY = "quantity";

    //Database Upgrade STATICS
    private static final String DATABASE_ALTER_TESTS_1 = "ALTER TABLE "
            + TESTS_TABLE_NAME + " ADD COLUMN " + "" + " string;";
    private static final String DATABASE_ALTER_HONEY_1 = "ALTER TABLE "
            + HONEY_TABLE_NAME + " ADD COLUMN " + "" + " string;";
    private static final String DATABASE_ALTER_HONEY_ADD_1 = "ALTER TABLE "
            + HONEY_ADD_TABLE_NAME + " ADD COLUMN " + "" + " string;";
    private static final String DATABASE_ALTER_MEAD_1 = "ALTER TABLE "
            + MEAD_TABLE_NAME + " ADD COLUMN " + "" + " string;";
    private static final String DATABASE_ALTER_INVENTORY_1 = "ALTER TABLE "
            + INVENTORY_TABLE_NAME + " ADD COLUMN " + "" + " string;";

    //Variables 
    
    
    //Constructor
    public DBMead(Context context)
    {
        super(context, DATABASE_NAME , null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Create Tables
        //TESTS TABLE
        db.execSQL( "CREATE TABLE " + TESTS_TABLE_NAME + " (" +
                TESTS_COLUMN_ID + " INTEGER PRIMARY KEY, " +
                TESTS_COLUMN_SG + " REAL, " +
                TESTS_COLUMN_DATE + " INT, " +
                TESTS_COLUMN_MEADID + " INT)" );

        //HONEY TABLE
        db.execSQL( "CREATE TABLE " + HONEY_TABLE_NAME + " (" +
                HONEY_COLUMN_ID + " INTEGER PRIMARY KEY, " +
                HONEY_COLUMN_BRIX + " REAL, " +
                HONEY_COLUMN_NAME + " TEXT, " +
                HONEY_COLUMN_FLAVOUR + " TEXT)" );

        //HONEY ADD TABLE
        db.execSQL( "CREATE TABLE " + HONEY_ADD_TABLE_NAME + " (" +
                HONEY_ADD_COLUMN_ID + " INTEGER PRIMARY KEY, " +
                HONEY_ADD_COLUMN_HONEYID + " INTEGER, " +
                HONEY_ADD_COLUMN_MEADID + " INTEGER, " +
                HONEY_ADD_COLUMN_VOLUME + " REAL)" );

        //MEAD TABLE
        db.execSQL( "CREATE TABLE " + MEAD_TABLE_NAME + " (" +
                 MEAD_COLUMN_ID + " INTEGER PRIMARY KEY, " +
                MEAD_COLUMN_NAME + " INTEGER, " +
                MEAD_COLUMN_OG + " REAL, " +
                MEAD_COLUMN_CAPACITY + " REAL, " +
                MEAD_COLUMN_VOLUME + " REAL, " +
                MEAD_COLUMN_ALCOHOL + " REAL)" );

        //INVENTORY TABLE
        db.execSQL( "CREATE TABLE " + INVENTORY_TABLE_NAME + " (" +
                INVENTORY_COLUMN_ID + " INTEGER PRIMARY KEY, " +
                INVENTORY_COLUMN_MEADID + " INTEGER, " +
                INVENTORY_COLUMN_VOLUME + " REAL, " +
                INVENTORY_COLUMN_QUANTITY + " INTEGER)" );
    }

    //Handle Upgrades for each revision add additional if oldVersion
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Drop Tables
        if (oldVersion < 2) { //Run these table updates when upgrading to 2
            db.execSQL(DATABASE_ALTER_TESTS_1);
            db.execSQL(DATABASE_ALTER_HONEY_1);
            db.execSQL(DATABASE_ALTER_HONEY_ADD_1);
            db.execSQL(DATABASE_ALTER_MEAD_1);
            db.execSQL(DATABASE_ALTER_INVENTORY_1);
        }
    }


    //Table Insertions (CREATE)
    public long insertTest  (double sg, int date, int meadID)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TESTS_COLUMN_SG, sg);
        contentValues.put(TESTS_COLUMN_DATE, date);
        contentValues.put(TESTS_COLUMN_MEADID, meadID);
        return db.insert(TESTS_TABLE_NAME, null, contentValues);
    }

    public long insertHoney  (double brix, String name, String flavour)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(HONEY_COLUMN_BRIX, brix);
        contentValues.put(HONEY_COLUMN_NAME, name);
        contentValues.put(HONEY_COLUMN_FLAVOUR, flavour);
        return db.insert(HONEY_TABLE_NAME, null, contentValues);
    }

    public long insertHoneyAddition  (long honeyID, long meadID, double volume)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(HONEY_ADD_COLUMN_HONEYID, honeyID);
        contentValues.put(HONEY_ADD_COLUMN_MEADID, meadID);
        contentValues.put(HONEY_ADD_COLUMN_VOLUME, volume);
        return db.insert(HONEY_ADD_TABLE_NAME, null, contentValues);
    }

    public long insertMead  (String name, double og, double capacity, double volume, double alcohol)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(MEAD_COLUMN_NAME, name);
        contentValues.put(MEAD_COLUMN_OG, og);
        contentValues.put(MEAD_COLUMN_CAPACITY, capacity);
        contentValues.put(MEAD_COLUMN_VOLUME, volume);
        contentValues.put(MEAD_COLUMN_ALCOHOL, alcohol);
        return db.insert(MEAD_TABLE_NAME, null, contentValues);
    }

    public long insertInventory  (long meadID, double volume, double quantity)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(INVENTORY_COLUMN_MEADID, meadID);
        contentValues.put(INVENTORY_COLUMN_VOLUME, volume);
        contentValues.put(INVENTORY_COLUMN_QUANTITY, quantity);
        return db.insert(INVENTORY_TABLE_NAME, null, contentValues);
    }

    //Cursor Queries (READ)
    public Cursor getTestData(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "SELECT * FROM " + TESTS_TABLE_NAME + " WHERE " + TESTS_COLUMN_ID + " = " + id + "", null );
        return res;
    }

    public Cursor getHoneyData(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "SELECT * FROM " + HONEY_TABLE_NAME + " WHERE " + HONEY_COLUMN_ID + " = " + id + "", null );
        return res;
    }

    public Cursor getHoneyAddData(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "SELECT * FROM " + HONEY_ADD_TABLE_NAME + " WHERE " + HONEY_ADD_COLUMN_ID + " = " + id + "", null );
        return res;
    }

    public Cursor getMeadData(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "SELECT * FROM " + MEAD_TABLE_NAME + " WHERE " + MEAD_COLUMN_ID + " = " + id + "", null );
        return res;
    }

    public Cursor getInventoryData(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "SELECT * FROM " + INVENTORY_TABLE_NAME + " WHERE " + INVENTORY_COLUMN_ID + " = " + id + "", null );
        return res;
    }

    // Get Rows
    public int numberOfTestRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, TESTS_TABLE_NAME);
        return numRows;
    }

    public int numberOfHoneyRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, HONEY_TABLE_NAME);
        return numRows;
    }

    public int numberOfHoneyAdditionRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, HONEY_ADD_TABLE_NAME);
        return numRows;
    }

    public int numberOfMeadRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, MEAD_TABLE_NAME);
        return numRows;
    }

    public int numberOfInventoryRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, INVENTORY_TABLE_NAME);
        return numRows;
    }

    // Update Tables (UPDATE)
    public int updateTest (int id, double sg, int date, long meadID)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TESTS_COLUMN_SG, sg);
        contentValues.put(TESTS_COLUMN_DATE, date);
        contentValues.put(TESTS_COLUMN_MEADID, meadID);
        return db.update(TESTS_TABLE_NAME, contentValues, TESTS_COLUMN_ID + " = ? ", new String[]{Integer.toString(id)});
    }

    public int updateHoney (int id, double brix, String name, String flavour)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(HONEY_COLUMN_BRIX, brix);
        contentValues.put(HONEY_COLUMN_NAME, name);
        contentValues.put(HONEY_COLUMN_FLAVOUR, flavour);
        return db.update(HONEY_TABLE_NAME, contentValues, HONEY_COLUMN_ID + " = ? ", new String[]{Integer.toString(id)});
    }

    public int updateHoneyAdd (int id, long honeyID, long meadID, double volume)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(HONEY_ADD_COLUMN_HONEYID, honeyID);
        contentValues.put(HONEY_ADD_COLUMN_MEADID, meadID);
        contentValues.put(HONEY_ADD_COLUMN_VOLUME, volume);
        return db.update(HONEY_ADD_TABLE_NAME, contentValues, HONEY_ADD_COLUMN_ID + " = ? ", new String[]{Integer.toString(id)});
    }

    public int updateMead (int id, String name, double og, double capacity, double volume, double alcohol)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(MEAD_COLUMN_NAME, name);
        contentValues.put(MEAD_COLUMN_OG, og);
        contentValues.put(MEAD_COLUMN_CAPACITY, capacity);
        contentValues.put(MEAD_COLUMN_VOLUME, volume);
        contentValues.put(MEAD_COLUMN_ALCOHOL, alcohol);
        return db.update(MEAD_TABLE_NAME, contentValues, MEAD_COLUMN_ID + " = ? ", new String[]{Integer.toString(id)});
    }

    public int updateInventory (int id, long meadID, double volume, double quantity)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(INVENTORY_COLUMN_MEADID, meadID);
        contentValues.put(INVENTORY_COLUMN_VOLUME, volume);
        contentValues.put(INVENTORY_COLUMN_QUANTITY, quantity);
        return db.update(INVENTORY_TABLE_NAME, contentValues, INVENTORY_COLUMN_ID + " = ? ", new String[]{Integer.toString(id)});
    }


    //Delete Records (DELETE)
    public Integer deleteTest (Integer id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TESTS_TABLE_NAME,
                TESTS_COLUMN_ID + " = ? ",
                new String[] { Integer.toString(id) });
    }

    public Integer deleteHoney (Integer id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(HONEY_ADD_TABLE_NAME,
                HONEY_ADD_COLUMN_HONEYID + " = ? ",
                new String[] { Integer.toString(id) });
        return db.delete(HONEY_TABLE_NAME,
                HONEY_COLUMN_ID + " = ? ",
                new String[] { Integer.toString(id) });
    }

    public Integer deleteHoneyAdd (Integer id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(HONEY_ADD_TABLE_NAME,
                HONEY_ADD_COLUMN_ID + " = ? ",
                new String[] { Integer.toString(id) });
    }

    public Integer deleteMead (Integer id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
         db.delete(HONEY_ADD_TABLE_NAME,
                HONEY_ADD_COLUMN_MEADID + " = ? ",
                new String[] { Integer.toString(id) });
         db.delete(INVENTORY_TABLE_NAME,
                INVENTORY_COLUMN_MEADID + " = ? ",
                new String[] { Integer.toString(id) });
        return db.delete(MEAD_TABLE_NAME,
                MEAD_COLUMN_ID + " = ? ",
                new String[] { Integer.toString(id) });
    }

    public Integer deleteInventory (Integer id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(INVENTORY_TABLE_NAME,
                INVENTORY_COLUMN_ID + " = ? ",
                new String[] { Integer.toString(id) });
    }

}
