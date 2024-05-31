package it.unipa.wsda.gestione.entities;

import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
public class Visualizzazione {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer codSegnalazione;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ref_impianto")
    private Impianto impianto;
    @ManyToOne
    @JoinColumn(name = "ref_cartellone", nullable = false)
    private Cartellone cartellone;
    private Integer durataVisualizzazione;
    private Timestamp ultimoSegnale;

    public Integer getCodSegnalazione() {
        return codSegnalazione;
    }

    public void setCodSegnalazione(Integer codSegnalazione) {
        this.codSegnalazione = codSegnalazione;
    }

    public Impianto getImpianto() {
        return impianto;
    }

    public void setImpianto(Impianto impianto) {
        this.impianto = impianto;
    }

    public Cartellone getCartellone() {
        return cartellone;
    }

    public void setCartellone(Cartellone cartellone) {
        this.cartellone = cartellone;
    }

    public Integer getDurataVisualizzazione() {
        return durataVisualizzazione;
    }

    public void setDurataVisualizzazione(Integer durataVisualizzazione) {
        this.durataVisualizzazione = durataVisualizzazione;
    }

    public Timestamp getUltimoSegnale() {
        return ultimoSegnale;
    }

    public void setUltimoSegnale(Timestamp ultimoSegnale) {
        this.ultimoSegnale = ultimoSegnale;
    }
}
