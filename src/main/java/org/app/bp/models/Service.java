package org.app.bp.models;

public class Service {
    private int idService = 0;
    private String nomService = null;

    public Service(int idService, String nomService) {
        this.idService = idService;
        this.nomService = nomService;
    }
    public int getIdService() {
        return idService;
    }
    public void setIdService(int idService) {
        this.idService = idService;
    }
    public String getNomService() {
        return nomService;
    }
    public void setNomService(String nomService) {
        this.nomService = nomService;
    }
    public String toString(){
        return this.nomService;
    }
    
}
