package com.inspiredpanama.inspiredmead;

import java.util.GregorianCalendar;

/**
 * Created by ctilluma on 10/8/16.
 *
 * Class to hold filter information
 */

// Private class for each test
public class Filter {
    //Variables
    private long id; //id record
    private long meadID; //Mead Sampled
    private GregorianCalendar filterDate; // Date of this test
    private int filterSize; // Description of this test

    //Constructors
    public Filter() {                 //Empty Constructor
        this(-1, -1, new GregorianCalendar(), 0);         // Set value to 0.00
    }

    public Filter(long meadID, int filterSize) {  //Constructor w/ test result, supply current time
        this(-1, meadID, new GregorianCalendar(), filterSize); //Set id as negative because it doesn't have a database number
    }

    public Filter(long id, long meadID, GregorianCalendar filterDate, int filterSize) {   //Constructor w/ all info known
        this.id = id;
        this.meadID = meadID;
        this.filterDate = filterDate;
        this.filterSize = filterSize;
    }

    //Getter and Setter methods
    public long getID() {
        return id;
    }

    public void setID(long id) {
        this.id = id;
    }

    public long getMeadID() {
        return meadID;
    }

    public void setMeadID(long meadID) {
        this.meadID = meadID;
    }

    public GregorianCalendar getFilterDate() {
        return filterDate;
    }

    public void setFilterDate(GregorianCalendar filterDate) {
        this.filterDate = filterDate;
    }

    public int getFilterSize() {
        return filterSize;
    }

    public void setFilterSize(int filterSize) {
        this.filterSize = filterSize;
    }


}

