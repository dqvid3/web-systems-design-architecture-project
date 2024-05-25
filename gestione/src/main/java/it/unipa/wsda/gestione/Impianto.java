package it.unipa.wsda.gestione;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Impianto {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer codImpianto;

    private Integer refPalinsesto;

    public Integer getCodImpianto() {
        return codImpianto;
    }

    public void setCodImpianto(Integer codImpianto) {
        this.codImpianto = codImpianto;
    }

    public Integer getRefPalinsesto() {
        return this.refPalinsesto;
    }

    public void setRefPalinsesto(Integer refPalinsesto) {
        this.refPalinsesto = refPalinsesto;
    }
}