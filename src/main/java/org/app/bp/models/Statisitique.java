/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.app.bp.models;

import java.sql.Date;
import java.text.NumberFormat;

/**
 *
 * @author Cocone
 */
public class Statisitique {
    Date date;
    double prix;
    int moi;
    int annee;
    int semaine;
    private int num_semaine_moi;
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

    public int getMoi() {
        return moi;
    }

    public void setMoi(int moi) {
        this.moi = moi;
    }

    public int getAnnee() {
        return annee;
    }

    public void setAnnee(int annee) {
        this.annee = annee;
    }

    public int getSemaine() {
        return semaine;
    }

    public void setSemaine(int semaine) {
        this.semaine = semaine;
    }

    public int getJour() {
        return jour;
    }

    public void setJour(int jour) {
        this.jour = jour;
    }
    int jour;

    
    public Date getDate() {
        return date;
    }

    @Override
    public String toString() {
        return "Statisitique{" + "date=" + date + ", prix=" + prix + ", moi=" + moi + ", annee=" + annee + ", semaine=" + semaine + ", num_semaine_moi=" + num_semaine_moi + ", jour=" + jour + '}';
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public int getNum_semaine_moi() {
        return num_semaine_moi;
    }

    public void setNum_semaine_moi(int num_semaine_moi) {
        this.num_semaine_moi = num_semaine_moi;
    }
}
