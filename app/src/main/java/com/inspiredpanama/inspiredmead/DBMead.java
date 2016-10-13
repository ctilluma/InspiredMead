package com.inspiredpanama.inspiredmead;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;


/**
 * Created by ctilluma on 10/8/16.
 */

public class DBMead extends SQLiteOpenHelper {
    //Statics
    public static final String DATABASE_NAME = "MeadDB.db";
    //Database Tests Table
    public static final String TESTS_TABLE_NAME = "tests";
    public static final String TESTS_COLUMN_ID = "_id";
    public static final String TESTS_COLUMN_SG = "sg";
    public static final String TESTS_COLUMN_DATE = "date";
    public static final String TESTS_COLUMN_MEADID = "meadID";
    //Database Filter Table
    public static final String FILTER_TABLE_NAME = "filter";
    public static final String FILTER_COLUMN_ID = "_id";
    public static final String FILTER_COLUMN_MEADID = "meadID";
    public static final String FILTER_COLUMN_DATE = "date";
    public static final String FILTER_COLUMN_FILTER = "filterSize";
    //Database Sample Table
    public static final String SAMPLE_TABLE_NAME = "sample";
    public static final String SAMPLE_COLUMN_ID = "_id";
    public static final String SAMPLE_COLUMN_TASTE = "taste";
    public static final String SAMPLE_COLUMN_DATE = "date";
    public static final String SAMPLE_COLUMN_MEADID = "meadID";
    //Database com.inspiredpanama.inspiredmead.Additives Table
    public static final String ADDITIVE_TABLE_NAME = "additive";
    public static final String ADDITIVE_COLUMN_ID = "_id";
    public static final String ADDITIVE_COLUMN_BRIX = "brix";
    public static final String ADDITIVE_COLUMN_NAME = "name";
    public static final String ADDITIVE_COLUMN_FLAVOUR = "flavor";
    //Database com.inspiredpanama.inspiredmead.AddAdditions Additions
    public static final String ADDITIVE_ADD_TABLE_NAME = "addAdditions";
    public static final String ADDITIVE_ADD_COLUMN_ID = "_id";
    public static final String ADDITIVE_ADD_COLUMN_ADDITIVEID = "addID";
    public static final String ADDITIVE_ADD_COLUMN_MEADID = "meadID";
    public static final String ADDITIVE_ADD_COLUMN_AMOUNT = "amount";
    public static final String ADDITIVE_ADD_COLUMN_ISMETRIC = "isMetric";
    public static final String ADDITIVE_ADD_COLUMN_ISWEIGHT = "isWeight";
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
    public static final String HONEY_ADD_COLUMN_ISMETRIC = "isMetric";
    //Database com.inspiredpanama.inspiredmead.Mead Table
    public static final String MEAD_TABLE_NAME = "mead";
    public static final String MEAD_COLUMN_ID = "_id";
    public static final String MEAD_COLUMN_NAME = "name";
    public static final String MEAD_COLUMN_OG = "og";
    public static final String MEAD_COLUMN_CAPACITY = "capacity";
    public static final String MEAD_COLUMN_VOLUME = "volume";
    public static final String MEAD_COLUMN_ALCOHOL = "abv";
    public static final String MEAD_COLUMN_START = "brewDate";
    public static final String MEAD_COLUMN_WASTE = "waste";
    public static final String MEAD_COLUMN_ORIGVOL = "origVol";
    //Database Inventory
    public static final String INVENTORY_TABLE_NAME = "inventory";
    public static final String INVENTORY_COLUMN_ID = "_id";
    public static final String INVENTORY_COLUMN_MEADID = "meadID";
    public static final String INVENTORY_COLUMN_VOLUME = "volume";
    public static final String INVENTORY_COLUMN_QUANTITY = "quantity";
    private static final int DATABASE_VERSION = 1;
    //Database Upgrade STATICS
    private static final String DATABASE_ALTER_HONEY_ADD_1 = "ALTER TABLE "
            + HONEY_ADD_TABLE_NAME + " ADD COLUMN " + "" + " " + HONEY_ADD_COLUMN_ISMETRIC + ";";
    private static final String DATABASE_ADD_ADDITIVE_1 = "CREATE TABLE " + ADDITIVE_TABLE_NAME + " (" +
            ADDITIVE_COLUMN_ID + " INTEGER PRIMARY KEY, " +
            ADDITIVE_COLUMN_BRIX + " REAL, " +
            ADDITIVE_COLUMN_NAME + " TEXT, " +
            ADDITIVE_COLUMN_FLAVOUR + " TEXT)";
    private static final String DATABASE_ADD_ADDITIVE_ADD_1 = "CREATE TABLE " + ADDITIVE_ADD_TABLE_NAME + " (" +
            ADDITIVE_ADD_COLUMN_ID + " INTEGER PRIMARY KEY, " +
            ADDITIVE_ADD_COLUMN_ADDITIVEID + " INTEGER, " +
            ADDITIVE_ADD_COLUMN_MEADID + " INTEGER, " +
            ADDITIVE_ADD_COLUMN_AMOUNT + " REAL, " +
            ADDITIVE_ADD_COLUMN_ISMETRIC + " INTEGER, " +
            ADDITIVE_ADD_COLUMN_ISWEIGHT + " INTEGER)";
    private static final String DATABASE_ADD_SAMPLE_1 = "CREATE TABLE " + SAMPLE_TABLE_NAME + " (" +
            SAMPLE_COLUMN_ID + " INTEGER PRIMARY KEY, " +
            SAMPLE_COLUMN_TASTE + " BLOB, " +
            SAMPLE_COLUMN_DATE + " INT, " +
            SAMPLE_COLUMN_MEADID + " INT)";

    //Variables 


    //Constructor
    public DBMead(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Create Tables
        //TESTS TABLE
        db.execSQL("CREATE TABLE " + TESTS_TABLE_NAME + " (" +
                TESTS_COLUMN_ID + " INTEGER PRIMARY KEY, " +
                TESTS_COLUMN_SG + " REAL, " +
                TESTS_COLUMN_DATE + " INT, " +
                TESTS_COLUMN_MEADID + " INT)");

        //FILTER TABLE
        db.execSQL("CREATE TABLE " + FILTER_TABLE_NAME + " (" +
                FILTER_COLUMN_ID + " INTEGER PRIMARY KEY, " +
                FILTER_COLUMN_MEADID + " INTEGER, " +
                FILTER_COLUMN_DATE + " INT, " +
                FILTER_COLUMN_FILTER + " INT)");

        //SAMPLE TABLE
        db.execSQL("CREATE TABLE " + SAMPLE_TABLE_NAME + " (" +
                SAMPLE_COLUMN_ID + " INTEGER PRIMARY KEY, " +
                SAMPLE_COLUMN_TASTE + " BLOB, " +
                SAMPLE_COLUMN_DATE + " INT, " +
                SAMPLE_COLUMN_MEADID + " INT)");

        //HONEY TABLE
        db.execSQL("CREATE TABLE " + HONEY_TABLE_NAME + " (" +
                HONEY_COLUMN_ID + " INTEGER PRIMARY KEY, " +
                HONEY_COLUMN_BRIX + " REAL, " +
                HONEY_COLUMN_NAME + " TEXT, " +
                HONEY_COLUMN_FLAVOUR + " TEXT)");

        //HONEY ADD TABLE
        db.execSQL("CREATE TABLE " + HONEY_ADD_TABLE_NAME + " (" +
                HONEY_ADD_COLUMN_ID + " INTEGER PRIMARY KEY, " +
                HONEY_ADD_COLUMN_HONEYID + " INTEGER, " +
                HONEY_ADD_COLUMN_MEADID + " INTEGER, " +
                HONEY_ADD_COLUMN_ISMETRIC + " INTEGER, " +
                HONEY_ADD_COLUMN_VOLUME + " REAL)");

        //ADDITIVE TABLE
        db.execSQL("CREATE TABLE " + ADDITIVE_TABLE_NAME + " (" +
                ADDITIVE_COLUMN_ID + " INTEGER PRIMARY KEY, " +
                ADDITIVE_COLUMN_BRIX + " REAL, " +
                ADDITIVE_COLUMN_NAME + " TEXT, " +
                ADDITIVE_COLUMN_FLAVOUR + " TEXT)");

        //ADDITIVE ADD TABLE
        db.execSQL("CREATE TABLE " + ADDITIVE_ADD_TABLE_NAME + " (" +
                ADDITIVE_ADD_COLUMN_ID + " INTEGER PRIMARY KEY, " +
                ADDITIVE_ADD_COLUMN_ADDITIVEID + " INTEGER, " +
                ADDITIVE_ADD_COLUMN_MEADID + " INTEGER, " +
                ADDITIVE_ADD_COLUMN_AMOUNT + " REAL, " +
                ADDITIVE_ADD_COLUMN_ISMETRIC + " INTEGER, " +
                ADDITIVE_ADD_COLUMN_ISWEIGHT + " INTEGER)");

        //MEAD TABLE
        db.execSQL("CREATE TABLE " + MEAD_TABLE_NAME + " (" +
                MEAD_COLUMN_ID + " INTEGER PRIMARY KEY, " +
                MEAD_COLUMN_NAME + " INTEGER, " +
                MEAD_COLUMN_OG + " REAL, " +
                MEAD_COLUMN_CAPACITY + " REAL, " +
                MEAD_COLUMN_VOLUME + " REAL, " +
                MEAD_COLUMN_ALCOHOL + " REAL, " +
                MEAD_COLUMN_START + " REAL, " +
                MEAD_COLUMN_ORIGVOL + " REAL, " +
                MEAD_COLUMN_WASTE + " REAL)");

        //INVENTORY TABLE
        db.execSQL("CREATE TABLE " + INVENTORY_TABLE_NAME + " (" +
                INVENTORY_COLUMN_ID + " INTEGER PRIMARY KEY, " +
                INVENTORY_COLUMN_MEADID + " INTEGER, " +
                INVENTORY_COLUMN_VOLUME + " REAL, " +
                INVENTORY_COLUMN_QUANTITY + " INTEGER)");
    }

    //Handle Upgrades for each revision add additional if oldVersion
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Drop Tables
        if (oldVersion < 2) { //Run these table updates when upgrading to 2
            db.execSQL(DATABASE_ALTER_HONEY_ADD_1);
            db.execSQL(DATABASE_ADD_ADDITIVE_1);
            db.execSQL(DATABASE_ADD_ADDITIVE_ADD_1);
            db.execSQL(DATABASE_ADD_SAMPLE_1);
        }
    }


    //Table Insertions (CREATE)
    public long insertTest(double sg, long date, long meadID) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TESTS_COLUMN_SG, sg);
        contentValues.put(TESTS_COLUMN_DATE, date);
        contentValues.put(TESTS_COLUMN_MEADID, meadID);
        return db.insert(TESTS_TABLE_NAME, null, contentValues);
    }

    public long insertFilter(long meadID, long date, int filterSize) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(FILTER_COLUMN_MEADID, meadID);
        contentValues.put(FILTER_COLUMN_DATE, date);
        contentValues.put(FILTER_COLUMN_FILTER, filterSize);
        return db.insert(FILTER_TABLE_NAME, null, contentValues);
    }

    public long insertFilter(Filter mFilter) {
        return insertFilter(mFilter.getMeadID(), mFilter.getFilterDate().getTimeInMillis(), mFilter.getFilterSize());
    }

    public long insertSample(String taste, long date, long meadID) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(SAMPLE_COLUMN_TASTE, taste);
        contentValues.put(SAMPLE_COLUMN_DATE, date);
        contentValues.put(SAMPLE_COLUMN_MEADID, meadID);
        return db.insert(SAMPLE_TABLE_NAME, null, contentValues);
    }

    public long insertHoney(double brix, String name, String flavour) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(HONEY_COLUMN_BRIX, brix);
        contentValues.put(HONEY_COLUMN_NAME, name);
        contentValues.put(HONEY_COLUMN_FLAVOUR, flavour);
        return db.insert(HONEY_TABLE_NAME, null, contentValues);
    }

    public long insertHoneyAddition(long honeyID, long meadID, double volume, boolean isMetric) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(HONEY_ADD_COLUMN_HONEYID, honeyID);
        contentValues.put(HONEY_ADD_COLUMN_MEADID, meadID);
        contentValues.put(HONEY_ADD_COLUMN_VOLUME, volume);
        contentValues.put(HONEY_ADD_COLUMN_ISMETRIC, isMetric);
        return db.insert(HONEY_ADD_TABLE_NAME, null, contentValues);
    }

    public long insertAdditive(double brix, String name, String flavour) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ADDITIVE_COLUMN_BRIX, brix);
        contentValues.put(ADDITIVE_COLUMN_NAME, name);
        contentValues.put(ADDITIVE_COLUMN_FLAVOUR, flavour);
        return db.insert(ADDITIVE_TABLE_NAME, null, contentValues);
    }

    public long insertAdditiveAdd(long honeyID, long meadID, double amount, boolean isMetric, boolean isWeight) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ADDITIVE_ADD_COLUMN_ADDITIVEID, honeyID);
        contentValues.put(ADDITIVE_ADD_COLUMN_MEADID, meadID);
        contentValues.put(ADDITIVE_ADD_COLUMN_AMOUNT, amount);
        contentValues.put(ADDITIVE_ADD_COLUMN_ISMETRIC, isMetric);
        contentValues.put(ADDITIVE_ADD_COLUMN_ISWEIGHT, isWeight);
        return db.insert(ADDITIVE_ADD_TABLE_NAME, null, contentValues);
    }

    public long insertMead(String name, double og, double capacity, double volume, double alcohol, long brewDate, double origVol, double waste) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(MEAD_COLUMN_NAME, name);
        contentValues.put(MEAD_COLUMN_OG, og);
        contentValues.put(MEAD_COLUMN_CAPACITY, capacity);
        contentValues.put(MEAD_COLUMN_VOLUME, volume);
        contentValues.put(MEAD_COLUMN_ALCOHOL, alcohol);
        contentValues.put(MEAD_COLUMN_START, brewDate);
        contentValues.put(MEAD_COLUMN_ORIGVOL, origVol);
        contentValues.put(MEAD_COLUMN_WASTE, waste);
        return db.insertOrThrow(MEAD_TABLE_NAME, null, contentValues);
    }

    public long insertMead(Mead myMead) {
        //Variables
        SpecGravity myTest;
        long tempID, meadID;
        Honey myHoney;
        Additive myAdditive;

        //Insert Record to get MeadID for other tables and setID
        meadID = insertMead(myMead.getName(), myMead.getOG(), myMead.getCapacity(), myMead.getVolume(), myMead.getAlcohol(), myMead.getStartDate().getTimeInMillis(), myMead.getOrigVolume(), myMead.getWaste());

        // Interate Test Results and add to Database
        if (myMead.getTestResults() != null) {
            for (int i = 0; i < myMead.getTestResults().size(); i++) {
                //Set local for easier reading
                myTest = myMead.getTestResults().get(i);
                tempID = insertTest(myTest.getTestGravity(), myTest.getTestDate().getTimeInMillis(), meadID);
            }
        }

        //Iterate Honey Additions and add to Database
        if (myMead.getHoney() != null) {
            for (int i = 0; i < myMead.getHoney().size(); i++) {
                // Set local for easier reading
                myHoney = myMead.getHoney().get(i);
                tempID = insertHoneyAddition(myHoney.getHoneyID(), meadID, myHoney.getVolume(), myHoney.getMetric());
            }
        }

        //Iterate Additive Additions and add to Database
        if (myMead.getAdditive() != null) {
            for (int i = 0; i < myMead.getAdditive().size(); i++) {
                // Set local for easier reading
                myAdditive = myMead.getAdditive().get(i);
                tempID = insertAdditiveAdd(myAdditive.getAdditiveID(), meadID, myAdditive.getAmount(), myAdditive.getMetric(), myAdditive.getIsWeight());
            }
        }

        //Return meadID
        return meadID;
    }

    public long insertInventory(long meadID, double volume, double quantity) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(INVENTORY_COLUMN_MEADID, meadID);
        contentValues.put(INVENTORY_COLUMN_VOLUME, volume);
        contentValues.put(INVENTORY_COLUMN_QUANTITY, quantity);
        return db.insert(INVENTORY_TABLE_NAME, null, contentValues);
    }

    //Cursor Queries (READ)
    public Cursor getTestData(long id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + TESTS_TABLE_NAME + " WHERE " + TESTS_COLUMN_ID + " = " + id + "", null);
        return res;
    }

    public Cursor getFilterData(long id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + FILTER_TABLE_NAME + " WHERE " + FILTER_COLUMN_ID + " = " + id + "", null);
        return res;
    }

    public Cursor getSampleData(long id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + SAMPLE_TABLE_NAME + " WHERE " + SAMPLE_COLUMN_ID + " = " + id + "", null);
        return res;
    }

    public Cursor getHoneyData(long id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + HONEY_TABLE_NAME + " WHERE " + HONEY_COLUMN_ID + " = " + id + "", null);
        return res;
    }

    public Cursor getHoneyAddData(long id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + HONEY_ADD_TABLE_NAME + " WHERE " + HONEY_ADD_COLUMN_ID + " = " + id + "", null);
        return res;
    }

    public Cursor getAdditiveData(long id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + ADDITIVE_TABLE_NAME + " WHERE " + ADDITIVE_COLUMN_ID + " = " + id + "", null);
        return res;
    }

    public Cursor getAdditiveAddData(long id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + ADDITIVE_ADD_TABLE_NAME + " WHERE " + ADDITIVE_ADD_COLUMN_ID + " = " + id + "", null);
        return res;
    }

    public Cursor getMeadData(long id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + MEAD_TABLE_NAME + " WHERE " + MEAD_COLUMN_ID + " = " + id + "", null);
        return res;
    }

    public Cursor getInventoryData(long id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + INVENTORY_TABLE_NAME + " WHERE " + INVENTORY_COLUMN_ID + " = " + String.valueOf(id) + "", null);
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

    public Additive getAdditiveRecord(Cursor current) {
        if (current.moveToFirst()) {
            if ((current.getString(current.getColumnIndex(ADDITIVE_COLUMN_ID)) != null) && (!current.getString(current.getColumnIndex(ADDITIVE_COLUMN_ID)).isEmpty())) {
                long id = current.getLong(current.getColumnIndex(ADDITIVE_COLUMN_ID));
            } else {
                long id = current.getLong(current.getColumnIndex(ADDITIVE_TABLE_NAME + "." + ADDITIVE_COLUMN_ID));
            }
            long id = current.getLong(current.getColumnIndex(ADDITIVE_COLUMN_ID));
            double brix = current.getDouble(current.getColumnIndex(ADDITIVE_COLUMN_BRIX));
            String name = current.getString(current.getColumnIndex(ADDITIVE_COLUMN_NAME));
            String flavour = current.getString(current.getColumnIndex(ADDITIVE_COLUMN_FLAVOUR));

            Additive newAdditive = new Additive(id, brix, name, flavour);
            newAdditive.setAdditiveID(id);

            // Return Honey Object
            return newAdditive;
        } else return null;
    }

    public List<Honey> getHoneyAdditionsFromMead(long meadID) {
        List<Honey> honeyList = new ArrayList<Honey>();

        //Select HoneyAdditions
        String selectQuery = "SELECT " +
                HONEY_TABLE_NAME + "." + HONEY_COLUMN_ID + ", " +
                HONEY_ADD_TABLE_NAME + "." + HONEY_ADD_COLUMN_ID + ", " +
                HONEY_ADD_COLUMN_VOLUME + ", " +
                HONEY_ADD_COLUMN_ISMETRIC + ", " +
                HONEY_COLUMN_BRIX + ", " +
                HONEY_COLUMN_NAME + ", " +
                HONEY_ADD_COLUMN_ISMETRIC + ", " +
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
                    if (cursor.getInt(cursor.getColumnIndex(HONEY_ADD_COLUMN_ISMETRIC)) == 0) {
                        newHoney.setMetric(false);
                    } else {
                        newHoney.setMetric(true);
                    }

                    honeyList.add(newHoney);
                } while (cursor.moveToNext());
            }
        }


        //Return List
        return honeyList;
    }

    public List<Additive> getAdditiveAdditionsFromMead(long meadID) {
        List<Additive> addList = new ArrayList<Additive>();

        //Select AdditiveAdditions
        String selectQuery = "SELECT " +
                ADDITIVE_TABLE_NAME + "." + ADDITIVE_COLUMN_ID + ", " +
                ADDITIVE_ADD_TABLE_NAME + "." + ADDITIVE_ADD_COLUMN_ID + ", " +
                ADDITIVE_ADD_COLUMN_AMOUNT + ", " +
                ADDITIVE_ADD_COLUMN_ISMETRIC + ", " +
                ADDITIVE_ADD_COLUMN_ISWEIGHT + ", " +
                ADDITIVE_COLUMN_BRIX + ", " +
                ADDITIVE_COLUMN_NAME + ", " +
                ADDITIVE_COLUMN_FLAVOUR +
                " FROM " + ADDITIVE_ADD_TABLE_NAME + " INNER JOIN " +
                ADDITIVE_TABLE_NAME +
                " ON " + ADDITIVE_TABLE_NAME + "." + ADDITIVE_COLUMN_ID + " = " +
                ADDITIVE_ADD_TABLE_NAME + "." + ADDITIVE_ADD_COLUMN_ADDITIVEID +
                " WHERE " + ADDITIVE_ADD_TABLE_NAME + "." + ADDITIVE_ADD_COLUMN_MEADID +
                " = " + String.valueOf(meadID);

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.getCount() > 0) {
            // Loop all rows
            if (cursor.moveToFirst()) {
                do {
                    Additive newAdd = getAdditiveRecord(cursor);
                    newAdd.setAmount(cursor.getDouble(cursor.getColumnIndex(ADDITIVE_ADD_COLUMN_AMOUNT)));
                    newAdd.setAdditiveID(newAdd.getID());
                    newAdd.setID(cursor.getLong(cursor.getColumnIndex((ADDITIVE_ADD_TABLE_NAME + "." + ADDITIVE_ADD_COLUMN_ID))));
                    if (cursor.getInt(cursor.getColumnIndex(ADDITIVE_ADD_COLUMN_ISMETRIC)) == 0) {
                        newAdd.setMetric(false);
                    } else {
                        newAdd.setMetric(true);
                    }
                    if (cursor.getInt(cursor.getColumnIndex(ADDITIVE_ADD_COLUMN_ISWEIGHT)) == 0) {
                        newAdd.setMetric(false);
                    } else {
                        newAdd.setMetric(true);
                    }
                    addList.add(newAdd);
                } while (cursor.moveToNext());
            }
        }


        //Return List
        return addList;
    }

    public List<Filter> getFiltersFromMead(long meadID) {
        List<Filter> lFilter = new ArrayList<>();

        //Select Test Quest
        String selectQuery = "SELECT * FROM " + FILTER_TABLE_NAME + " WHERE " + FILTER_COLUMN_MEADID + " = " + String.valueOf(meadID);

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.getCount() > 0) {
            // Loop all rows
            if (cursor.moveToFirst()) {
                do {
                    Filter mFilter = getFilterRecord(cursor);
                    lFilter.add(mFilter);
                } while (cursor.moveToNext());
            }
        }

        //Return List
        return lFilter;

    }

    public List<TasteSample> getSampleResultsFromMead(long meadID) {
        List<TasteSample> testSample = new ArrayList<>();

        //Select Test Quest
        String selectQuery = "SELECT * FROM " + SAMPLE_TABLE_NAME + " WHERE " + SAMPLE_COLUMN_MEADID + " = " + String.valueOf(meadID);

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.getCount() > 0) {
            // Loop all rows
            if (cursor.moveToFirst()) {
                do {
                    TasteSample newSample = getSampleRecord(cursor);
                    testSample.add(newSample);
                } while (cursor.moveToNext());
            }
        }

        //Return List
        return testSample;

    }

    public List<SpecGravity> getTestResultsFromMead(long meadID) {
        List<SpecGravity> testList = new ArrayList<>();

        //Select Test Quest
        String selectQuery = "SELECT * FROM " + TESTS_TABLE_NAME + " WHERE " + TESTS_COLUMN_MEADID + " = " + String.valueOf(meadID);

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
        String selectQuery = "SELECT * FROM " + TESTS_TABLE_NAME + " WHERE ((" + TESTS_COLUMN_MEADID + " = " + String.valueOf(meadID) + ") AND (" + TESTS_COLUMN_DATE + " = (SELECT MAX (" + TESTS_COLUMN_DATE + ") FROM " + TESTS_TABLE_NAME + ")))";

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

    public Filter getFilterRecord(Cursor current) {
        if (current.moveToFirst()) {
            long id = current.getLong(current.getColumnIndex(FILTER_COLUMN_ID));
            long meadID = current.getLong(current.getColumnIndex(FILTER_COLUMN_MEADID));
            long date = current.getLong(current.getColumnIndex(FILTER_COLUMN_DATE));
            int filterSize = current.getInt(current.getColumnIndex(FILTER_COLUMN_FILTER));

            GregorianCalendar tempCalendar = new GregorianCalendar();
            tempCalendar.setTimeInMillis(date);

            Filter mFilter = new Filter(id, meadID, tempCalendar, filterSize);

            //return Test
            return mFilter;
        } else return null;
    }

    public SpecGravity getTestRecord(Cursor current) {
        if (current.moveToFirst()) {
            long id = current.getLong(current.getColumnIndex(TESTS_COLUMN_ID));
            double sg = current.getDouble(current.getColumnIndex(TESTS_COLUMN_SG));
            long date = current.getLong(current.getColumnIndex(TESTS_COLUMN_DATE));
            long meadID = current.getLong(current.getColumnIndex(TESTS_COLUMN_MEADID));

            GregorianCalendar tempCalendar = new GregorianCalendar();
            tempCalendar.setTimeInMillis(date);

            SpecGravity newTest = new SpecGravity(id, meadID, tempCalendar, sg);

            //return Test
            return newTest;
        } else return null;
    }

    public TasteSample getSampleRecord(Cursor current) {
        if (current.moveToFirst()) {
            long id = current.getLong(current.getColumnIndex(SAMPLE_COLUMN_ID));
            String taste = current.getString(current.getColumnIndex(SAMPLE_COLUMN_TASTE));
            long date = current.getLong(current.getColumnIndex(SAMPLE_COLUMN_DATE));
            long meadID = current.getLong(current.getColumnIndex(SAMPLE_COLUMN_MEADID));

            GregorianCalendar tempCalendar = new GregorianCalendar();
            tempCalendar.setTimeInMillis(date);

            TasteSample newTaste = new TasteSample(id, meadID, tempCalendar, taste);

            //return Test
            return newTaste;
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
            double origVol = current.getDouble(current.getColumnIndex(MEAD_COLUMN_ORIGVOL));
            double waste = current.getDouble(current.getColumnIndex(MEAD_COLUMN_WASTE));

            Mead newMead = new Mead(id, name, og, capacity, volume, alcohol);
            newMead.setStartDate(newCalendarFromMillis(current.getLong(current.getColumnIndex(MEAD_COLUMN_START))));
            newMead.setLastTest(getLatestTestResultFromMead(id));
            newMead.setOrigVolume(origVol);
            newMead.setWaste(waste);

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
            for (int i = 0; i < numberOfMeadRows(); i++) {

                //Collect Info
                long id = cursor.getLong(cursor.getColumnIndex(MEAD_COLUMN_ID));
                String name = cursor.getString(cursor.getColumnIndex(MEAD_COLUMN_NAME));
                double og = cursor.getDouble(cursor.getColumnIndex(MEAD_COLUMN_OG));
                double capacity = cursor.getDouble(cursor.getColumnIndex(MEAD_COLUMN_CAPACITY));
                double volume = cursor.getDouble(cursor.getColumnIndex(MEAD_COLUMN_VOLUME));
                double alcohol = cursor.getDouble(cursor.getColumnIndex(MEAD_COLUMN_ALCOHOL));
                double origVol = cursor.getDouble(cursor.getColumnIndex(MEAD_COLUMN_ORIGVOL));
                double waste = cursor.getDouble(cursor.getColumnIndex(MEAD_COLUMN_WASTE));


                Mead newMead = new Mead(id, name, og, capacity, volume, alcohol);
                newMead.setStartDate(newCalendarFromMillis(cursor.getLong(cursor.getColumnIndex(MEAD_COLUMN_START))));
                newMead.setOrigVolume(origVol);
                newMead.setWaste(waste);


                meadList.add(newMead);
                cursor.moveToNext();
            }

            //Using cursor twice was causing load problems, so separate out the parts and create list
            for (int i = 0; i < meadList.size(); i++) {
                if (getLatestTestResultFromMead(meadList.get(i).getId()) != null) {
                    SpecGravity mSpec = getLatestTestResultFromMead(meadList.get(i).getId());
                    meadList.get(i).setLastTest(mSpec);
                    meadList.get(i).setAlcohol(mSpec.getAlcohol(meadList.get(i).getOG()));
                } else {
                    meadList.get(i).setAlcohol(0.00);
                }

                myData = new MeadData(meadList.get(i));
                meadDataList.add(myData);

            }

        }

        // Return List
        return meadDataList;
    }

    // Get Number of Rows
    public int numberOfTestRows() {
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, TESTS_TABLE_NAME);
        return numRows;
    }

    public int numberOfTestRows(long meadID) {
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, TESTS_TABLE_NAME, TESTS_COLUMN_MEADID + " = " + String.valueOf(meadID));
        return numRows;
    }

    public int numberOfSampleRows() {
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, SAMPLE_TABLE_NAME);
        return numRows;
    }

    public int numberOfSampleRows(long meadID) {
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, SAMPLE_TABLE_NAME, SAMPLE_COLUMN_MEADID + " = " + String.valueOf(meadID));
        return numRows;
    }

    public int numberOfHoneyRows() {
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, HONEY_TABLE_NAME);
        return numRows;
    }

    public int numberOfHoneyAdditionRows() {
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, HONEY_ADD_TABLE_NAME);
        return numRows;
    }

    public int numberOfHoneyAdditionRows(long meadID) {
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, HONEY_ADD_TABLE_NAME, HONEY_ADD_COLUMN_MEADID + " = " + String.valueOf(meadID));
        return numRows;
    }

    public int numberOfAdditiveRows() {
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, ADDITIVE_TABLE_NAME);
        return numRows;
    }

    public int numberOfAdditiveAdditionRows() {
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, ADDITIVE_ADD_TABLE_NAME);
        return numRows;
    }

    public int numberOfAdditiveAdditionRows(long meadID) {
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, ADDITIVE_ADD_TABLE_NAME, ADDITIVE_ADD_COLUMN_MEADID + " = " + String.valueOf(meadID));
        return numRows;
    }

    public int numberOfMeadRows() {
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, MEAD_TABLE_NAME);
        return numRows;
    }

    public int numberOfInventoryRows() {
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, INVENTORY_TABLE_NAME);
        return numRows;
    }

    // Update Tables (UPDATE)
    public int updateTest(int id, double sg, int date, long meadID) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TESTS_COLUMN_SG, sg);
        contentValues.put(TESTS_COLUMN_DATE, date);
        contentValues.put(TESTS_COLUMN_MEADID, meadID);
        return db.update(TESTS_TABLE_NAME, contentValues, TESTS_COLUMN_ID + " = ? ", new String[]{Integer.toString(id)});
    }

    public int updateSample(int id, String taste, int date, long meadID) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(SAMPLE_COLUMN_TASTE, taste);
        contentValues.put(SAMPLE_COLUMN_DATE, date);
        contentValues.put(SAMPLE_COLUMN_MEADID, meadID);
        return db.update(SAMPLE_TABLE_NAME, contentValues, SAMPLE_COLUMN_ID + " = ? ", new String[]{Integer.toString(id)});
    }

    public int updateHoney(int id, double brix, String name, String flavour) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(HONEY_COLUMN_BRIX, brix);
        contentValues.put(HONEY_COLUMN_NAME, name);
        contentValues.put(HONEY_COLUMN_FLAVOUR, flavour);
        return db.update(HONEY_TABLE_NAME, contentValues, HONEY_COLUMN_ID + " = ? ", new String[]{Integer.toString(id)});
    }

    public int updateHoneyAdd(int id, long honeyID, long meadID, double volume, boolean isMetric) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(HONEY_ADD_COLUMN_HONEYID, honeyID);
        contentValues.put(HONEY_ADD_COLUMN_MEADID, meadID);
        contentValues.put(HONEY_ADD_COLUMN_VOLUME, volume);
        contentValues.put(HONEY_ADD_COLUMN_ISMETRIC, isMetric);
        return db.update(HONEY_ADD_TABLE_NAME, contentValues, HONEY_ADD_COLUMN_ID + " = ? ", new String[]{Integer.toString(id)});
    }

    public int updateAdditive(int id, double brix, String name, String flavour) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ADDITIVE_COLUMN_BRIX, brix);
        contentValues.put(ADDITIVE_COLUMN_NAME, name);
        contentValues.put(ADDITIVE_COLUMN_FLAVOUR, flavour);
        return db.update(ADDITIVE_TABLE_NAME, contentValues, ADDITIVE_COLUMN_ID + " = ? ", new String[]{Integer.toString(id)});
    }

    public int updateAdditiveAdd(int id, long honeyID, long meadID, double volume, boolean isMetric, boolean isWeight) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ADDITIVE_ADD_COLUMN_ADDITIVEID, honeyID);
        contentValues.put(ADDITIVE_ADD_COLUMN_MEADID, meadID);
        contentValues.put(ADDITIVE_ADD_COLUMN_AMOUNT, volume);
        contentValues.put(ADDITIVE_ADD_COLUMN_ISMETRIC, isMetric);
        contentValues.put(ADDITIVE_ADD_COLUMN_ISWEIGHT, isWeight);
        return db.update(ADDITIVE_ADD_TABLE_NAME, contentValues, ADDITIVE_ADD_COLUMN_ID + " = ? ", new String[]{Integer.toString(id)});
    }

    public int updateMead(long id, String name, double og, double capacity, double volume, double alcohol) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(MEAD_COLUMN_NAME, name);
        contentValues.put(MEAD_COLUMN_OG, og);
        contentValues.put(MEAD_COLUMN_CAPACITY, capacity);
        contentValues.put(MEAD_COLUMN_VOLUME, volume);
        contentValues.put(MEAD_COLUMN_ALCOHOL, alcohol);
        return db.update(MEAD_TABLE_NAME, contentValues, MEAD_COLUMN_ID + " = ? ", new String[]{Long.toString(id)});
    }

    public int updateMead(Mead mMead) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(MEAD_COLUMN_NAME, mMead.getName());
        contentValues.put(MEAD_COLUMN_OG, mMead.getOG());
        contentValues.put(MEAD_COLUMN_CAPACITY, mMead.getCapacity());
        contentValues.put(MEAD_COLUMN_VOLUME, mMead.getVolume());
        contentValues.put(MEAD_COLUMN_ALCOHOL, mMead.getAlcohol());
        contentValues.put(MEAD_COLUMN_WASTE, mMead.getWaste());
        contentValues.put(MEAD_COLUMN_ORIGVOL, mMead.getOrigVolume());
        contentValues.put(MEAD_COLUMN_START, mMead.getStartDate().getTimeInMillis());

        return db.update(MEAD_TABLE_NAME, contentValues, MEAD_COLUMN_ID + " = ? ", new String[]{Long.toString(mMead.getId())});
    }

    public int updateInventory(int id, long meadID, double volume, double quantity) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(INVENTORY_COLUMN_MEADID, meadID);
        contentValues.put(INVENTORY_COLUMN_VOLUME, volume);
        contentValues.put(INVENTORY_COLUMN_QUANTITY, quantity);
        return db.update(INVENTORY_TABLE_NAME, contentValues, INVENTORY_COLUMN_ID + " = ? ", new String[]{Integer.toString(id)});
    }


    //Delete Records (DELETE)
    public Integer deleteTest(long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TESTS_TABLE_NAME,
                TESTS_COLUMN_ID + " = ? ",
                new String[]{Long.toString(id)});
    }

    public Integer deleteSample(long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(SAMPLE_TABLE_NAME,
                SAMPLE_COLUMN_ID + " = ? ",
                new String[]{Long.toString(id)});
    }

    public Integer deleteHoney(long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(HONEY_ADD_TABLE_NAME,
                HONEY_ADD_COLUMN_HONEYID + " = ? ",
                new String[]{Long.toString(id)});
        return db.delete(HONEY_TABLE_NAME,
                HONEY_COLUMN_ID + " = ? ",
                new String[]{Long.toString(id)});
    }

    public Integer deleteHoneyAdd(long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(HONEY_ADD_TABLE_NAME,
                HONEY_ADD_COLUMN_ID + " = ? ",
                new String[]{Long.toString(id)});
    }

    public Integer deleteAdditive(long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(ADDITIVE_ADD_TABLE_NAME,
                ADDITIVE_ADD_COLUMN_ADDITIVEID + " = ? ",
                new String[]{Long.toString(id)});
        return db.delete(ADDITIVE_TABLE_NAME,
                ADDITIVE_COLUMN_ID + " = ? ",
                new String[]{Long.toString(id)});
    }

    public Integer deleteAdditiveAdd(long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(ADDITIVE_ADD_TABLE_NAME,
                ADDITIVE_ADD_COLUMN_ID + " = ? ",
                new String[]{Long.toString(id)});
    }

    public Integer deleteMead(long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(HONEY_ADD_TABLE_NAME,
                HONEY_ADD_COLUMN_MEADID + " = ? ",
                new String[]{Long.toString(id)});
        db.delete(INVENTORY_TABLE_NAME,
                INVENTORY_COLUMN_MEADID + " = ? ",
                new String[]{Long.toString(id)});
        return db.delete(MEAD_TABLE_NAME,
                MEAD_COLUMN_ID + " = ? ",
                new String[]{Long.toString(id)});
    }

    public Integer deleteInventory(long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(INVENTORY_TABLE_NAME,
                INVENTORY_COLUMN_ID + " = ? ",
                new String[]{Long.toString(id)});
    }

    private GregorianCalendar newCalendarFromMillis(long millis) {
        GregorianCalendar tempCal = new GregorianCalendar();
        tempCal.setTimeInMillis(millis);
        return tempCal;
    }
}
