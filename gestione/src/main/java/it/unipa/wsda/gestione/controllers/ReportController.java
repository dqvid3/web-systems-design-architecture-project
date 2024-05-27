package it.unipa.wsda.gestione.controllers;

import it.unipa.wsda.gestione.entities.Cartellone;
import it.unipa.wsda.gestione.repositories.ReportRepository;
import it.unipa.wsda.gestione.services.ReportService;
import it.unipa.wsda.gestione.entities.Report;
import it.unipa.wsda.gestione.repositories.CartelloneRepository;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
public class ReportController {

    @Autowired
    private ReportService reportService;
    @Autowired
    private ReportRepository reportRepository;
    @Autowired
    private CartelloneRepository cartelloneRepository;

    private List<Report> results;
    private Iterable<Cartellone> cartelloni;
    private int limit = 1000;
    private int minViews = 0;

    @GetMapping("/reportistica")
    public String showReportForm(Model model) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startOfDay = now.with(LocalTime.MIN);
        LocalDateTime endOfDay = now;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        cartelloni = cartelloneRepository.findAll();
        model.addAttribute("cartelloni", cartelloni);
        model.addAttribute("limit", limit);
        model.addAttribute("minViews", minViews);
        model.addAttribute("startDate", startOfDay.format(formatter));
        model.addAttribute("endDate", endOfDay.format(formatter));
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
                                 Model model) {
        results = reportRepository.findCustomReport(startDate, endDate, cartelloneName, operator, minViews, sortOrder, limit);
        model.addAttribute("results", results);
        model.addAttribute("operator", operator);
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        model.addAttribute("cartelloneName", cartelloneName);
        model.addAttribute("sortOrder", sortOrder);
        model.addAttribute("minViews", minViews);
        model.addAttribute("limit", limit);
        model.addAttribute("cartelloni", cartelloni);
        return "reportistica";
    }

    @GetMapping("/reportistica/esporta")
    public void exportReport(@RequestParam String formato, HttpServletResponse response) {
        if (formato.equalsIgnoreCase("pdf")) {
            reportService.exportToPDF(response, results);
        } else if (formato.equalsIgnoreCase("xlsx")) {
            reportService.exportToExcel(response, results);
        } else {
            throw new IllegalArgumentException("Formato non supportato: " + formato);
        }
    }
}
