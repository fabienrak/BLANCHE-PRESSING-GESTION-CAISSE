package org.app.bp.models;

import java.util.ArrayList;
import java.util.List;

public class EtatLivraison {
    private int value = 0;
    private String nom = "";
    /**
     * @return the value
     */
    public int getValue() {
        return value;
    }
    /**
     * @param value the value to set
     */
    public void setValue(int value) {
        this.value = value;
    }
    /**
     * @return the nom
     */
    public String getNom() {
        return nom;
    }
    /**
     * @param nom the nom to set
     */
    public void setNom(String nom) {
        this.nom = nom;
    }
    /**
     * @param value
     * @param nom
     */
    public EtatLivraison(int value, String nom) {
        this.value = value;
        this.nom = nom;
    }

    public String toString(){
        return nom;
    }

    public static List<EtatLivraison> getListeEtatLivraison(){
        List<EtatLivraison> list = new ArrayList<>();
        list.add(new EtatLivraison(6,"TOUT"));
        list.add(new EtatLivraison(1,"LIVRER"));
        list.add(new EtatLivraison(0,"NON LIVRER"));
        return list;
    }

    public static String valueRecherche(EtatLivraison etat){
        String value = "";
        if(etat != null){
            if(etat.getValue() != 6){
                value = String.valueOf(etat.getValue());
            }
        }
        return value;
    }

}
