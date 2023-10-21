package com.xantrix.webapp.controllers;

import com.xantrix.webapp.dtos.IvaDto;
import com.xantrix.webapp.services.IvaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/iva")
@CrossOrigin("http://localhost:4200")
public class IvaController {

    @Autowired
    IvaService ivaService;
    @GetMapping()
    public ResponseEntity<List<IvaDto>> getAll(){
        return ResponseEntity.ok(ivaService.selTutti());
    }
}
