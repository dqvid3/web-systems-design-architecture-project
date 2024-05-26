package it.unipa.wsda.gestione.controllers;

import it.unipa.wsda.gestione.entities.Impianto;
import it.unipa.wsda.gestione.entities.Palinsesto;
import it.unipa.wsda.gestione.repositories.ImpiantoRepository;
import it.unipa.wsda.gestione.repositories.PalinsestoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MainController {
    @Autowired
    private PalinsestoRepository palinsestoRepository;
    @Autowired
    private ImpiantoRepository impiantoRepository;

    @GetMapping("/impianto")
    public String impianto(@RequestParam("id") int codImpianto, Model model) {
        Impianto impianto = impiantoRepository.findByCodImpianto(codImpianto);
        if (!impianto.isAttivo())
            return "error";
        int codPalinsesto = impianto.getRefPalinsesto();
        Palinsesto palinsesto = palinsestoRepository.findByCodPalinsesto(codPalinsesto);
        String path = palinsesto.getPathPalinsesto();
        model.addAttribute("path", path);
        return "led_wall";
    }
}
