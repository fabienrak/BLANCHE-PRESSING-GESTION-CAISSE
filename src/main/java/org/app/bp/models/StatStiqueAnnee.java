/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.app.bp.models;

import java.text.NumberFormat;

/**
 *
 * @author Cocone
 */
public class StatStiqueAnnee {
    int annee;
    double prix;
    String affichePrix;
    
    /**
     * @return the affichePrix
     */
    public String getAffichePrix() {
        return NumberFormat.getInstance(java.util.Locale.FRENCH).format(prix);
    }

    /**
     * @param affichePrix the affichePrix to set
     */
    public void setAffichePrix(String affichePrix) {
        this.affichePrix = affichePrix;
    }

    @Override
    public String toString() {
        return annee +"";
    }

    public int getAnnee() {
        return annee;
    }

    public void setAnnee(int annee) {
        this.annee = annee;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }
}
