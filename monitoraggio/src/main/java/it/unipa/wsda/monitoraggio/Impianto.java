package it.unipa.wsda.monitoraggio;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

public class Impianto implements Serializable {
    private int codImpianto;
    private String descrizione;
    private BigDecimal latitudine;
    private BigDecimal longitudine;
    private Timestamp ultimoSegnale;
    @Serial
    private static final long serialVersionUID = 12345;
    /*
    public Impianto() {
        this(0, "", BigDecimal.ZERO, BigDecimal.ZERO, new Timestamp(System.currentTimeMillis()));
    }
    */
    public Impianto(int codImpianto, String descrizione, BigDecimal latitudine, BigDecimal longitudine, Timestamp ultimoSegnale) {
        this.codImpianto = codImpianto;
        this.descrizione = descrizione;
        this.latitudine = latitudine;
        this.longitudine = longitudine;
        this.ultimoSegnale = ultimoSegnale;
    }

    public int getCodImpianto() {
        return this.codImpianto;
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

    public Timestamp getUltimoSegnale() {
        return this.ultimoSegnale;
    }

    public void setCodImpianto(int codImpianto) {
        this.codImpianto = codImpianto;
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

    public void setUltimoSegnale(Timestamp ultimoSegnale) {
        this.ultimoSegnale = ultimoSegnale;
    }

    @Override
    public String toString() {
        StringBuilder impianto = new StringBuilder();
        impianto.append(this.getLatitudine()).append("\t").append(this.getLongitudine()).append("\t");
        impianto.append("<div class='title'>Impianto ").append(this.getCodImpianto()).append("</div>\t");
        impianto.append("<div class='description'>").append(this.getDescrizione()).append("</div>");
        String statoClass = this.isAttivo() ? "attivo" : "nonattivo";
        impianto.append("<div class='state ").append(statoClass).append("'><B>").append(this.isAttivo() ? "Attivo" : "Non attivo").append("</B></div>");
        String ultimoSegnale = this.getUltimoSegnale().toString().substring(0, this.getUltimoSegnale().toString().indexOf("."));
        impianto.append("<div class='heartbeat'>Ultimo segnale: <B>").append(ultimoSegnale).append("</B></div>\t../imgs/");
        impianto.append(this.isAttivo() ? "green.png" : "red.png").append("\t58,96\t").append("-29,-96\n");
        return impianto.toString();
    }

    public boolean isAttivo() {
        return (this.getUltimoSegnale().compareTo(new Timestamp(System.currentTimeMillis() -
                java.time.Duration.ofMinutes(2).toMillis())) >= 0);
    }
}
