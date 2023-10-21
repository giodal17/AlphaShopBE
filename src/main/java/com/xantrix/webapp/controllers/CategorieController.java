package com.xantrix.webapp.controllers;

import com.xantrix.webapp.dtos.CategoriaDto;
import com.xantrix.webapp.services.CategorieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/cat")
@CrossOrigin("http://localhost:4200")
public class CategorieController {
    @Autowired
    CategorieService categorieService;

    @GetMapping()
    public ResponseEntity<List<CategoriaDto>> getAll(){
        List<CategoriaDto> categorie = categorieService.selTutti();
        return ResponseEntity.ok(categorie);
    }
}
