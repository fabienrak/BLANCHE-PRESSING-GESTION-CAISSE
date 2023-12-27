package org.app.bp.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Role {
    String nom;
    int value = 0;
    /**
     * @param nom
     * @param value
     */
    public Role(String nom, int value) {
        this.nom = nom;
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
    public String toString(){
        return nom;
    }

    public static ObservableList<Role> getAllRole(){
        ObservableList<Role> list = FXCollections.observableArrayList(new Role("ADMIN",0),new Role("UTILISATEUR",1));
        
        return list;
    }

}
