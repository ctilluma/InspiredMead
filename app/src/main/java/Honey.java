/**
 * Created by ctilluma on 10/8/16.
 */

public class Honey {
    //Variables
    private double brix; // Sugar content of this Honey
    private String name; // Name of honey
    private String flavor; // Flavor of Honey

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
        this.brix = brix;
        this.name = name;
        this.flavor = flavor;
    }

    //Getter and Setter Methods
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

}

