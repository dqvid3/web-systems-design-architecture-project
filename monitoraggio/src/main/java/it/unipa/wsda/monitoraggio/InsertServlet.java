package it.unipa.wsda.monitoraggio;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import java.util.LinkedList;

@WebServlet("/insertservlet")
public class InsertServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final List<String> allowedOrigins = new ArrayList<>(Arrays.asList("http://localhost:8080", "http://localhost:63342"));
        String origin = request.getHeader("Origin");
        if (allowedOrigins.contains(origin)) {
            response.addHeader("Access-Control-Allow-Origin", origin);
        }
        response.setContentType("application/json; charset=UTF-8");
        PrintWriter out = response.getWriter();
        final String query = "INSERT INTO visualizzazione(ref_impianto, cod_palinsesto, ref_cartellone, " +
                "durata_visualizzazione) VALUES (?, ?, (SELECT c.cod_cartellone FROM cartellone c WHERE c.nome = ?), ?)";
        List<String> parametri = new LinkedList<>();
        parametri.add(request.getParameter("ref_impianto"));
        parametri.add(request.getParameter("cod_palinsesto"));
        parametri.add(request.getParameter("nome_cartellone"));
        parametri.add(request.getParameter("durata_visualizzazione"));
        if (!parametri.contains(null)) {
            if (DBConnection.eseguiUpdate(query, parametri) != -1) {
                response.setStatus(HttpServletResponse.SC_OK);
                out.println("{\"status\": \"success\", \"message\": \"Operazione eseguita con successo!\"}");
            } else {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                out.println("{\"status\": \"error\", \"message\": \"Errore durante l'esecuzione della query!\"}");
            }
        } else {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.println("{\"status\": \"error\", \"message\": \"Errore numero di parametri!\"}");
        }
    }
}
