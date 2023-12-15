package org.app.bp.models.modelAffichage;

public class Commande {
    private String nomArticle;
    private int nombre;
    private String service;
    private double prixUnitaire;
    private double prixTotal;

    
    /**
     * @param nomArticle
     * @param nombre
     * @param service
     * @param prixUnitaire
     * @param prixTotal
     */
    public Commande(String nomArticle, int nombre, String service, double prixUnitaire, double prixTotal) {
        this.nomArticle = nomArticle;
        this.nombre = nombre;
        this.service = service;
        this.prixUnitaire = prixUnitaire;
        this.prixTotal = prixTotal;
    }
    /**
     * @return the nomArticle
     */
    public String getNomArticle() {
        return nomArticle;
    }
    /**
     * @param nomArticle the nomArticle to set
     */
    public void setNomArticle(String nomArticle) {
        this.nomArticle = nomArticle;
    }
    /**
     * @return the nombre
     */
    public int getNombre() {
        return nombre;
    }
    /**
     * @param nombre the nombre to set
     */
    public void setNombre(int nombre) {
        this.nombre = nombre;
    }
    /**
     * @return the service
     */
    public String getService() {
        return service;
    }
    /**
     * @param service the service to set
     */
    public void setService(String service) {
        this.service = service;
    }
    /**
     * @return the prixUnitaire
     */
    public double getPrixUnitaire() {
        return prixUnitaire;
    }
    /**
     * @param prixUnitaire the prixUnitaire to set
     */
    public void setPrixUnitaire(double prixUnitaire) {
        this.prixUnitaire = prixUnitaire;
    }
    /**
     * @return the prixTotal
     */
    public double getPrixTotal() {
        return prixTotal;
    }
    /**
     * @param prixTotal the prixTotal to set
     */
    public void setPrixTotal(double prixTotal) {
        this.prixTotal = prixTotal;
    }
}
