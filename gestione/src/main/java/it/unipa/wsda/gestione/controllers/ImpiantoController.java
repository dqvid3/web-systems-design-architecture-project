package it.unipa.wsda.gestione.controllers;

import it.unipa.wsda.gestione.services.ImpiantoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ImpiantoController {
    @Autowired
    private ImpiantoService impiantoService;

    @GetMapping("/impianto")
    public String showImpianto(@RequestParam("id") int codImpianto, Model model) {
        try {
            String path = impiantoService.getPathPalinsesto(codImpianto);
            model.addAttribute("path", path);
            model.addAttribute("codImpianto", codImpianto);
            return "led_wall";
        } catch (IllegalStateException e) {
            model.addAttribute("message", e.getMessage());
            return "error";
        }
    }
}

