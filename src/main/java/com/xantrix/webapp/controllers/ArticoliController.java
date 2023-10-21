package com.xantrix.webapp.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.xantrix.webapp.dtos.ArticoliDto;
import com.xantrix.webapp.dtos.InfoMsg;
import com.xantrix.webapp.entities.Articoli;
import com.xantrix.webapp.exceptions.NotFoundException;
import com.xantrix.webapp.exceptions.RestExceptionHandler;
import com.xantrix.webapp.services.ArticoliService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

@RestController
@RequestMapping("/api/articoli")
@CrossOrigin()
public class ArticoliController {
    private static final Logger logger = LoggerFactory.getLogger(ArticoliController.class);

    @Autowired
    private ArticoliService articoliService;

    @Autowired
    private ResourceBundleMessageSource errMessage;

    @GetMapping(value="/testauth", produces = "application/json")
    public ResponseEntity<?> testAuth(){
        return new ResponseEntity<InfoMsg>(new InfoMsg(LocalDate.now(), "Test Autenticazione Ok!"), HttpStatus.OK);
    }

    @GetMapping(value = "/getall", produces = "application/json")
    public ResponseEntity<?> getAll() {
        logger.info("****** Sto selezionando tutti gli articoli ******");
        List<ArticoliDto> articoliDtoList = articoliService.selTutti();
        if (articoliDtoList.isEmpty()) {
            String errMsg = "Errore nel caricamento dei prodotti";
            logger.warn(errMsg);
            return RestExceptionHandler.exceptionNotFoundHandler(errMsg);
        } else {

            return new ResponseEntity<List<ArticoliDto>>(articoliDtoList, HttpStatus.OK);
        }
    }

    @GetMapping(value = "/cerca/ean/{barcode}", produces = "application/json")
    public ResponseEntity<?> listArtByEan(@PathVariable String barcode) throws NotFoundException {

        logger.info("****** Otteniamo l'articolo con barcode " + barcode + " ******");

        ArticoliDto articoloDto = articoliService.selByBarcode(barcode);

        if(articoloDto == null){
            String errMsg = "Il barcode " +barcode+" non è stato trovato!";
            logger.warn(errMsg);
            return RestExceptionHandler.exceptionNotFoundHandler(errMsg);
        }else{

            return new ResponseEntity<ArticoliDto>(articoloDto,HttpStatus.OK);
        }
    }

    @GetMapping(value = "/cerca/codice/{codart}", produces = "application/json")
    public ResponseEntity<?> listArtByCodArt(@PathVariable("codart") String codArt){

        logger.info("****** Otteniamo l'articolo con codice " +codArt + " ******");
        Articoli articolo = articoliService.selByCodArt(codArt);
        if(articolo == null){
            String errMsg = "L'articolo con codice " +codArt+" non è stato trovato!";
            logger.warn(errMsg);
            return RestExceptionHandler.exceptionNotFoundHandler(errMsg);
        }else{
            return new ResponseEntity<Articoli>(articolo, HttpStatus.OK);
        }

    }
    @GetMapping(value="cerca/descrizione/{filter}", produces = "application/json")
    public ResponseEntity<?> listArtBydesc(@PathVariable String filter){

        logger.info("****** Otteniamo gli articoli con descrizione: "+filter+" ******");

        List<ArticoliDto> articoli = articoliService.selByDescrizione(filter.toUpperCase() + "%");
        if(articoli.isEmpty()){
            String errMsg = "Non è stato trovato alcun articolo avente descrizione "+filter;
            logger.warn(errMsg);
            return RestExceptionHandler.exceptionNotFoundHandler(errMsg);
        }else{
            return new ResponseEntity<List<ArticoliDto>>(articoli, HttpStatus.OK);
        }
    }

    @PostMapping(value = "/inserisci")
    public ResponseEntity<?> createArt(@Valid @RequestBody Articoli articolo, BindingResult bindingResult){
        logger.info("****** Sto inserendo l'articolo con codice " + articolo.getCodArt()+ " ******");

        if(bindingResult.hasErrors()){ //per controllare se ci sono stati errori nella validazione dei dati
            String errMsg = errMessage.getMessage(Objects.requireNonNull(bindingResult.getFieldError()), LocaleContextHolder.getLocale());
            return RestExceptionHandler.exceptionBindingHandler(errMsg);
        }

        Articoli checkArt = articoliService.selByCodArt(articolo.getCodArt());

        if(checkArt!=null) {

            String errMsg = "Articolo " + articolo.getCodArt() + " presente in anagrafica! Impossibile utilizzare il metodo POST";
            logger.warn(errMsg);
            return RestExceptionHandler.exceptionDuplicateRecordHandler(errMsg);
        }
            articoliService.insArt(articolo);
            return new ResponseEntity<InfoMsg>(new InfoMsg(LocalDate.now(), "Inserimento Articolo eseguito con successo"), HttpStatus.CREATED);

    }

    @PutMapping("/modifica")
    public ResponseEntity<?> updateArt(@Valid @RequestBody Articoli articolo, BindingResult bindingResult){
        logger.info("****** Sto modificando l'articolo con codice " + articolo.getCodArt()+ " ******");

        if(bindingResult.hasErrors()){
            String errMsg = errMessage.getMessage(Objects.requireNonNull(bindingResult.getFieldError()), Locale.ITALY);
            logger.error(errMsg);
            return RestExceptionHandler.exceptionBindingHandler(errMsg);
        }

        Articoli checkArt = articoliService.selByCodArt(articolo.getCodArt());
        if(checkArt==null){
            String errMsg = "Articolo " + articolo.getCodArt() + " non presente in anagrafica! Impossibile utilizzare il metodo PUT";
            logger.warn(errMsg);
            return RestExceptionHandler.exceptionNotFoundHandler(errMsg);
        }else{
            articoliService.insArt(articolo);
            return new ResponseEntity<InfoMsg>(new InfoMsg(LocalDate.now(), "Modifica Articolo " + articolo.getCodArt() + " eseguita con successo"), HttpStatus.CREATED);
        }
    }

    @DeleteMapping("/elimina/{codart}")
    public ResponseEntity<?> deleteArt(@PathVariable("codart") String codArt){
        logger.info("****** Sto modificando l'articolo con codice " + codArt+ " ******");

        Articoli chekcArt = articoliService.selByCodArt(codArt);

        if(chekcArt == null){
            String errMsg = "Articolo " + codArt + "non presente in anagrafica! Impossibile utilizzare il metodo DELETE";
            logger.warn(errMsg);
            return RestExceptionHandler.exceptionNotFoundHandler(errMsg);
        }else{
            articoliService.delArt(chekcArt);
            ObjectMapper mapper = new ObjectMapper();
            ObjectNode responseNode = mapper.createObjectNode();
            responseNode.put("codice", HttpStatus.OK.toString());
            responseNode.put("messaggio", "Eliminazione Articolo " + codArt + " Eseguita Con Successo");
            return new ResponseEntity<>(responseNode, HttpStatus.OK);
        }
    }
}
