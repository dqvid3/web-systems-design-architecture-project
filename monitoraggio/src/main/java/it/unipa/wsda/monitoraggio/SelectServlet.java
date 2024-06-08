package it.unipa.wsda.monitoraggio;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serial;

import java.math.BigDecimal;
import java.math.RoundingMode;

import java.util.List;

/**
 * Servlet che gestisce il recupero dei dati e il monitoraggio degli impianti tramite una richiesta GET.
 * <p>
 * Questa servlet si occupa di recuperare i dati degli impianti dal database, scriverli su file,
 * calcolare il centro geografico degli impianti e restituire un JSON con i risultati.
 * </p>
 * 
 * @version 1.0
 */
@WebServlet("/selectservlet")
public class SelectServlet extends HttpServlet {

    /**
     * Numero di versione per il supporto alla serializzazione della classe.
     * <p>
     * Il campo serialVersionUID è utilizzato durante la serializzazione e deserializzazione degli oggetti
     * per garantire che la versione del compilatore utilizzata per la serializzazione corrisponda alla versione
     * del compilatore utilizzata per la deserializzazione.
     * </p>
     */
    @Serial
    private static final long serialVersionUID = 1234567L;

    /**
     * Gestisce le richieste HTTP GET per il recupero dei dati.
     * <p>
     * La richiesta recupera i dati degli impianti dal database, li scrive su file,
     * calcola il centro geografico degli impianti e risponde con un JSON che indica
     * il successo o il fallimento dell'operazione.
     * </p>
     *
     * @param request  l'oggetto {@link HttpServletRequest} che contiene la richiesta del client
     * @param response l'oggetto {@link HttpServletResponse} che contiene la risposta della servlet
     * @throws IOException se si verifica un errore di input/output durante la gestione della richiesta
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Imposta il tipo di contenuto della risposta come JSON con codifica UTF-8
        response.setContentType("application/json; charset=UTF-8");
        PrintWriter out = response.getWriter();

        // Query SQL per selezionare i dati delle tabelle impianto e visualizzazione
        final String query = "SELECT i.cod_impianto, i.descrizione, i.latitudine, i.longitudine, i.stato, " +
                "MAX(v.ultimo_segnale) as ultimo_segnale " +
                "FROM impianto i LEFT JOIN visualizzazione v ON i.cod_impianto = v.ref_impianto " +
                "GROUP BY i.cod_impianto;";

        // Esecuzione della query e gestione delle eccezioni
        try {
            List<Impianto> impianti = DBConnection.eseguiQuery(query);
            int[] totImpianti = scriviImpiantiSuFile(impianti);
            response.setStatus(HttpServletResponse.SC_OK);
            out.println("{\"status\": \"success\", \"message\": \"Operazione SELECT eseguita con successo!\"," +
                    "\"attivi\": " + totImpianti[0] + ", \"nonAttivi\": " + totImpianti[1]
                    + ", \"spenti\": " + totImpianti[2]);
            BigDecimal[] ris = calculateCenter(impianti);
            out.println(", \"latCentro\":" + ris[0] + ", \"lonCentro\":" + ris[1] + "}");
        } catch (MonitoraggioException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.println(e);
        }
    }

    /**
     * Scrive le informazioni sugli impianti attivi, non attivi e spenti su file.
     *
     * @param impianti la lista degli impianti da scrivere su file
     * @return un array di interi contenente il numero di impianti attivi, non attivi e spenti
     * @throws MonitoraggioException se si verifica un errore durante la scrittura su file
     */
    private int[] scriviImpiantiSuFile(List<Impianto> impianti) throws MonitoraggioException {
        final String titolo = "lat\tlon\ttitle\tdescription\ticon\ticonSize\ticonOffset\n";
        StringBuilder attivi = new StringBuilder(titolo);
        StringBuilder nonAttivi = new StringBuilder(titolo);
        StringBuilder spenti = new StringBuilder(titolo);
        for (Impianto impianto : impianti) {
            if (impianto.getStatoImpianto().equals("attivo")) attivi.append(impianto);
            else if (impianto.getStatoImpianto().equals("nonattivo")) nonAttivi.append(impianto);
            else if (impianto.getStatoImpianto().equals("spento")) spenti.append(impianto);
        }
        final String path = getServletContext().getRealPath("/") + "resources/static/mappa/";
        try {
            scriviStringaSuFile(path + "attivi.txt", attivi.toString());
            scriviStringaSuFile(path + "nonAttivi.txt", nonAttivi.toString());
            scriviStringaSuFile(path + "spenti.txt", spenti.toString());
            return new int[]{contaCaratteri(attivi.toString(), "\n") - 1, contaCaratteri(nonAttivi.toString(), "\n") - 1,
                    contaCaratteri(spenti.toString(), "\n") - 1};
        } catch (MonitoraggioException e) {
            throw new MonitoraggioException("scriviImpiantiSuFile()");
        }
    }

    /**
     * Scrive una stringa su un file specificato.
     *
     * @param path  il percorso del file su cui scrivere
     * @param testo la stringa da scrivere sul file
     * @throws MonitoraggioException se si verifica un errore durante la scrittura su file
     */
    private void scriviStringaSuFile(String path, String testo) throws MonitoraggioException {
        try {
            FileWriter fileWriter = new FileWriter(path);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(testo);
            bufferedWriter.close();
        } catch (IOException e) {
            throw new MonitoraggioException("scriviStringaSuFile()");
        }
    }

    /**
     * Conta il numero di occorrenze di un carattere specificato in una stringa.
     *
     * @param stringa   la stringa in cui contare le occorrenze
     * @param carattere il carattere da contare
     * @return il numero di occorrenze del carattere nella stringa
     */
    private int contaCaratteri(String stringa, String carattere) {
        return (stringa.length() - stringa.replace(carattere, "").length());
    }

    /**
     * Calcola il centro geografico degli impianti.
     *
     * @param impianti la lista degli impianti
     * @return un array di BigDecimal contenente le coordinate del centro [latitudine, longitudine]
     * @throws MonitoraggioException se si verifica un errore durante il calcolo
     */
    private BigDecimal[] calculateCenter(List<Impianto> impianti) throws MonitoraggioException {
        BigDecimal totLatitude = BigDecimal.ZERO;
        BigDecimal totLongitude = BigDecimal.ZERO;
        int totImpianti = 0;
        for (Impianto impianto : impianti) {
            totLatitude = totLatitude.add(impianto.getLatitudine());
            totLongitude = totLongitude.add(impianto.getLongitudine());
            totImpianti++;
        }
        if (totImpianti > 0) {
            BigDecimal totImpiantiBD = new BigDecimal(totImpianti);
            BigDecimal centerLatitude = totLatitude.divide(totImpiantiBD, RoundingMode.HALF_UP);
            BigDecimal centerLongitude = totLongitude.divide(totImpiantiBD, RoundingMode.HALF_UP);
            return new BigDecimal[]{centerLatitude, centerLongitude};
        } else {
            throw new MonitoraggioException("calculateCenter()");
        }
    }
}
