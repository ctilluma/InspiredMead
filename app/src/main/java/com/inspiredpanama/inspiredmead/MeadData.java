package com.inspiredpanama.inspiredmead;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by ctilluma on 10/10/16.
 */

public class MeadData {
    //Variables
    private String name;
    private long id;
    private GregorianCalendar date;
    private double alcohol, volume;

    //Constructor
    public MeadData(String name, long id, GregorianCalendar date, double alcohol, double volume) {
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
