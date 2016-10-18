package com.inspiredpanama.inspiredmead;

import java.util.GregorianCalendar;

/**
 * Created by ctilluma on 10/10/16.
 *
 * Holds a small amount of information about each Mead batch for use in displaying
 */

public class MeadData {
    //Variables
    private String name;
    private long id;
    private GregorianCalendar date;     // Last Test
    private GregorianCalendar brewDate;  // When was brewed
    private double alcohol, volume;


    //Constructor
    public MeadData(String name, long id, GregorianCalendar date, GregorianCalendar brewDate, double alcohol, double volume) {
        this.name = name;
        this.id = id;
        this.date = date;
        this.alcohol = alcohol;
        this.volume = volume;
    }

    public MeadData(Mead myMead) {
        this.name = myMead.getName();
        this.id = myMead.getId();
        if (myMead.getLastTest() == null) {
            this.date = myMead.getStartDate();
        } else {
            this.date = myMead.getLastTest().getTestDate();
        }
        this.brewDate = myMead.getStartDate();
        this.alcohol = myMead.getAlcohol();
        this.volume = myMead.getVolume();
    }

    //Getter and Setter Methods
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public GregorianCalendar getDate() {
        return date;
    }

    public void setDate(GregorianCalendar date) {
        this.date = date;
    }

    public GregorianCalendar getBrewDate() {
        return brewDate;
    }

    public void setBrewDate(GregorianCalendar brewDate) {
        this.brewDate = brewDate;
    }

    public double getAlcohol() {
        return alcohol;
    }

    public void setAlcohol(double alcohol) {
        this.alcohol = alcohol;
    }

    public double getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }

}
