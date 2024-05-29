package it.unipa.wsda.gestione.dto;

public class ReportDTO {
    private Integer refCartellone;
    private String nome;
    private Integer risultato;

    public ReportDTO(Integer refCartellone, String nome, Integer risultato) {
        this.refCartellone = refCartellone;
        this.nome = nome;
        this.risultato = risultato;
    }

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

    public void setRisultato(Integer risultato) {
        this.risultato = risultato;
    }
}