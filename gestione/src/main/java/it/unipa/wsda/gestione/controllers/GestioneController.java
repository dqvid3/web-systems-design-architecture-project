package it.unipa.wsda.gestione.controllers;

import it.unipa.wsda.gestione.services.GestioneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class GestioneController {
    @Autowired
    private GestioneService gestioneService;

    @GetMapping("/gestione")
    public String showImpianti(Model model) {
        model.addAttribute("impianti",  gestioneService.getImpianti());
        return "gestione";
    }
    @PostMapping("/gestione")
    public String modificaImpianto(Model model, @RequestParam Integer id){
        System.out.println(id);
        return "aggiungi_impianto";
    }
}
