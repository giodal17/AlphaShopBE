package com.xantrix.webapp.exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RestController
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    public static ResponseEntity<ErrorResponse> exceptionNotFoundHandler(String msg){
        ErrorResponse errore = new ErrorResponse();
        errore.setCodice(HttpStatus.NOT_FOUND.value());
        errore.setMessaggio(msg);
        return new ResponseEntity<ErrorResponse>(errore, new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    public static ResponseEntity<ErrorResponse> exceptionBindingHandler(String msg){
        ErrorResponse errore = new ErrorResponse();
        errore.setCodice(HttpStatus.BAD_REQUEST.value());
        errore.setMessaggio(msg);
        return new ResponseEntity<ErrorResponse>(errore, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    public static ResponseEntity<ErrorResponse> exceptionDuplicateRecordHandler(String msg){
        ErrorResponse errore = new ErrorResponse();
        errore.setCodice(HttpStatus.NOT_ACCEPTABLE.value());
        errore.setMessaggio(msg);
        return new ResponseEntity<ErrorResponse>(errore, new HttpHeaders(), HttpStatus.NOT_ACCEPTABLE);
    }
}
