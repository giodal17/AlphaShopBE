package com.xantrix.webapp.exceptions;

public class DuplicateException extends Exception{

    private static final long serialVersionUID = 3515241122380060392L;
    private String messaggio;

    public DuplicateException() {
       super();
    }

    public DuplicateException(String messaggio) {
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
