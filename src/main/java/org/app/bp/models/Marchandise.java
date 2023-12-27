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
public class Marchandise {
    private int id_marchandise;
    private String nom;
    private String code;
    private double prix;
    private int etat;

    public int getId_marchandise() {
        return id_marchandise;
    }

    public void setId_marchandise(int id_marchandise) {
        this.id_marchandise = id_marchandise;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public int getEtat() {
        return etat;
    }

    public void setEtat(int etat) {
        this.etat = etat;
    }
}
