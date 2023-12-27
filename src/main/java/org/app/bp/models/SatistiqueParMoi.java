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
public class SatistiqueParMoi {
    int annee;
    double prix;
    int num_moi ;
    String moi;

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
        String value = moi + " "+annee;
        return value;
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

    public int getNum_moi() {
        return num_moi;
    }

    public void setNum_moi(int num_moi) {
        this.num_moi = num_moi;
    }

    public String getMoi() {
        return moi;
    }

    public void setMoi(String moi) {
        this.moi = moi;
    }
}
