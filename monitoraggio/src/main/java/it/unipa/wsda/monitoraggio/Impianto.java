package it.unipa.wsda.monitoraggio;

import java.io.Serial;
import java.io.Serializable;

import java.math.BigDecimal;

import java.sql.Timestamp;

/**
 * Rappresenta un impianto di monitoraggio con attributi quali codice, descrizione,
 * posizione geografica e ultimo segnale ricevuto.
 * <p>
 * La classe implementa {@link Serializable} per permettere la serializzazione
 * degli oggetti di questa classe.
 * </p>
 * 
 * @version 1.0
 */
public class Impianto implements Serializable {

    /**
     * Numero di versione per il supporto alla serializzazione della classe.
     * <p>
     * Il campo serialVersionUID è utilizzato durante la serializzazione e deserializzazione degli oggetti
     * per garantire che la versione del compilatore utilizzata per la serializzazione corrisponda alla versione
     * del compilatore utilizzata per la deserializzazione.
     * </p>
     */
    @Serial
    private static final long serialVersionUID = 12345;

    /**
     * Il codice identificativo dell'impianto.
     */
    private int codImpianto;

    /**
     * La descrizione dell'impianto.
     */
    private String descrizione;

    /**
     * La latitudine della posizione dell'impianto.
     */
    private BigDecimal latitudine;

    /**
     * La longitudine della posizione dell'impianto.
     */
    private BigDecimal longitudine;

    /**
     * L'ultima data e ora in cui è stato ricevuto un segnale dall'impianto.
     */
    private Timestamp ultimoSegnale;

    /**
     * Lo stato dell'impianto (acceso/spento).
     */
    private boolean stato;

    /*
    public Impianto() {
        this(0, "", BigDecimal.ZERO, BigDecimal.ZERO, new Timestamp(System.currentTimeMillis()));
    }
    */

    /**
     * Costruisce un nuovo impianto con i parametri specificati.
     *
     * @param codImpianto il codice identificativo dell'impianto
     * @param descrizione la descrizione dell'impianto
     * @param latitudine la latitudine dell'impianto
     * @param longitudine la longitudine dell'impianto
     * @param ultimoSegnale l'ultima data e ora del segnale ricevuto dall'impianto
     * @param stato lo stato dell'impianto
     */
    public Impianto(int codImpianto, String descrizione, BigDecimal latitudine, BigDecimal longitudine, Timestamp ultimoSegnale, boolean stato) {
        this.codImpianto = codImpianto;
        this.descrizione = descrizione;
        this.latitudine = latitudine;
        this.longitudine = longitudine;
        this.ultimoSegnale = ultimoSegnale;
        this.stato = stato;
    }

    /**
     * Restituisce il codice dell'impianto.
     *
     * @return il codice dell'impianto
     */
    public int getCodImpianto() {
        return this.codImpianto;
    }

    /**
     * Imposta il codice dell'impianto.
     *
     * @param codImpianto il nuovo codice dell'impianto da impostare
     */
    public void setCodImpianto(int codImpianto) {
        this.codImpianto = codImpianto;
    }

    /**
     * Restituisce la descrizione dell'impianto.
     *
     * @return la descrizione dell'impianto
     */
    public String getDescrizione() {
        return this.descrizione;
    }

    /**
     * Imposta la descrizione dell'impianto.
     *
     * @param descrizione la nuova descrizione dell'impianto da impostare
     */
    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    /**
     * Restituisce la latitudine dell'impianto.
     *
     * @return la latitudine dell'impianto
     */
    public BigDecimal getLatitudine() {
        return this.latitudine;
    }

    /**
     * Imposta la latitudine dell'impianto.
     *
     * @param latitudine la nuova latitudine dell'impianto da impostare
     */
    public void setLatitudine(BigDecimal latitudine) {
        this.latitudine = latitudine;
    }

    /**
     * Restituisce la longitudine dell'impianto.
     *
     * @return la longitudine dell'impianto
     */
    public BigDecimal getLongitudine() {
        return this.longitudine;
    }

    /**
     * Imposta la longitudine dell'impianto.
     *
     * @param longitudine la nuova longitudine dell'impianto da impostare
     */
    public void setLongitudine(BigDecimal longitudine) {
        this.longitudine = longitudine;
    }

    /**
     * Restituisce l'ultimo segnale ricevuto dall'impianto.
     *
     * @return l'ultima data e ora del segnale ricevuto
     */
    public Timestamp getUltimoSegnale() {
        return this.ultimoSegnale;
    }

    /**
     * Imposta l'ultimo segnale ricevuto dall'impianto.
     *
     * @param ultimoSegnale la nuova ultima data e ora del segnale ricevuto da impostare
     */
    public void setUltimoSegnale(Timestamp ultimoSegnale) {
        this.ultimoSegnale = ultimoSegnale;
    }

    /**
     * Restituisce lo stato (acceso/spento) dell'impianto.
     *
     * @return {@code true} se l'impianto è acceso, {@code false} altrimenti
     */
    public boolean isAcceso() {
        return this.stato;
    }

    /**
     * Imposta lo stato dell'impianto.
     *
     * @param stato il nuovo stato dell'impianto (acceso/spento) da impostare
     */
    public void setStato(boolean stato) {
        this.stato = stato;
    }

    /**
     * Restituisce lo stato attuale dell'impianto come stringa descrittiva.
     *
     * @return una stringa che rappresenta lo stato attuale dell'impianto:
     *         "spento" (giallo), "attivo" (verde), "nonattivo" (rosso)
     */
    public String getStatoImpianto() {
        if (!isAcceso())
            return "spento"; // giallo
        if (isAttivo())
            return "attivo"; // verde
        // if (!isAttivo())
        return "nonattivo"; // rosso
    }

    /**
     * Restituisce una rappresentazione stringa HTML di questa istanza dell'impianto.
     * <p>
     * La rappresentazione include la latitudine, longitudine, descrizione, stato 
     * e l'ultimo segnale in formato leggibile.
     * </p>
     *
     * @return una stringa HTML che rappresenta questa istanza dell'impianto
     */
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

    /**
     * Restituisce lo stato (attivo/non attivo) dell'impianto.
     *
     * @return {@code true} se l'impianto è attivo, {@code false} altrimenti
     */
    public boolean isAttivo() {
        if (this.getUltimoSegnale() == null) return false;
        return (this.getUltimoSegnale().compareTo(new Timestamp(System.currentTimeMillis() -
                java.time.Duration.ofMinutes(2).toMillis())) >= 0);
    }
}
