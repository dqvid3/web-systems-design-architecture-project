package it.unipa.wsda.gestione.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Impianto {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer codImpianto;
    private Integer refPalinsesto;
    private boolean stato;


    public Integer getCodImpianto() {
        return codImpianto;
    }

    public void setCodImpianto(int codImpianto) {
        this.codImpianto = codImpianto;
    }

    public Integer getRefPalinsesto() {
        return refPalinsesto;
    }

    public void setRefPalinsesto(int refPalinsesto) {
        this.refPalinsesto = refPalinsesto;
    }

    public boolean isAttivo() {
        return stato;
    }

    public void setStato(boolean stato) {
        this.stato = stato;
    }
}