package it.unipa.wsda.gestione;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ReportController {

    @Autowired
    private ReportService reportService;

    @GetMapping("/reportistica")
    public String showReportForm(Model model) {
        model.addAttribute("cartelloni", reportService.getAllCartelloni());
        return "reportistica";
    }

    @PostMapping("/reportistica")
    public String generateReport(@RequestParam String dateRange,
                                 @RequestParam String cartelloneName,
                                 @RequestParam String operator,
                                 @RequestParam String sortOrder,
                                 @RequestParam int minViews,
                                 Model model) {
        List<ReportEntry> results = reportService.getReport(dateRange, cartelloneName, operator, sortOrder, minViews);
        model.addAttribute("results", results);
        return "reportistica";
    }

    @GetMapping("/reportistica/esporta")
    public void exportReport(@RequestParam String dateRange,
                             @RequestParam String cartelloneName,
                             @RequestParam String operator,
                             @RequestParam String sortOrder,
                             @RequestParam int minViews,
                             @RequestParam String formato, HttpServletResponse response) {
        List<ReportEntry> results = reportService.getReport(dateRange, cartelloneName, operator, sortOrder, minViews);
        if (formato.equalsIgnoreCase("pdf")) {
            reportService.exportToPDF(response, results);
        } else if (formato.equalsIgnoreCase("xlsx")) {
            reportService.exportToExcel(response, results);
        } else {
            throw new IllegalArgumentException("Formato non supportato: " + formato);
        }
    }
}
