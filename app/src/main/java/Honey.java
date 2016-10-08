/**
 * Created by ctilluma on 10/8/16.
 */

public class Honey {
    //Variables
    private long id; //id record
    private double brix; // Sugar content of this Honey
    private String name; // Name of honey
    private String flavor; // Flavor of Honey
    private double volume; //Volume of Honey

    //Constructors
    public Honey() {
        this(new String());
    }

    public Honey(String name) {
        this(name, new String());
    }

    public Honey(String name, String flavor) {
        this(81.5, name, flavor);   // Use rough Brix value
    }

    public Honey(double brix, String name, String flavor) {
        this(-1, brix, name, flavor, 0.00); //send -1 for id since no database id known
    }

    public Honey(int id, double brix, String name, String flavor, double volume) {
        this.id = id;
        this.brix = brix;
        this.name = name;
        this.flavor = flavor;
        this.volume = volume;
    }

    //Getter and Setter Methods
    public long getID() {
        return id;
    }

    public void setID(long id) {
        this.id = id;
    }

    public double getBrix() {
        return brix;
    }

    public void setBrix(double brix) {
        this.brix = brix;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFlavor() {
        return flavor;
    }

    public void setFlavor(String flavor) {
        this.flavor = flavor;
    }

    public double getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }

    public double getSG() {
        return (( brix / ( 258.6 - (( brix / 258.2 ) * 227.1 ))) + 1);
    }

}

