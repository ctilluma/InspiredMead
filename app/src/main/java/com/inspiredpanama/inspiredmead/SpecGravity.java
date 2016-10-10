package com.inspiredpanama.inspiredmead;

import java.util.GregorianCalendar;

/**
 * Created by ctilluma on 10/8/16.
 */

// Private class for each test
public class SpecGravity {
    //Variables
    private long id; //id record
    private GregorianCalendar testDate; // Date of this test
    private double testGravity; // Specific Gravity of this test

    //Constructors
    public SpecGravity() {                 //Empty Constructor
        this(0.00);         // Set value to 0.00
    }

    public SpecGravity(double testGravity) {  //Constructor w/ test value, supply current time
        this(-1, new GregorianCalendar(), testGravity); //Set id as negative because it doesn't have a database number
    }

    public SpecGravity(long id, GregorianCalendar testDate, double testGravity) {   //Constructor w/ all info known
        this.id = id;
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

