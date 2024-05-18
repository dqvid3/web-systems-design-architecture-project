package it.unipa.wsda.monitoraggio;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class Impianto {
    private int cod_impianto;
    private String descrizione;
    private BigDecimal latitudine;
    private BigDecimal longitudine;
    private Timestamp ultimo_segnale;

    public Impianto(int cod_impianto, String descrizione, BigDecimal latitudine, BigDecimal longitudine, Timestamp ultimo_segnale) {
        this.setCod_impianto(cod_impianto);
        this.setDescrizione(descrizione);
        this.setLatitudine(latitudine);
        this.setLongitudine(longitudine);
        this.setUltimo_segnale(ultimo_segnale);
    }

    public int getCod_impianto() {
        return this.cod_impianto;
    }

    public String getDescrizione() {
        return this.descrizione;
    }

    public BigDecimal getLatitudine() {
        return this.latitudine;
    }

    public BigDecimal getLongitudine() {
        return this.longitudine;
    }

    public Timestamp getUltimo_segnale() {
        return this.ultimo_segnale;
    }

    public void setCod_impianto(int cod_impianto) {
        this.cod_impianto = cod_impianto;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public void setLatitudine(BigDecimal latitudine) {
        this.latitudine = latitudine;
    }

    public void setLongitudine(BigDecimal longitudine) {
        this.longitudine = longitudine;
    }

    public void setUltimo_segnale(Timestamp ultimo_segnale) {
        this.ultimo_segnale = ultimo_segnale;
    }

    @Override
    public String toString() {
        StringBuilder impianto = new StringBuilder();
        impianto.append(this.getLatitudine()).append("\t").append(this.getLongitudine()).append("\tImpianto ")
            .append(this.getCod_impianto()).append("\t").append(this.getDescrizione()).append("<br/><br/><B>");
        impianto.append(this.isAttivo() ? "Attivo" : "Non attivo");
        String ultimo_segnale = this.getUltimo_segnale().toString().substring(0, this.getUltimo_segnale().toString().indexOf("."));
        impianto.append("</B><br/>Ultimo segnale: <B><br/>").append(ultimo_segnale).append("</B>\t");
        impianto.append(this.isAttivo() ? "green.png" : "red.png").append("\t58,96\t").append("-29,-96\n");
        return impianto.toString();
    }

    public boolean isAttivo() {
        return (this.getUltimo_segnale().compareTo(new Timestamp(System.currentTimeMillis() -
            java.time.Duration.ofMinutes(2).toMillis())) >= 0);
    }
}
