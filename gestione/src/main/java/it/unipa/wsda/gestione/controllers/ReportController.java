package it.unipa.wsda.gestione.controllers;

import it.unipa.wsda.gestione.dto.ReportDTO;
import it.unipa.wsda.gestione.entities.Cartellone;
import it.unipa.wsda.gestione.services.ReportService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.time.LocalDateTime;
import java.util.List;

@Controller
public class ReportController {

    @Autowired
    private ReportService reportService;

    @GetMapping("/reportistica")
    public String showReportForm(HttpSession session, Model model) {
        Iterable<Cartellone> cartelloni;
        try {
            cartelloni = reportService.getCartelloni();
        } catch (IllegalStateException e) {
            model.addAttribute("errore", e.getMessage());
            return "error";
        }
        session.setAttribute("cartelloni", cartelloni);
        // Genera il report con parametri predefiniti
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startDate = now.withHour(0).withMinute(0).withSecond(0).withNano(0);
        LocalDateTime endDate = now.withHour(23).withMinute(59).withSecond(59).withNano(0);
        String operator = "COUNT";
        List<ReportDTO> results = reportService.getReports(startDate, endDate, "%", operator, "ASC", 0, 1000);
        session.setAttribute("results", results);
        model.addAttribute("results", results);
        model.addAttribute("operator", operator);
        model.addAttribute("cartelloni", cartelloni);
        model.addAttribute("dateRange", "Oggi");
        return "reportistica";
    }

    @PostMapping("/reportistica")
    public String generateReport(@RequestParam LocalDateTime startDate,
                                 @RequestParam LocalDateTime endDate,
                                 @RequestParam String cartelloneName,
                                 @RequestParam String operator,
                                 @RequestParam String sortOrder,
                                 @RequestParam int minViews,
                                 @RequestParam int limit,
                                 @RequestParam String dateRange,
                                 HttpSession session,
                                 Model model) {
        List<ReportDTO> results = reportService.getReports(startDate, endDate, cartelloneName, operator, sortOrder, minViews, limit);
        session.setAttribute("results", results);
        model.addAttribute("results", results);
        model.addAttribute("operator", operator);
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        model.addAttribute("cartelloneName", cartelloneName);
        model.addAttribute("sortOrder", sortOrder);
        model.addAttribute("minViews", minViews);
        model.addAttribute("limit", limit);
        model.addAttribute("dateRange", dateRange);
        model.addAttribute("cartelloni", session.getAttribute("cartelloni"));
        return "reportistica";
    }

    @GetMapping("/reportistica/esporta")
    public void exportReport(@RequestParam String formato, HttpServletResponse response, HttpSession session) {
        List<ReportDTO> results = (List<ReportDTO>) session.getAttribute("results");
        if (results != null) {
            try {
                if (formato.equalsIgnoreCase("pdf")) {
                    reportService.exportToPDF(response, results);
                } else if (formato.equalsIgnoreCase("xlsx")) {
                    reportService.exportToExcel(response, results);
                } else {
                    throw new IllegalArgumentException("Formato non supportato: " + formato);
                }
            } catch (RuntimeException e) {
                throw new RuntimeException(e.getMessage());
            }
        } else
            throw new IllegalArgumentException("Risultati non validi");
    }
}
