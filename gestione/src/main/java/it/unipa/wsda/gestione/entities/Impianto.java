package it.unipa.wsda.gestione.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;

import java.math.BigDecimal;

@Entity
public class Impianto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer codImpianto;
    private String descrizione;
    @Column(precision = 10, scale = 6)
    private BigDecimal latitudine, longitudine;

    @ManyToOne
    @JoinColumn(name = "ref_palinsesto", nullable = false)
    private Palinsesto palinsesto;
    private boolean stato;

    public Impianto(String descrizione, BigDecimal latitudine, BigDecimal longitudine, Palinsesto palinsesto) {
        this.descrizione = descrizione;
        this.latitudine = latitudine;
        this.longitudine = longitudine;
        this.palinsesto = palinsesto;
    }

    public Impianto() {}

    public Integer getCodImpianto() {
        return codImpianto;
    }

    public void setCodImpianto(Integer codImpianto) {
        this.codImpianto = codImpianto;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public BigDecimal getLatitudine() {
        return latitudine;
    }

    public void setLatitudine(BigDecimal latitudine) {
        this.latitudine = latitudine;
    }

    public BigDecimal getLongitudine() {
        return longitudine;
    }

    public void setLongitudine(BigDecimal longitudine) {
        this.longitudine = longitudine;
    }

    public Palinsesto getPalinsesto() {
        return palinsesto;
    }

    public void setPalinsesto(Palinsesto palinsesto) {
        this.palinsesto = palinsesto;
    }

    public boolean isAttivo() {
        return stato;
    }

    public void setStato(boolean stato) {
        this.stato = stato;
    }
}