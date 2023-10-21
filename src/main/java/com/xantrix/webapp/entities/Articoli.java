package com.xantrix.webapp.entities;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="ARTICOLI")
@Data
public class Articoli implements Serializable {

    private static final long serialVersionUID = 7361753083273455478L;
    @Id
    @Column(name="CODART")
    @Size(min = 5, max = 20, message = "{Size.Articoli.codArt.Validation}")
    @NotNull(message = "{NotNull.Articoli.codArt.Validation}")
    private String codArt;

    @Column(name="DESCRIZIONE")
    @Size(min = 6, max = 80, message = "Il campo Descrizione deve avere un numero di caratteri compreso tra 6 e 80")
    private String descrizione;

    @Column(name="UM")
    private String um;

    @Column(name="CODSTAT")
    private String codStat;

    @Column(name="PZCART")
    @Max(value=99, message = "{Max.Articoli.pzCart.Validation}")
    private Integer pzCart;

    @Column(name="PESONETTO")
    @Min(value= (long) 0.01, message = "{Max.Articoli.pzCart.Validation}")
    private double pesoNetto;

    @Column(name="IDSTATOART")
    private String idStatoArt;

    @Temporal(TemporalType.DATE)
    @Column(name="DATACREAZIONE")
    private Date dataCreaz;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "articolo" ,orphanRemoval = true)
    @JsonManagedReference
    private Set<Barcode> barcode = new HashSet<>();

    @OneToOne(mappedBy = "articolo", cascade = CascadeType.ALL, orphanRemoval = true)
    private Ingredienti ingredienti;

    @ManyToOne
    @JoinColumn(name="IDIVA", referencedColumnName = "idIva")
    private Iva iva;

    @ManyToOne
    @JoinColumn(name="IDFAMASS", referencedColumnName = "id")
    private FamAssort famAssort;

    @Override
    public String toString() {
        return "Articoli{" +
                "codArt='" + codArt + '\'' +
                ", descrizione='" + descrizione + '\'' +
                ", um='" + um + '\'' +
                ", codStat='" + codStat + '\'' +
                ", pzCart=" + pzCart +
                ", pesoNetto=" + pesoNetto +
                ", idStatoArt='" + idStatoArt + '\'' +
                ", dataCreaz=" + dataCreaz +
                '}';
    }
}
