import java.util.GregorianCalendar;

/**
 * Created by ctilluma on 10/8/16.
 */

public class Mead {


     // Variables
    private GregorianCalendar startDate;
    private double OG; //Original
    private SpecGravity lastTest; //Most Recent test results
    private SpecGravity[] testResults; // List of all test results
    private double capacity; // Holds tank total capacity
    private double volume; // Current volume used in tank
    private double alcohol; //Current ABV
    private Honey[] honey; //Honey in Batch


    // Constructors
    public Mead() {
        this(0.00, 0.00);
    }

    public Mead(double OG, double volume) {
        this(OG, volume, volume);
    }

    public Mead(double OG, double capacity, double volume) {
        this(OG, capacity, volume, new Honey[0]);
    }

    public Mead(double OG, double capacity, double volume, Honey[] honey) {
        this(OG,capacity, volume, honey, new GregorianCalendar(), new SpecGravity(OG));
    }

    public Mead(double OG, double capacity, double volume, Honey[] honey, GregorianCalendar startDate, SpecGravity lastTest) {
        this(OG,capacity, volume, honey, startDate, lastTest, new SpecGravity[0], 0.00);
    }

    public Mead(double OG, double capacity, double volume, Honey[] honey, GregorianCalendar startDate, SpecGravity lastTest, SpecGravity[] testResults, double alcohol) {
        this.OG = OG;
        this.capacity = capacity;
        this.volume = volume;
        this.honey = honey;
        this.startDate = startDate;
        this.lastTest = lastTest;
        this.testResults = testResults;
        this.alcohol = alcohol;
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

    public SpecGravity[] getTestResults() {
        return testResults;
    }

    public void setTestResults(SpecGravity[] testResults) {
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

    public Honey[] getHoney() {
        return honey;
    }

    public void setHoney(Honey[] honey) {
        this.honey = honey;
    }


}
