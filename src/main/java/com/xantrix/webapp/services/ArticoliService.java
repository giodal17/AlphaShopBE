package com.xantrix.webapp.services;

import com.xantrix.webapp.dtos.ArticoliDto;
import com.xantrix.webapp.entities.Articoli;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ArticoliService {
    public List<ArticoliDto> selTutti();
    public List<ArticoliDto> selByDescrizione(String descrizione);
    public List<ArticoliDto> selByDescrizione(String descrizione, Pageable pageable);
    public ArticoliDto selByBarcode(String barcode);
    public Articoli selByCodArt(String codArt);
    public void delArt(Articoli articolo);
    public void insArt(Articoli articolo);
}
