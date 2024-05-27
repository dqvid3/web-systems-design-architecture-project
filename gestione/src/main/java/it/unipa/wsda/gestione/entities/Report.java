package it.unipa.wsda.gestione.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer refCartellone;
    private String nome;
    private Integer risultato;

    public Integer getRefCartellone() {
        return refCartellone;
    }

    public void setRefCartellone(Integer refCartellone) {
        this.refCartellone = refCartellone;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getRisultato() {
        return risultato;
    }

    public void setNumeroVisualizzazioni(Integer risultato) {
        this.risultato = risultato;
    }
}
