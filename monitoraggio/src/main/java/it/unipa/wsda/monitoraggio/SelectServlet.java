package it.unipa.wsda.monitoraggio;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@WebServlet("/selectservlet")
public class SelectServlet extends HttpServlet {
    @Serial
    private static final long serialVersionUID = 1234567L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json; charset=UTF-8");
        PrintWriter out = response.getWriter();
        final String query = "SELECT i.cod_impianto, i.descrizione, i.latitudine, i.longitudine, i.stato, " +
                "MAX(v.ultimo_segnale) as ultimo_segnale " +
                "FROM impianto i JOIN visualizzazione v ON i.cod_impianto = v.ref_impianto " +
                "GROUP BY i.cod_impianto;";
        try {
            List<Impianto> impianti = DBConnection.eseguiQuery(query);
            int[] totImpianti = scriviImpiantiSuFile(impianti);
            response.setStatus(HttpServletResponse.SC_OK);
            out.println("{\"status\": \"success\", \"message\": \"Operazione eseguita con successo!\"," +
                    "\"attivi\": " + totImpianti[0] + ", \"nonAttivi\": " + totImpianti[1]);
            BigDecimal[] ris = calculateCenter(impianti);
            out.println(", \"latCentro\":" + ris[0] + ", \"lonCentro\":" + ris[1] + "}");
        } catch (MonitoraggioException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.println(e);
        }
    }

    private int[] scriviImpiantiSuFile(List<Impianto> impianti) throws MonitoraggioException {
        final String titolo = "lat\tlon\ttitle\tdescription\ticon\ticonSize\ticonOffset\n";
        StringBuilder attivi = new StringBuilder(titolo);
        StringBuilder nonAttivi = new StringBuilder(titolo);
        for (Impianto impianto : impianti) {
            if (impianto.isAttivo()) {
                attivi.append(impianto);
            } else {
                nonAttivi.append(impianto);
            }
        }
        final String path = getServletContext().getRealPath("/") + "resources/static/mappa/";
        final String pathAttivi = path + "attivi.txt", pathNonAttivi = path + "nonAttivi.txt";
        try {
            scriviStringaSuFile(pathAttivi, attivi.toString());
            scriviStringaSuFile(pathNonAttivi, nonAttivi.toString());
            return new int[]{contaCaratteri(attivi.toString(), "\n") - 1, contaCaratteri(nonAttivi.toString(), "\n") - 1};
        } catch (MonitoraggioException e) {
            throw new MonitoraggioException("scriviImpiantiSuFile()");
        }
    }

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

    private int contaCaratteri(String stringa, String carattere) {
        return (stringa.length() - stringa.replace(carattere, "").length());
    }

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
