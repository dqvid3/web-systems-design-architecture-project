package it.unipa.wsda.monitoraggio;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.math.BigDecimal;
import java.math.RoundingMode;

import java.util.List;

@WebServlet("/selectservlet")
public class SelectServlet extends HttpServlet {
    private static final long serialVersionUID = 1234567L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json; charset=UTF-8");
        PrintWriter out = resp.getWriter();
        final String query = "SELECT i.cod_impianto, i.descrizione, i.latitudine, i.longitudine, MAX(v.ultimo_segnale)" +
                " AS ultimo_segnale FROM visualizzazione v, impianto i WHERE v.ref_impianto = i.cod_impianto GROUP BY v.ref_impianto;";
        List<Impianto> impianti = DBConnection.eseguiQuery(query);
        if (impianti != null) {
            int[] totImpianti = scriviImpiantiSuFile(impianti);
            if (totImpianti.length == 2 && totImpianti[0] != -1 && totImpianti[1] != -1) {
                resp.setStatus(HttpServletResponse.SC_OK);
                out.println("{\"status\": \"success\", \"message\": \"Operazione eseguita con successo!\"," +
                        "\"attivi\": " + totImpianti[0] + ", \"nonAttivi\": " + totImpianti[1]);
                BigDecimal[] ris = calculateCenter(impianti);
                assert ris != null;
                out.println(", \"latCentro\":" + ris[0] + ", \"lonCentro\":" + ris[1] + "}");
            } else {
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                out.println("{\"status\": \"error\", \"message\": \"Errore durante la scrittura su file!\"}");
            }
        } else {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.println("{\"status\": \"error\", \"message\": \"Errore durante l'esecuzione della query!\"}");
        }
    }

    private int[] scriviImpiantiSuFile(List<Impianto> impianti) {
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
        String path = getServletContext().getRealPath("/") + "resources/static/mappa/";
        String pathAttivi = path + "attivi.txt", pathNonAttivi = path + "nonAttivi.txt";
        return (scriviStringaSuFile(pathAttivi, attivi.toString()) && scriviStringaSuFile(pathNonAttivi, nonAttivi.toString()) ?
                new int[]{contaCaratteri(attivi.toString(), "\n") - 1, contaCaratteri(nonAttivi.toString(), "\n") - 1} : new int[]{-1, -1});
    }

    private boolean scriviStringaSuFile(String path, String testo) {
        try {
            FileWriter fileWriter = new FileWriter(path);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(testo);
            bufferedWriter.close();
            System.out.println("Stringa scritta sul file con successo.");
            return true;
        } catch (IOException e) {
            System.out.printf("Errore durante la scrittura sul file: %s", e.getMessage());
            return false;
        }
    }

    private int contaCaratteri(String stringa, String carattere) {
        return (stringa.length() - stringa.replace(carattere, "").length());
    }

    private BigDecimal[] calculateCenter(List<Impianto> impianti) {
        BigDecimal totalLatitude = BigDecimal.ZERO;
        BigDecimal totalLongitude = BigDecimal.ZERO;
        int count = 0;
        for (Impianto impianto : impianti) {
            totalLatitude = totalLatitude.add(impianto.getLatitudine());
            totalLongitude = totalLongitude.add(impianto.getLongitudine());
            count++;
        }
        if (count > 0) {
            BigDecimal countBD = new BigDecimal(count);
            BigDecimal centerLatitude = totalLatitude.divide(countBD, RoundingMode.HALF_UP);
            BigDecimal centerLongitude = totalLongitude.divide(countBD, RoundingMode.HALF_UP);
            return new BigDecimal[]{centerLatitude, centerLongitude};
        }
        return null;
    }
}
