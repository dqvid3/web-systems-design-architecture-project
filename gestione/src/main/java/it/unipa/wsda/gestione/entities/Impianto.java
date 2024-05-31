package it.unipa.wsda.gestione.entities;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.List;

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

    @OneToMany(mappedBy = "impianto", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Visualizzazione> visualizzazioni;

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

    public boolean getStato() {
        return stato;
    }

    public void setStato(boolean stato) {
        this.stato = stato;
    }
}