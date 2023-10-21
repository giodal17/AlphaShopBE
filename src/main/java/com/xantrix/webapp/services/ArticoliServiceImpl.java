package com.xantrix.webapp.services;

import com.xantrix.webapp.dtos.ArticoliDto;
import com.xantrix.webapp.entities.Articoli;
import com.xantrix.webapp.repository.ArticoliRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional()
public class ArticoliServiceImpl implements ArticoliService{

    @Autowired
    ArticoliRepository articoliRepository;

    @Autowired
    ModelMapper modelMapper;

    private ArticoliDto convertToDto(Articoli a){
        ArticoliDto articoloDto = null;
        if(a!=null && a.getDescrizione()!=null && a.getUm()!=null && a.getIdStatoArt()!=null){
            articoloDto = modelMapper.map(a, ArticoliDto.class);
            articoloDto.setDescrizione(articoloDto.getDescrizione().trim());
            articoloDto.setUm(articoloDto.getUm().trim());
            articoloDto.setIdStatoArt(articoloDto.getIdStatoArt().trim());
        }
        return articoloDto;
    }
    @Override
    public List<ArticoliDto> selTutti() {
        List<Articoli> articoli = (List<Articoli>) articoliRepository.findAll();
        List<ArticoliDto> articoliDtoList = new ArrayList<ArticoliDto>();
        for (Articoli a : articoli) {
            if (a != null) {
                articoliDtoList.add(this.convertToDto(a));
            }
        }
        return articoliDtoList;
    }

    @Override
    public List<ArticoliDto> selByDescrizione(String descrizione) {
        descrizione = descrizione.toUpperCase();
        List<Articoli> articoli = articoliRepository.SelByDescrizioneLike(descrizione);
        List<ArticoliDto> articoliDtoList = new ArrayList<ArticoliDto>();
        if(articoli!=null){
            for(Articoli a: articoli){
                if(a!=null){
                    articoliDtoList.add(this.convertToDto(a));
                }
            }
        }

        return articoliDtoList;
    }

    @Override
    public List<ArticoliDto> selByDescrizione(String descrizione, Pageable pageable) {
        descrizione = descrizione.toUpperCase();
        List<Articoli> articoli = articoliRepository.findByDescrizioneLike(descrizione, pageable);
        List<ArticoliDto> articoliDtoList = new ArrayList<ArticoliDto>();
        if(articoli!=null){
            for(Articoli a: articoli){
                if(a!=null){
                    articoliDtoList.add(this.convertToDto(a));
                }
            }
        }

        return articoliDtoList;
    }

    @Override
    public ArticoliDto selByBarcode(String barcode) {
        return this.convertToDto(articoliRepository.selByEan(barcode));
    }

    @Override
    public Articoli selByCodArt(String codArt) {
        return articoliRepository.findByCodArt(codArt);
    }

    @Override
    public void delArt(Articoli articolo) {
        articoliRepository.delete(articolo);
    }

    @Override
    public void insArt(Articoli articolo) {
        articolo.setDescrizione(articolo.getDescrizione().toUpperCase());
        articolo.setCodStat(articolo.getCodStat().trim());
        articoliRepository.save(articolo);
    }
}
