/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.app.bp.models;

/**
 *
 * @author Cocone
 */
//@Table(nom = "facturation")
public class Facturartion {
    private double caisse;
    private String date_payement;
//    private Date date;

    @Override
    public String toString() {
        return "Facturartion{" + "caisse=" + caisse + ", date_payement=" + date_payement + '}';
    }

    public double getCaisse() {
        return caisse;
    }

    public void setCaisse(double caisse) {
        this.caisse = caisse;
    }

    public String getDate_payement() {
        return date_payement;
    }

    public void setDate_payement(String date_payement) {
        this.date_payement = date_payement;
    }

   
    
}
