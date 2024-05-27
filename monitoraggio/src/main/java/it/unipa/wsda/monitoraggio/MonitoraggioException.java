package it.unipa.wsda.monitoraggio;

import java.io.Serial;

/**
 * Eccezione personalizzata per il monitoraggio.
 * <p>
 * Questa classe rappresenta un'eccezione specifica per situazioni di errore 
 * durante il monitoraggio all'interno dell'applicazione. Estende {@link Exception}
 * e fornisce un metodo {@code toString()} per restituire un messaggio di errore 
 * in formato JSON.
 * </p>
 * 
 * @version 1.0
 */
public class MonitoraggioException extends Exception {

    /**
     * Numero di versione per il supporto alla serializzazione della classe.
     * <p>
     * Il campo serialVersionUID è utilizzato durante la serializzazione e deserializzazione degli oggetti
     * per garantire che la versione del compilatore utilizzata per la serializzazione corrisponda alla versione
     * del compilatore utilizzata per la deserializzazione.
     * </p>
     */
    @Serial
    private static final long serialVersionUID = 1234568L;

    /**
     * Costruisce una nuova eccezione di monitoraggio con il messaggio specificato.
     *
     * @param messaggio il messaggio di dettaglio. Il messaggio di dettaglio è 
     * salvato per il successivo recupero tramite il metodo {@link #getMessage()}.
     */
    public MonitoraggioException(String messaggio) {
        super(messaggio);
    }

    /**
     * Restituisce una rappresentazione stringa di questa eccezione.
     * <p>
     * La rappresentazione stringa è formattata come un oggetto JSON con lo 
     * stato e il messaggio di errore.
     * </p>
     *
     * @return una stringa che rappresenta questa eccezione in formato JSON.
     */
    @Override
    public String toString() {
        return "{\"status\": \"error\", \"message\": \"" + this.getMessage() + "\"}";
    }
}
