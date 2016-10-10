package com.inspiredpanama.inspiredmead;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Debug;
import android.util.Log;
import android.widget.GridLayout;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;


/**
 * Created by ctilluma on 10/8/16.
 */

public class DBMead extends SQLiteOpenHelper {
    //Statics
    public static final String DATABASE_NAME = "MeadDB.db";
    private static final int DATABASE_VERSION      = 1;

    //Database Tests Table
    public static final String TESTS_TABLE_NAME = "tests";
    public static final String TESTS_COLUMN_ID = "_id";
    public static final String TESTS_COLUMN_SG = "sg";
    public static final String TESTS_COLUMN_DATE = "date";
    public static final String TESTS_COLUMN_MEADID = "meadID";

    //Database com.inspiredpanama.inspiredmead.Honey Table
    public static final String HONEY_TABLE_NAME = "honey";
    public static final String HONEY_COLUMN_ID = "_id";
    public static final String HONEY_COLUMN_BRIX = "brix";
    public static final String HONEY_COLUMN_NAME = "name";
    public static final String HONEY_COLUMN_FLAVOUR = "flavor";

    //Database com.inspiredpanama.inspiredmead.Honey Additions
    public static final String HONEY_ADD_TABLE_NAME = "honeyAdditions";
    public static final String HONEY_ADD_COLUMN_ID = "_id";
    public static final String HONEY_ADD_COLUMN_HONEYID = "honeyID";
    public static final String HONEY_ADD_COLUMN_MEADID = "meadID";
    public static final String HONEY_ADD_COLUMN_VOLUME = "volume";

    //Database com.inspiredpanama.inspiredmead.Mead Table
    public static final String MEAD_TABLE_NAME = "mead";
    public static final String MEAD_COLUMN_ID = "_id";
    public static final String MEAD_COLUMN_NAME = "name";
    public static final String MEAD_COLUMN_OG = "og";
    public static final String MEAD_COLUMN_CAPACITY = "capacity";
    public static final String MEAD_COLUMN_VOLUME = "volume";
    public static final String MEAD_COLUMN_ALCOHOL = "abv";
    public static final String MEAD_COLUMN_START = "brewDate";

    //Database Inventory
    public static final String INVENTORY_TABLE_NAME = "inventory";
    public static final String INVENTORY_COLUMN_ID = "_id";
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
                MEAD_COLUMN_ALCOHOL + " REAL, " +
                MEAD_COLUMN_START + " REAL)" );

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
    public long insertTest  (double sg, long date, long meadID)
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

    public long insertMead  (String name, double og, double capacity, double volume, double alcohol, long brewDate)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(MEAD_COLUMN_NAME, name);
        contentValues.put(MEAD_COLUMN_OG, og);
        contentValues.put(MEAD_COLUMN_CAPACITY, capacity);
        contentValues.put(MEAD_COLUMN_VOLUME, volume);
        contentValues.put(MEAD_COLUMN_ALCOHOL, alcohol);
        contentValues.put(MEAD_COLUMN_START, brewDate);
        return db.insertOrThrow(MEAD_TABLE_NAME, null, contentValues);
    }

    public long insertMead (Mead myMead) {
        //Variables
        SpecGravity myTest;
        long tempID, meadID;
        Honey myHoney;

        //Insert Record to get MeadID for other tables and setID
        meadID = insertMead(myMead.getName(), myMead.getOG(), myMead.getCapacity(), myMead.getVolume(), myMead.getAlcohol(), myMead.getStartDate().getTimeInMillis());

        // Interate Test Results and add to Database
        if (myMead.getTestResults() != null) {
            for (int i=0;i<myMead.getTestResults().size();i++) {
                //Set local for easier reading
                myTest = myMead.getTestResults().get(i);
                tempID = insertTest(myTest.getTestGravity(), myTest.getTestDate().getTimeInMillis(),meadID);
            }
        }

        //Iterate Honey Additions and add to Database
        if (myMead.getHoney() != null) {
            for (int i=0;i<myMead.getHoney().size();i++) {
                // Set local for easier reading
                myHoney = myMead.getHoney().get(i);
                tempID = insertHoneyAddition(myHoney.getHoneyID(),meadID,myHoney.getVolume());
            }
        }

        //Return meadID
        return meadID;
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
    public Cursor getTestData(long id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "SELECT * FROM " + TESTS_TABLE_NAME + " WHERE " + TESTS_COLUMN_ID + " = " + id + "", null );
        return res;
    }

    public Cursor getHoneyData(long id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "SELECT * FROM " + HONEY_TABLE_NAME + " WHERE " + HONEY_COLUMN_ID + " = " + id + "", null );
        return res;
    }

    public Cursor getHoneyAddData(long id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "SELECT * FROM " + HONEY_ADD_TABLE_NAME + " WHERE " + HONEY_ADD_COLUMN_ID + " = " + id + "", null );
        return res;
    }

    public Cursor getMeadData(long id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "SELECT * FROM " + MEAD_TABLE_NAME + " WHERE " + MEAD_COLUMN_ID + " = " + id + "", null );
        return res;
    }

    public Cursor getInventoryData(long id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "SELECT * FROM " + INVENTORY_TABLE_NAME + " WHERE " + INVENTORY_COLUMN_ID + " = " + String.valueOf(id) + "", null );
        return res;
    }

    // Get Object Record
    public Honey getHoneyRecord(Cursor current) {
        if (current.moveToFirst()) {
            if ((current.getString(current.getColumnIndex(HONEY_COLUMN_ID)) != null) && (!current.getString(current.getColumnIndex(HONEY_COLUMN_ID)).isEmpty())) {
                long id = current.getLong(current.getColumnIndex(HONEY_COLUMN_ID));
            } else {
                long id = current.getLong(current.getColumnIndex(HONEY_TABLE_NAME + "." + HONEY_COLUMN_ID));
            }
            long id = current.getLong(current.getColumnIndex(HONEY_COLUMN_ID));
            double brix = current.getDouble(current.getColumnIndex(HONEY_COLUMN_BRIX));
            String name = current.getString(current.getColumnIndex(HONEY_COLUMN_NAME));
            String flavour = current.getString(current.getColumnIndex(HONEY_COLUMN_FLAVOUR));

            Honey newHoney = new Honey(id, brix, name, flavour);
            newHoney.setHoneyID(id);

            // Return Honey Object
            return newHoney;
        } else return null;
    }

    public List<Honey> getHoneyAdditionsFromMead(long meadID) {
        List<Honey> honeyList = new ArrayList<Honey>();

        //Select HoneyAdditions
        String selectQuery = "SELECT " +
                HONEY_TABLE_NAME + "." + HONEY_COLUMN_ID + ", " +
                HONEY_ADD_TABLE_NAME + "." + HONEY_ADD_COLUMN_ID + ", " +
                HONEY_ADD_COLUMN_VOLUME + ", " +
                HONEY_COLUMN_BRIX + ", " +
                HONEY_COLUMN_NAME + ", " +
                HONEY_COLUMN_FLAVOUR +
                " FROM " + HONEY_ADD_TABLE_NAME + " INNER JOIN " +
                HONEY_TABLE_NAME +
                " ON " + HONEY_TABLE_NAME + "." + HONEY_COLUMN_ID + " = " +
                HONEY_ADD_TABLE_NAME + "." + HONEY_ADD_COLUMN_HONEYID +
                " WHERE " + HONEY_ADD_TABLE_NAME + "." + HONEY_ADD_COLUMN_MEADID +
                " = " + String.valueOf(meadID);

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.getCount() > 0) {
            // Loop all rows
            if (cursor.moveToFirst()) {
                do {
                    Honey newHoney = getHoneyRecord(cursor);
                    newHoney.setVolume(cursor.getDouble(cursor.getColumnIndex(HONEY_ADD_COLUMN_VOLUME)));
                    newHoney.setHoneyID(newHoney.getID());
                    newHoney.setID(cursor.getLong(cursor.getColumnIndex((HONEY_ADD_TABLE_NAME + "." + HONEY_ADD_COLUMN_ID))));
                    honeyList.add(newHoney);
                } while (cursor.moveToNext());
            }
        }


        //Return List
        return honeyList;
    }

    public List<SpecGravity> getTestResultsFromMead(long meadID) {
        List<SpecGravity> testList = new ArrayList<>();

        //Select Test Quest
        String selectQuery = "SELECT * FROM " + TESTS_TABLE_NAME +" WHERE " + TESTS_COLUMN_MEADID + " = " + String.valueOf(meadID);

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.getCount() > 0) {
            // Loop all rows
            if (cursor.moveToFirst()) {
                do {
                    SpecGravity newTest = getTestRecord(cursor);
                    testList.add(newTest);
                } while (cursor.moveToNext());
            }
        }

        //Return List
        return testList;

    }

    public SpecGravity getLatestTestResultFromMead(long meadID) {
        SpecGravity mySpec = new SpecGravity();

        //Select Test Quest
        String selectQuery = "SELECT * FROM " + TESTS_TABLE_NAME +" WHERE ((" + TESTS_COLUMN_MEADID + " = " + String.valueOf(meadID) + ") AND (" + TESTS_COLUMN_DATE + " = (SELECT MAX (" + TESTS_COLUMN_DATE + ") FROM " + TESTS_TABLE_NAME + ")))";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                mySpec = getTestRecord(cursor);
            }
        } else {
            return null;
        }


        //Return List
        return mySpec;

    }

    public SpecGravity getTestRecord(Cursor current) {
        if (current.moveToFirst()) {
            long id = current.getLong(current.getColumnIndex(TESTS_COLUMN_ID));
            double sg = current.getDouble(current.getColumnIndex(TESTS_COLUMN_SG));
            long date = current.getLong(current.getColumnIndex(TESTS_COLUMN_DATE));

            GregorianCalendar tempCalendar = new GregorianCalendar();
            tempCalendar.setTimeInMillis(date);

            SpecGravity newTest = new SpecGravity(id, tempCalendar, sg);

            //return Test
            return newTest;
        } else return null;
    }

    public Mead getMeadRecord(Cursor current) {
        if (current.moveToFirst()) {
            long id = current.getLong(current.getColumnIndex(MEAD_COLUMN_ID));
            String name = current.getString(current.getColumnIndex(MEAD_COLUMN_NAME));
            double og = current.getDouble(current.getColumnIndex(MEAD_COLUMN_OG));
            double capacity = current.getDouble(current.getColumnIndex(MEAD_COLUMN_CAPACITY));
            double volume = current.getDouble(current.getColumnIndex(MEAD_COLUMN_VOLUME));
            double alcohol = current.getDouble(current.getColumnIndex(MEAD_COLUMN_ALCOHOL));

            Mead newMead = new Mead(id, name, og, capacity, volume, alcohol);
            newMead.setStartDate(newCalendarFromMillis(current.getLong(current.getColumnIndex(MEAD_COLUMN_START))));
            newMead.setLastTest(getLatestTestResultFromMead(id));

            /************************
             * Was used to populate additional information to mead record, uses alot of heap on access entire


             if (numberOfHoneyAdditionRows(id) > 0) {
             List<Honey> myHoney = getHoneyAdditionsFromMead(id);
             if (myHoney != null) {
             newMead.setHoney(myHoney);
             }
             }

             if (numberOfTestRows(id) > 0) {
             List<SpecGravity> myTests = getTestResultsFromMead(id);
             if (myTests != null) {
             newMead.setTestResults(myTests);
             newMead.setMostCurrentTest();
             }
             }

             */

            // Return Mead Object
            return newMead;
        } else return null;
    }

    public Mead getMeadRecordFromID(long id) {
        Cursor current = getMeadData(id);

        return getMeadRecord(current);
    }

    public List<MeadData> getAllMead() {
        List<MeadData> meadDataList = new ArrayList<MeadData>();
        List<Mead> meadList = new ArrayList<>();
        MeadData myData;

        // Select All Query
        String selectQuery = "SELECT * FROM " + MEAD_TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // Loop all rows
        if (cursor.moveToFirst()) {
            for (int i=0;i<numberOfMeadRows();i++) {

                //Collect Info
                long id = cursor.getLong(cursor.getColumnIndex(MEAD_COLUMN_ID));
                String name = cursor.getString(cursor.getColumnIndex(MEAD_COLUMN_NAME));
                double og = cursor.getDouble(cursor.getColumnIndex(MEAD_COLUMN_OG));
                double capacity = cursor.getDouble(cursor.getColumnIndex(MEAD_COLUMN_CAPACITY));
                double volume = cursor.getDouble(cursor.getColumnIndex(MEAD_COLUMN_VOLUME));
                double alcohol = cursor.getDouble(cursor.getColumnIndex(MEAD_COLUMN_ALCOHOL));

                Mead newMead = new Mead(id, name, og, capacity, volume, alcohol);
                newMead.setStartDate(newCalendarFromMillis(cursor.getLong(cursor.getColumnIndex(MEAD_COLUMN_START))));

                meadList.add(newMead);
                cursor.moveToNext();
            }

            //Using cursor twice was causing load problems, so separate out the parts and create list
            for (int i=0;i<meadList.size();i++) {
                if (getLatestTestResultFromMead(meadList.get(i).getId()) != null) {
                    meadList.get(i).setLastTest(getLatestTestResultFromMead(meadList.get(i).getId()));
                }

                myData = new MeadData(meadList.get(i));
                meadDataList.add(myData);

            }

        }

        // Return List
        return meadDataList;
    }

    // Get Number of Rows
    public int numberOfTestRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, TESTS_TABLE_NAME);
        return numRows;
    }

    public int numberOfTestRows(long meadID){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, TESTS_TABLE_NAME, TESTS_COLUMN_MEADID + " = " + String.valueOf(meadID));
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

    public int numberOfHoneyAdditionRows(long meadID){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, HONEY_ADD_TABLE_NAME, HONEY_ADD_COLUMN_MEADID + " = " + String.valueOf(meadID));
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

    public Integer deleteMead (double id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(HONEY_ADD_TABLE_NAME,
                HONEY_ADD_COLUMN_MEADID + " = ? ",
                new String[] { Double.toString(id) });
        db.delete(INVENTORY_TABLE_NAME,
                INVENTORY_COLUMN_MEADID + " = ? ",
                new String[] { Double.toString(id) });
        return db.delete(MEAD_TABLE_NAME,
                MEAD_COLUMN_ID + " = ? ",
                new String[] { Double.toString(id) });
    }

    public Integer deleteInventory (Integer id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(INVENTORY_TABLE_NAME,
                INVENTORY_COLUMN_ID + " = ? ",
                new String[] { Integer.toString(id) });
    }

    private GregorianCalendar newCalendarFromMillis(long millis) {
        GregorianCalendar tempCal = new GregorianCalendar();
        tempCal.setTimeInMillis(millis);
        return tempCal;
    }
}
