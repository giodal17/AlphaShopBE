package com.xantrix.webapp.repository;

import com.xantrix.webapp.entities.Articoli;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;


import java.util.List;

public interface ArticoliRepository extends PagingAndSortingRepository<Articoli, String> {
    @Query(value="SELECT * FROM ARTICOLI WHERE DESCRIZIONE LIKE :desArt", nativeQuery = true)
    List<Articoli> SelByDescrizioneLike(@Param("desArt") String descrizione);
    List<Articoli> findByDescrizioneLike(String descrizione, Pageable pageable);
    Articoli findByCodArt(String codArt);
    @Query(value = "SELECT a FROM Articoli a JOIN a.barcode b WHERE b.barcode IN (:ean)")
    Articoli selByEan(@Param("ean") String ean);
}
