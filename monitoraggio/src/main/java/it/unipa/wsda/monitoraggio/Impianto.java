package it.unipa.wsda.monitoraggio;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

public class Impianto implements Serializable {
    @Serial
    private static final long serialVersionUID = 12345;
    private int codImpianto;
    private String descrizione;
    private BigDecimal latitudine;
    private BigDecimal longitudine;
    private Timestamp ultimoSegnale;

    private boolean stato;

    /*
    public Impianto() {
        this(0, "", BigDecimal.ZERO, BigDecimal.ZERO, new Timestamp(System.currentTimeMillis()));
    }
    */
    public Impianto(int codImpianto, String descrizione, BigDecimal latitudine, BigDecimal longitudine, Timestamp ultimoSegnale, boolean stato) {
        this.codImpianto = codImpianto;
        this.descrizione = descrizione;
        this.latitudine = latitudine;
        this.longitudine = longitudine;
        this.ultimoSegnale = ultimoSegnale;
        this.stato = stato;
    }

    public int getCodImpianto() {
        return this.codImpianto;
    }

    public void setCodImpianto(int codImpianto) {
        this.codImpianto = codImpianto;
    }

    public String getDescrizione() {
        return this.descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public BigDecimal getLatitudine() {
        return this.latitudine;
    }

    public void setLatitudine(BigDecimal latitudine) {
        this.latitudine = latitudine;
    }

    public BigDecimal getLongitudine() {
        return this.longitudine;
    }

    public void setLongitudine(BigDecimal longitudine) {
        this.longitudine = longitudine;
    }

    public Timestamp getUltimoSegnale() {
        return this.ultimoSegnale;
    }

    public void setUltimoSegnale(Timestamp ultimoSegnale) {
        this.ultimoSegnale = ultimoSegnale;
    }

    public boolean isAcceso() {
        return stato;
    }

    public void setStato(boolean stato) {
        this.stato = stato;
    }

    public String getStatoImpianto() {
        if (!isAcceso())
            return "spento"; // giallo
        if (isAttivo())
            return "attivo"; // verde
        // if (!isAttivo())
        return "nonattivo"; // rosso
    }

    @Override
    public String toString() {
        StringBuilder impianto = new StringBuilder();
        impianto.append(this.getLatitudine()).append("\t").append(this.getLongitudine()).append("\t");
        impianto.append("<div class='title'>Impianto ").append(this.getCodImpianto()).append("</div>\t");
        impianto.append("<div class='description'>").append(this.getDescrizione()).append("</div>");
        String statoClass = getStatoImpianto();
        String statoText = "";
        String statoIcon = "";
        switch (statoClass) {
            case "attivo":
                statoText = "Attivo";
                statoIcon = "green.png";
                break;
            case "nonattivo":
                statoText = "Non attivo";
                statoIcon = "red.png";
                break;
            case "spento":
                statoText = "Spento";
                statoIcon = "yellow.png";
                break;
        }
        String ultimoSegnale = (this.getUltimoSegnale() != null) ?
                this.getUltimoSegnale().toString().substring(0, this.getUltimoSegnale().toString().indexOf(".")) : "N/A";
        impianto.append("<div class='state ").append(statoClass).append("'><B>").append(statoText).append("</B></div>");
        impianto.append("<div class='heartbeat'>Ultimo segnale: <B>")
                .append(ultimoSegnale).append("</B></div>\t../imgs/");
        impianto.append(statoIcon).append("\t58,96\t").append("-29,-96\n");
        return impianto.toString();
    }

    public boolean isAttivo() {
        if (this.getUltimoSegnale() == null) return false;
        return (this.getUltimoSegnale().compareTo(new Timestamp(System.currentTimeMillis() -
                java.time.Duration.ofMinutes(2).toMillis())) >= 0);
    }
}
