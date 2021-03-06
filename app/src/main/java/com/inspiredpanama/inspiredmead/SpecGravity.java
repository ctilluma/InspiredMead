package com.inspiredpanama.inspiredmead;

import java.util.GregorianCalendar;

/**
 * Created by ctilluma on 10/8/16.
 *
 * Class to hold Specific Gravity Information
 */

// Private class for each test
public class SpecGravity {
    //Variables
    private long id, meadID; //id records
    private GregorianCalendar testDate; // Date of this test
    private double testGravity; // Specific Gravity of this test

    //Constructors
    public SpecGravity() {                 //Empty Constructor
        this(0.00);         // Set value to 0.00
    }

    public SpecGravity(double testGravity) {  //Constructor w/ test value, supply current time
        this(-1, -1, new GregorianCalendar(), testGravity); //Set id as negative because it doesn't have a database number
    }

    public SpecGravity(long id, long meadID, GregorianCalendar testDate, double testGravity) {   //Constructor w/ all info known
        this.id = id;
        this.meadID = meadID;
        this.testDate = testDate;
        this.testGravity = testGravity;
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

    public double getTestGravity() {
        return testGravity;
    }

    public void setTestGravity(double testGravity) {
        this.testGravity = testGravity;
    }

    public void setTestNow() {   //Set current time of test to system time
        this.testDate = new GregorianCalendar();
    }

    //Methods
    public double getAlcohol(double originalGravity) {
        return (76.08 * (originalGravity - this.testGravity) / (1.775 - originalGravity)) * (this.testGravity / 0.794);  // Calculation to computer ABV
    }

}

