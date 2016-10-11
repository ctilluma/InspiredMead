package com.inspiredpanama.inspiredmead;

import java.util.GregorianCalendar;

/**
 * Created by ctilluma on 10/8/16.
 */

// Private class for each test
public class TasteSample {
    //Variables
    private long id; //id record
    private long meadID; //Mead Sampled
    private GregorianCalendar testDate; // Date of this test
    private String sampleTaste; // Description of this test

    //Constructors
    public TasteSample() {                 //Empty Constructor
        this("");         // Set value to 0.00
    }

    public TasteSample(String sampleTaste) {  //Constructor w/ test result, supply current time
        this(-1, -1, new GregorianCalendar(), sampleTaste); //Set id as negative because it doesn't have a database number
    }

    public TasteSample(long id, long meadID, GregorianCalendar testDate, String sampleTaste) {   //Constructor w/ all info known
        this.id = id;
        this.meadID = meadID;
        this.testDate = testDate;
        this.sampleTaste = sampleTaste;
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

    public GregorianCalendar getTestDate() {
        return testDate;
    }

    public void setTestDate(GregorianCalendar testDate) {
        this.testDate = testDate;
    }

    public String getSampleTaste() {
        return sampleTaste;
    }

    public void setSampleTaste(String sampleTaste) {
        this.sampleTaste = sampleTaste;
    }

    public void setTestNow() {   //Set current time of test to system time
        this.testDate = new GregorianCalendar();
    }

}

