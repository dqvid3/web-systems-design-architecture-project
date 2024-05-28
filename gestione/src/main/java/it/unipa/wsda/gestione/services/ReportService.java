package it.unipa.wsda.gestione.services;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import it.unipa.wsda.gestione.entities.Cartellone;
import it.unipa.wsda.gestione.entities.Report;
import it.unipa.wsda.gestione.repositories.CartelloneRepository;
import it.unipa.wsda.gestione.repositories.ReportRepository;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReportService {

    @Autowired
    private ReportRepository reportRepository;
    @Autowired
    private CartelloneRepository cartelloneRepository;

    public Iterable<Cartellone> getCartelloni() {
        return cartelloneRepository.findAll();
    }

    public List<Report> getReports(LocalDateTime startDate,
                                   LocalDateTime endDate,
                                   String cartelloneName,
                                   String operator,
                                   String sortOrder,
                                   int minViews,
                                   int limit) {
        return reportRepository.findCustomReport(startDate, endDate, cartelloneName, operator, minViews, sortOrder, limit);
    }

    public void exportToExcel(HttpServletResponse response, List<Report> results) {
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
            for (Report entry : results) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(entry.getRefCartellone());
                row.createCell(1).setCellValue(entry.getNome());
                row.createCell(2).setCellValue(entry.getRisultato());
            }

            workbook.write(response.getOutputStream());
        } catch (IOException e) {
            throw new RuntimeException("Errore durante l'esportazione in Excel", e);
        }
    }

    public void exportToPDF(HttpServletResponse response, List<Report> results) {
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

            for (Report entry : results) {
                table.addCell(String.valueOf(entry.getRefCartellone()));
                table.addCell(entry.getNome());
                table.addCell(String.valueOf(entry.getRisultato()));
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
