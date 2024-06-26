package it.unipa.wsda.gestione.services;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import it.unipa.wsda.gestione.dto.ReportDTO;
import it.unipa.wsda.gestione.entities.Cartellone;
import it.unipa.wsda.gestione.repositories.CartelloneRepository;
import it.unipa.wsda.gestione.repositories.VisualizzazioneRepository;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

@Service
public class ReportService {
    @Autowired
    private VisualizzazioneRepository visualizzazioneRepository;
    @Autowired
    private CartelloneRepository cartelloneRepository;

    public Iterable<Cartellone> getCartelloni() {
        return cartelloneRepository.findAll();
    }

    public List<ReportDTO> getReports(LocalDateTime startDate,
                                      LocalDateTime endDate,
                                      String cartelloneName,
                                      String operator,
                                      String sortOrder,
                                      int minViews,
                                      int limit) {
        List<Cartellone> cartelloni = cartelloneRepository.findByNomeLike(cartelloneName);
        int refCartellone = 0;
        if (cartelloni.size() == 1)
            refCartellone = cartelloni.get(0).getCodCartellone();
        List<List<Integer>> records = visualizzazioneRepository.findVisualizzazioniByPeriodo(startDate, endDate, refCartellone, operator, minViews, sortOrder, limit);
        List<ReportDTO> reports = new ArrayList<>(records.size());
        Map<Integer, String> cartelloneNames = new HashMap<>(cartelloni.size());
        for (Cartellone c : cartelloni) {
            cartelloneNames.put(c.getCodCartellone(), c.getNome());
        }
        for (List<Integer> record : records) {
            Integer codCartellone = record.get(0);
            String nome = cartelloneNames.get(record.get(0));
            reports.add(new ReportDTO(codCartellone, nome, record.get(1)));
        }
        if (reports == null) throw new IllegalStateException("Risultati non trovati");
        return reports;
    }

    public void exportToExcel(HttpServletResponse response, List<ReportDTO> results) {
        response.setContentType("application/octet-stream");
        LocalDateTime now = LocalDateTime.now();
        response.setHeader("Content-Disposition", "attachment; filename=report_" + now + ".xlsx");
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Report");
            Row headerRow = sheet.createRow(0);
            String[] headers = {"Ref Cartellone", "Nome", "Numero Visualizzazioni"};
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
            }
            int rowNum = 1;
            for (ReportDTO entry : results) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(entry.refCartellone()); // getRefCartellone());
                row.createCell(1).setCellValue(entry.nome()); // getNome());
                row.createCell(2).setCellValue(entry.risultato()); // getRisultato());
            }
            workbook.write(response.getOutputStream());
        } catch (IOException e) {
            throw new RuntimeException("Errore durante l'esportazione in Excel", e);
        }
    }

    public void exportToPDF(HttpServletResponse response, List<ReportDTO> results) {
        response.setContentType("application/pdf");
        LocalDateTime now = LocalDateTime.now();
        response.setHeader("Content-Disposition", "attachment; filename=report_" + now + ".pdf");
        try {
            Document document = new Document();
            PdfWriter.getInstance(document, response.getOutputStream());
            document.open();
            PdfPTable table = new PdfPTable(3);
            table.addCell("Ref Cartellone");
            table.addCell("Nome");
            table.addCell("Numero Visualizzazioni");
            for (ReportDTO entry : results) {
                table.addCell(String.valueOf(entry.refCartellone())); // getRefCartellone()));
                table.addCell(entry.nome()); // getNome());
                table.addCell(String.valueOf(entry.risultato())); // getRisultato()));
            }
            document.add(table);
            document.close();
        } catch (IOException e) {
            throw new RuntimeException("Errore durante l'esportazione in PDF", e);
        } catch (DocumentException e) {
            throw new RuntimeException(e);
        }
    }
}
