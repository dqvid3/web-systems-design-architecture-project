package it.unipa.wsda.gestione;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class ReportService {

    public void exportToExcel(HttpServletResponse response, List<ReportEntry> results) {
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=report.xlsx");

        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Report");
            Row headerRow = sheet.createRow(0);
            String[] headers = {"Ref Cartellone", "Nome", "Numero Visualizzazioni"};
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
            }

            int rowNum = 1;
            for (ReportEntry entry : results) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(entry.getRefCartellone());
                row.createCell(1).setCellValue(entry.getNome());
                row.createCell(2).setCellValue(entry.getNumeroVisualizzazioni());
            }

            workbook.write(response.getOutputStream());
        } catch (IOException e) {
            throw new RuntimeException("Errore durante l'esportazione in Excel", e);
        }
    }

    public void exportToPDF(HttpServletResponse response, List<ReportEntry> results) {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=report.pdf");

        try {
            Document document = new Document();
            PdfWriter.getInstance(document, response.getOutputStream());
            document.open();

            PdfPTable table = new PdfPTable(3);
            table.addCell("Ref Cartellone");
            table.addCell("Nome");
            table.addCell("Numero Visualizzazioni");

            for (ReportEntry entry : results) {
                table.addCell(String.valueOf(entry.getRefCartellone()));
                table.addCell(entry.getNome());
                table.addCell(String.valueOf(entry.getNumeroVisualizzazioni()));
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
