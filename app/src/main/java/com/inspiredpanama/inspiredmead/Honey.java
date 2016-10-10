package com.inspiredpanama.inspiredmead;

/**
 * Created by ctilluma on 10/8/16.
 */

public class Honey {
    //Variables
    private long id; //id record
    private long honeyID;
    private double brix; // Sugar content of this com.inspiredpanama.inspiredmead.Honey
    private String name; // Name of honey
    private String flavour; // Flavor of com.inspiredpanama.inspiredmead.Honey
    private double volume; //Volume of com.inspiredpanama.inspiredmead.Honey

    //Constructors
    public Honey() {
        this(new String());
    }

    public Honey(String name) {
        this(name, new String());
    }

    public Honey(String name, String flavour) {
        this(81.5, name, flavour);   // Use rough Brix value
    }

    public Honey(double brix, String name, String flavour) {
        this(-1, 0, brix, name, flavour, 0.00); //send -1 for id since no database id known
    }

    public Honey(long id, double brix, String name, String flavour) {
        this(id, 0, brix, name, flavour, 0.00);
    }
    
    public Honey(long id, long honeyID, double brix, String name, String flavour, double volume) {
        this.id = id;
        this.honeyID = honeyID;
        this.brix = brix;
        this.name = name;
        this.flavour = flavour;
        this.volume = volume;
    }

    //Getter and Setter Methods
    public long getID() {
        return id;
    }

    public void setID(long id) {
        this.id = id;
    }

    public long getHoneyID() {
        return honeyID;
    }

    public void setHoneyID(long id) {
        this.honeyID = honeyID;
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
        return flavour;
    }

    public void setFlavor(String flavour) {
        this.flavour = flavour;
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

