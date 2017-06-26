package fr.givel.catherine.dsmap;

import java.io.Serializable;

/**
 * Created by catherine on 23/04/17.
 */

public class Coordonnees implements Serializable {
    private String nom;
    private double latitude;
    private double longitude;

    public Coordonnees(String n, double la, double lo) {
        nom = n;
        latitude = la;
        longitude = lo;
    }

    public String getNom() {
        return nom;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude (){
        return longitude;
    }

    public String toString()
    {
        return nom;
    }
}
