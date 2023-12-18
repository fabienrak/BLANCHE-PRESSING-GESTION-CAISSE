package org.app.bp.utils;

public class Erreur extends Exception{
    private String message = "";

    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * @param message
     * @param message2
     */
    public Erreur(String message) {
        super(message);
        this.message = message;
    }

}
