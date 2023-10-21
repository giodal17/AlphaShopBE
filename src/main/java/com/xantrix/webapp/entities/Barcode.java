package com.xantrix.webapp.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="BARCODE")
@Data
public class Barcode implements Serializable {
    private static final long serialVersionUID = 7446639033443828132L;

    @Id
    @Column(name="BARCODE")
    @NotNull(message = "{NotNull.Barcode.barcode.Validation}")
    @Size(min = 8, max = 13, message = "{Size.Barcode.barcode.Validation}")
    private String barcode;

    @Column(name="IDTIPOART")
    @NotNull(message = "{NotNull.Barcode.idTipoArt.Validation}")
    private String idTipoArt;

    @ManyToOne
    @EqualsAndHashCode.Exclude
    @JoinColumn(name = "CODART", referencedColumnName = "codArt")
    @JsonBackReference
    private Articoli articolo;

}
