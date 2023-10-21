package com.xantrix.webapp.exceptions;

public class NotFoundException extends Exception{
    private static final long serialVersionUID = -7168287271375943562L;

    private String messaggio = "Elemento non trovato!";

    public NotFoundException(String messaggio) {
        super(messaggio);
        this.messaggio = messaggio;
    }

    public String getMessaggio() {
        return messaggio;
    }

    public void setMessaggio(String messaggio) {
        this.messaggio = messaggio;
    }
}
