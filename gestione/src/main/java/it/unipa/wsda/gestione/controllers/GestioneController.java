package it.unipa.wsda.gestione.controllers;

import it.unipa.wsda.gestione.entities.Impianto;
import it.unipa.wsda.gestione.entities.Palinsesto;
import it.unipa.wsda.gestione.repositories.ImpiantoRepository;
import it.unipa.wsda.gestione.services.GestioneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;

@Controller
public class GestioneController {
    @Autowired
    private GestioneService gestioneService;
    @Autowired
    private ImpiantoRepository impiantoRepository;

    @GetMapping("/gestione")
    public String showImpianti(Model model) {
        model.addAttribute("impianti",  gestioneService.getImpianti());
        return "gestione";
    }
    @PostMapping("/gestione")
    public String modificaImpianto(Model model, @RequestParam Integer id){
        model.addAttribute("palinsesti", gestioneService.getPalinsesti());
        return "gestione";
    }

    @GetMapping("/aggiungi_impianto")
    public String showAggiungiImpianto(Model model){
        model.addAttribute("palinsesti", gestioneService.getPalinsesti());
        return "aggiungi_impianto";
    }

    @PostMapping("/aggiungi_impianto")
    public @ResponseBody String aggiungiImpianto(@RequestParam String descrizione, @RequestParam BigDecimal latitudine, @RequestParam BigDecimal longitudine, @RequestParam Palinsesto palinsesto){
        Impianto n = new Impianto();
        n.setDescrizione(descrizione);
        n.setLatitudine(latitudine);
        n.setLongitudine(longitudine);
        n.setPalinsesto(palinsesto);
        impiantoRepository.save(n);
        return "Saved";
    }
}
