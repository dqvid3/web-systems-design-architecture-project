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

@WebServlet("/insertservlet")
public class InsertServlet extends HttpServlet {
    @Serial
    private static final long serialVersionUID = 1234569L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final List<String> allowedOrigins = new ArrayList<>(Arrays.asList("http://localhost:8080", "http://localhost:63342"));
        String origin = request.getHeader("Origin");
        if (allowedOrigins.contains(origin)) {
            response.addHeader("Access-Control-Allow-Origin", origin);
        }
        response.setContentType("application/json; charset=UTF-8");
        PrintWriter out = response.getWriter();
        final String query = "INSERT INTO visualizzazione(ref_impianto, ref_palinsesto, ref_cartellone, " +
                "durata_visualizzazione) VALUES (?, ?, (SELECT c.cod_cartellone FROM cartellone c WHERE c.nome = ?), ?)";
        List<String> parametri = new LinkedList<>();
        parametri.add(request.getParameter("ref_impianto"));
        parametri.add(request.getParameter("ref_palinsesto"));
        parametri.add(request.getParameter("nome_cartellone"));
        parametri.add(request.getParameter("durata_visualizzazione"));
        try {
            DBConnection.eseguiUpdate(query, parametri);
            response.setStatus(HttpServletResponse.SC_OK);
            out.println("{\"status\": \"success\", \"message\": \"Operazione UPDATE eseguita con successo!\"}");
        } catch (MonitoraggioException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.println(e);
        }
    }
}
