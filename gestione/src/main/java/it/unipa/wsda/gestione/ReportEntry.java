package it.unipa.wsda.gestione;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class ReportEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    public ReportEntry(int refCartellone, String nome, int numeroVisualizzazioni) {
        this.refCartellone = refCartellone;
        this.nome = nome;
        this.numeroVisualizzazioni = numeroVisualizzazioni;
    }

    public int getRefCartellone() {
        return refCartellone;
    }

    public void setRefCartellone(int refCartellone) {
        this.refCartellone = refCartellone;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getNumeroVisualizzazioni() {
        return numeroVisualizzazioni;
    }

    public void setNumeroVisualizzazioni(int numeroVisualizzazioni) {
        this.numeroVisualizzazioni = numeroVisualizzazioni;
    }
}
