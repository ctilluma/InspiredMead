package com.inspiredpanama.inspiredmead;

/**
 * Created by ctilluma on 10/8/16.
 *
 * Class to hold information about additives
 */

public class Additive {
    //Variables
    private long id; //id record
    private long additiveID;
    private double brix; // Sugar content of this com.inspiredpanama.inspiredmead.Honey
    private String name; // Name of honey
    private String flavour; // Flavor of com.inspiredpanama.inspiredmead.Honey
    private double amount; //Volume of com.inspiredpanama.inspiredmead.Honey
    private boolean isMetric;
    private boolean isWeight;

    //Constructors
    public Additive() {
        this("");
    }

    public Additive(String name) {
        this(name, "");
    }

    public Additive(String name, String flavour) {
        this(81.5, name, flavour);   // Use rough Brix value
    }

    public Additive(double brix, String name, String flavour) {
        this(-1, 0, brix, name, flavour, 0.00, true, false); //send -1 for id since no database id known
    }

    public Additive(long id, double brix, String name, String flavour) {
        this(id, 0, brix, name, flavour, 0.00, true, false);
    }

    public Additive(long id, long additiveID, double brix, String name, String flavour, double amount, boolean isMetric, boolean isWeight) {
        this.additiveID = additiveID;
        this.brix = brix;
        this.name = name;
        this.flavour = flavour;
        this.amount = amount;
        this.isMetric = isMetric;
        this.isWeight = isWeight;
        this.id = id;
    }

    //Getter and Setter Methods
    public long getID() {
        return id;
    }

    public void setID(long id) {
        this.id = id;
    }

    public long getAdditiveID() {
        return additiveID;
    }

    public void setAdditiveID(long additiveID) {
        this.additiveID = additiveID;
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

    public double getAmount() {
        return amount;
    }

    public void setAmount(double volume) {
        this.amount = volume;
    }

    public boolean getMetric() {
        return isMetric;
    }

    public void setMetric(boolean isMetric) {
        this.isMetric = isMetric;
    }

    public boolean getIsWeight() {
        return isWeight;
    }

    public void setIsWeight(boolean isWeight) {
        this.isWeight = isWeight;
    }

    public double getSG() {
        return ((brix / (258.6 - ((brix / 258.2) * 227.1))) + 1);
    }

    public void setSG(double sg) {
        brix = ((((182.4601 * sg - 775.6821) * sg + 1262.7794) * sg - 669.5622));
    }

}

