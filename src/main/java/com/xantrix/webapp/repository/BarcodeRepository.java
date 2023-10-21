package com.xantrix.webapp.repository;

import com.xantrix.webapp.entities.Barcode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BarcodeRepository extends JpaRepository<Barcode, String> {
    Barcode findByBarcode(String barcode);
}
