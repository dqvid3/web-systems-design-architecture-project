package it.unipa.wsda.monitoraggio;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serial;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Servlet che gestisce l'inserimento dei dati nella base di dati tramite una richiesta POST.
 * <p>
 * Questa servlet si occupa di inserire i dati di visualizzazione nel database,
 * verificando l'origine della richiesta e rispondendo con un messaggio di successo o errore.
 * </p>
 * 
 * @version 1.0
 */
@WebServlet("/insertservlet")
public class InsertServlet extends HttpServlet {

    /**
     * Numero di versione per il supporto alla serializzazione della classe.
     * <p>
     * Il campo serialVersionUID è utilizzato durante la serializzazione e deserializzazione degli oggetti
     * per garantire che la versione del compilatore utilizzata per la serializzazione corrisponda alla versione
     * del compilatore utilizzata per la deserializzazione.
     * </p>
     */
    @Serial
    private static final long serialVersionUID = 1234569L;

    /**
     * Gestisce le richieste HTTP POST per l'inserimento dei dati.
     * <p>
     * La richiesta deve includere i parametri necessari per l'inserimento dei dati
     * nella tabella di visualizzazione del database. La servlet verifica l'origine
     * della richiesta e risponde con un JSON che indica il successo o il fallimento
     * dell'operazione.
     * </p>
     *
     * @param request  l'oggetto {@link HttpServletRequest} che contiene la richiesta del client
     * @param response l'oggetto {@link HttpServletResponse} che contiene la risposta della servlet
     * @throws IOException se si verifica un errore di input/output durante la gestione della richiesta
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Lista delle origini consentite per le richieste
        final List<String> allowedOrigins = new ArrayList<>(Arrays.asList("http://localhost:8080", "http://localhost:63342"));
        String origin = request.getHeader("Origin");

        // Verifica se l'origine della richiesta è consentita
        if (allowedOrigins.contains(origin)) {
            response.addHeader("Access-Control-Allow-Origin", origin);
        }

        // Imposta il tipo di contenuto della risposta come JSON con codifica UTF-8
        response.setContentType("application/json; charset=UTF-8");
        PrintWriter out = response.getWriter();

        // Query SQL per inserire i dati nella tabella di visualizzazione
        final String query = "INSERT INTO visualizzazione(ref_impianto, ref_cartellone, " +
                "durata_visualizzazione) VALUES (?, (SELECT c.cod_cartellone FROM cartellone c WHERE c.nome = ?), ?);";

        // Raccolta dei parametri dalla richiesta
        List<String> parametri = new LinkedList<>();
        parametri.add(request.getParameter("ref_impianto"));
        parametri.add(request.getParameter("nome_cartellone"));
        parametri.add(request.getParameter("durata_visualizzazione"));

        // Esecuzione della query e gestione delle eccezioni
        try {
            DBConnection.eseguiUpdate(query, parametri);
            response.setStatus(HttpServletResponse.SC_OK);
            out.println("{\"status\": \"success\", \"message\": \"Operazione INSERT eseguita con successo!\"}");
        } catch (MonitoraggioException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.println(e);
        }
    }
}
