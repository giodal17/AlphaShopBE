package com.xantrix.webapp.services;

import com.xantrix.webapp.dtos.CategoriaDto;
import com.xantrix.webapp.entities.FamAssort;
import com.xantrix.webapp.repository.CategorieRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
@Service
@Transactional()
public class CategorieServiceImpl implements CategorieService{

    @Autowired
    CategorieRepository categorieRepository;
    @Autowired
    ModelMapper modelMapper;
    @Override
    public List<CategoriaDto> selTutti() {
        List<FamAssort> categorie = categorieRepository.findAll();
        return categorie
                .stream()
                .map(source -> modelMapper.map(source, CategoriaDto.class))
                .collect(Collectors.toList());
    }
}
