package it.unipa.wsda.gestione;

import it.unipa.wsda.gestione.repositories.CartelloneRepository;
import jakarta.servlet.http.HttpServletResponse;
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
    @Autowired
    private ReportRepository reportRepository;
    @Autowired
    private CartelloneRepository cartelloneRepository;

    private List<ReportEntry> results;

    @GetMapping("/reportistica")
    public String showReportForm(Model model) {
        model.addAttribute("cartelloni", cartelloneRepository.findAll());
        return "reportistica";
    }

    @PostMapping("/reportistica")
    public String generateReport(@RequestParam LocalDateTime startDate,
                                 @RequestParam LocalDateTime endDate,
                                 @RequestParam String cartelloneName,
                                 @RequestParam String operator,
                                 @RequestParam String sortOrder,
                                 @RequestParam int minViews,
                                 Model model) {
        results = reportRepository.findCustomReport(startDate, endDate, cartelloneName, minViews);
        model.addAttribute("results", results);
        return "reportistica";
    }

    @GetMapping("/reportistica/esporta")
    public void exportReport(@RequestParam String formato, HttpServletResponse response) {
        System.out.println("ciao");
        if (formato.equalsIgnoreCase("pdf")) {
            reportService.exportToPDF(response, results);
        } else if (formato.equalsIgnoreCase("xlsx")) {
            reportService.exportToExcel(response, results);
        } else {
            throw new IllegalArgumentException("Formato non supportato: " + formato);
        }
    }
}
