package it.unipa.wsda.gestione;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReportService {

    @Autowired
    private ReportRepository reportRepository;

    @Autowired
    private EntityManager entityManager;

    public List<ReportEntry> getReport(String dateRange, String cartelloneName, String operator, String sortOrder, int minViews) {
        // Assume dateRange is a string like "today", "lastMonth", etc., for simplicity
        LocalDateTime startDate;
        LocalDateTime endDate = LocalDateTime.now();

        switch (dateRange) {
            case "today":
                startDate = endDate.toLocalDate().atStartOfDay();
                break;
            case "lastWeek":
                startDate = endDate.minusWeeks(1);
                break;
            case "lastMonth":
                startDate = endDate.minusMonths(1);
                break;
            default:
                throw new IllegalArgumentException("Invalid date range: " + dateRange);
        }

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<ReportEntry> query = cb.createQuery(ReportEntry.class);
        Root<Visualizzazione> visualizzazione = query.from(Visualizzazione.class);
        Join<Visualizzazione, Cartellone> cartellone = visualizzazione.join("cartellone");

        query.select(cb.construct(ReportEntry.class,
                visualizzazione.get("refCartellone"),
                cartellone.get("nome"),
                cb.count(visualizzazione.get("id"))));

        Predicate datePredicate = cb.between(visualizzazione.get("ultimoSegnale"), startDate, endDate);
        Predicate namePredicate = cb.like(cartellone.get("nome"), "%" + cartelloneName + "%");
        query.where(cb.and(datePredicate, namePredicate));
        query.groupBy(visualizzazione.get("refCartellone"));
        query.having(cb.greaterThan(cb.count(visualizzazione.get("id")), minViews));

        if ("asc".equalsIgnoreCase(sortOrder)) {
            query.orderBy(cb.asc(cb.count(visualizzazione.get("id"))));
        } else {
            query.orderBy(cb.desc(cb.count(visualizzazione.get("id"))));
        }

        TypedQuery<ReportEntry> typedQuery = entityManager.createQuery(query);
        return typedQuery.getResultList();
    }

    public List<String> getAllCartelloni() {
        return reportRepository.findAllCartelloni();
    }

    public void exportToExcel(HttpServletResponse response, List<ReportEntry> results) {
        // Implementazione dell'esportazione in Excel
        System.out.println("Excel");
/*
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
        }*/
    }

    public void exportToPDF(HttpServletResponse response, List<ReportEntry> results) {
        // Implementazione dell'esportazione in PDF
        System.out.println("PDF");/*
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=report.pdf");

        try (PdfWriter writer = new PdfWriter(response.getOutputStream());
             PdfDocument pdf = new PdfDocument(writer);
             Document document = new Document(pdf)) {

            Table table = new Table(new float[]{1, 1, 1});
            table.setWidth(UnitValue.createPercentValue(100));
            table.addHeaderCell("Ref Cartellone");
            table.addHeaderCell("Nome");
            table.addHeaderCell("Numero Visualizzazioni");

            for (ReportEntry entry : results) {
                table.addCell(entry.getRefCartellone().toString());
                table.addCell(entry.getNome());
                table.addCell(entry.getNumeroVisualizzazioni().toString());
            }

            document.add(table);
        } catch (IOException e) {
            throw new RuntimeException("Errore durante l'esportazione in PDF", e);
        }*/
    }
}
