import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by ctilluma on 10/8/16.
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
    private List<Honey> honey; //Honey in Batch
    private String name; //Mead Name


    // Constructors
    public Mead() {
        this(0.00, 0.00);
    }

    public Mead(double OG, double volume) {
        this(OG, volume, volume);
    }

    public Mead(double OG, double capacity, double volume) {
        this(OG, capacity, volume, new ArrayList<Honey>());
    }

    public Mead(double OG, double capacity, double volume, List<Honey> honey) {
        this(OG,capacity, volume, honey, new GregorianCalendar(), new SpecGravity(OG));
    }

    public Mead(double OG, double capacity, double volume, List<Honey> honey, GregorianCalendar startDate, SpecGravity lastTest) {
        this(OG,capacity, volume, honey, startDate, lastTest, new ArrayList<SpecGravity>(), 0.00, "");
    }


    public Mead(double OG, double capacity, double volume, List<Honey> honey, GregorianCalendar startDate, SpecGravity lastTest, List<SpecGravity> testResults, double alcohol, String name) {
        this.OG = OG;
        this.capacity = capacity;
        this.volume = volume;
        this.honey = honey;
        this.startDate = startDate;
        this.lastTest = lastTest;
        this.testResults = testResults;
        this.alcohol = alcohol;
        this.name = name;
    }


    // Getter and Setter Methods
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
    }

    public double getAlcohol() {
        return alcohol;
    }

    public void setAlcohol(double alcohol) {
        this.alcohol = alcohol;
    }

    public List<Honey> getHoney() {
        return honey;
    }

    public void setHoney(List<Honey> honey) {
        this.honey = honey;
    }

    // Methods
    public void newTest(SpecGravity testData) {
        this.testResults.add(testData);
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

        for (int i=0;i<honey.size();i++) { //Loop through and add volume of honey and SG adjustment
            currentHoney = honey.get(i);
            honeyTotal += currentHoney.getVolume();
            honeyAdjustment += ((currentHoney.getSG() * currentHoney.getVolume()) / this.volume );
        }

        this.setOG((this.volume - honeyTotal) + honeyAdjustment);
    }
}
