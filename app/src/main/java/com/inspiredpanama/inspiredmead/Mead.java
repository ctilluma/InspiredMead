package com.inspiredpanama.inspiredmead;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by ctilluma on 10/8/16.
 *
 * Class to hold information about tanks of Mead
 */

public class Mead {


    // Variables
    private GregorianCalendar startDate;
    private double OG; //Original
    private SpecGravity lastTest; //Most Recent test results
    private List<SpecGravity> testResults; // List of all test results
    private double capacity; // Holds tank total capacity
    private double volume; // Current volume used in tank
    private double alcohol; //Current ABV
    private List<Honey> honey; //com.inspiredpanama.inspiredmead.Honey in Batch
    private List<Additive> additive; //com.inspiredpanama.inspiredmead.Additive in Batch
    private String name; //com.inspiredpanama.inspiredmead.Mead Name
    private long id; //database ID
    private double waste; //Amount of waste
    private double origVolume; //Original volume of batch


    // Constructors
    public Mead() {
        this(0.00, 0.00);
    }

    public Mead(String name) {
        this(0.00, 0.00);
        this.name = name;
    }

    public Mead(double OG, double volume) {
        this(OG, volume, volume);
    }

    public Mead(double OG, double capacity, double volume) {
        this(OG, capacity, volume, null);
    }

    public Mead(double OG, double capacity, double volume, List<Honey> honey) {
        this(OG, capacity, volume, honey, new GregorianCalendar(), null);
    }

    public Mead(double OG, double capacity, double volume, List<Honey> honey, GregorianCalendar startDate, SpecGravity lastTest) {
        this(-1, OG, capacity, volume, honey, startDate, lastTest, null, 0.00, "", 0.00, volume);
    }

    public Mead(long id, String name, double OG, double capacity, double volume, double alcohol) {
        this(id, OG, capacity, volume, null, new GregorianCalendar(), null, null, alcohol, name, 0.00, volume);
    }

    public Mead(long id, double OG, double capacity, double volume, List<Honey> honey, GregorianCalendar startDate, SpecGravity lastTest, List<SpecGravity> testResults, double alcohol, String name, double waste, double origVolume) {
        this.id = id;
        this.OG = OG;
        this.capacity = capacity;
        this.volume = volume;
        this.honey = honey;
        this.startDate = startDate;
        this.lastTest = lastTest;
        this.testResults = testResults;
        this.alcohol = alcohol;
        this.name = name;
        this.waste = waste;
        this.origVolume = origVolume;
    }


    // Getter and Setter Methods
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public GregorianCalendar getStartDate() {
        return startDate;
    }

    public void setStartDate(GregorianCalendar startDate) {
        this.startDate = startDate;
    }

    public double getOG() {
        return OG;
    }

    public void setOG(double OG) {
        this.OG = OG;
    }

    public SpecGravity getLastTest() {
        return lastTest;
    }

    public void setLastTest(SpecGravity lastTest) {
        this.lastTest = lastTest;
    }

    public List<SpecGravity> getTestResults() {
        return testResults;
    }

    public void setTestResults(List<SpecGravity> testResults) {
        this.testResults = testResults;
    }

    public double getCapacity() {
        return capacity;
    }

    public void setCapacity(double capacity) {
        this.capacity = capacity;
    }

    public double getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
        if (this.origVolume <= 0.00) {
            this.origVolume = volume;
        }
    }

    public double getAlcohol() {
        return alcohol;
    }

    public void setAlcohol(double alcohol) {
        this.alcohol = alcohol;
    }

    public void addWaste(double waste) {
        this.waste += waste;
        this.volume -= waste;
    }

    public double getWaste() {
        return waste;
    }

    public void setWaste(double waste) {
        this.waste = waste;
    }

    public double getOrigVolume() {
        return origVolume;
    }

    public void setOrigVolume(double origVolume) {
        this.origVolume = origVolume;
    }

    public List<Honey> getHoney() {
        return honey;
    }

    public void setHoney(List<Honey> honey) {
        this.honey = honey;
    }

    public List<Additive> getAdditive() {
        return additive;
    }

    public void setAdditive(List<Additive> additive) {
        this.additive = additive;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Methods
    public void newTest(SpecGravity testData) {
        if (testResults == null) {
            testResults = new ArrayList<>();
        }

        testResults.add(testData);
        if (testData.getTestDate().after(this.getLastTest())) {  // Check if newest test is more current
            this.setLastTest(testData);         //Set to new test
            this.setAlcohol(testData.getAlcohol(this.getOG()));  //Set alcohol level
        }
    }

    public int addHoney(Honey honey, double quantityHoney) {
        //Check for a negative quantity
        if (quantityHoney < 0.0) {
            return 1; //Return 1 for negative quantity
        }

        //increase volumes appropriately
        honey.setVolume(quantityHoney); //Set the amount of honey
        this.honey.add(honey); //Add new honey to list
        this.volume += quantityHoney; //Add volume to batch

        //check new volume is less than capacity or return error
        if (this.volume > this.capacity) {
            this.volume = this.capacity;
            return 2; //Return 2 for overcapacity
        }

        //return success
        return 0;
    }

    public void computeOriginalGravity() {
        double honeyAdjustment = 0.00;
        double honeyTotal = 0.00;
        Honey currentHoney;

        for (int i = 0; i < honey.size(); i++) { //Loop through and add volume of honey and SG adjustment
            currentHoney = honey.get(i);
            honeyTotal += currentHoney.getVolume();
            honeyAdjustment += ((currentHoney.getSG() * currentHoney.getVolume()) / this.volume);
        }

        this.setOG((this.volume - honeyTotal) + honeyAdjustment);
    }

    public void setMostCurrentTest() {
        SpecGravity currentTest = null;
        SpecGravity loopTestResult;

        //Loop through tests
        if (this.getTestResults().size() > 1) {
            for (int i = 0; i < this.getTestResults().size(); i++) {
                loopTestResult = this.getTestResults().get(i);

                if (currentTest == null) {  //If current is null, fill with first record
                    currentTest = loopTestResult;
                } else { //otherwise check for more current test
                    if (currentTest.getTestDate().after(loopTestResult.getTestDate())) {
                        currentTest = loopTestResult;  //set to more current
                    }
                }
            }
        }

        this.lastTest = currentTest;
    }
}
