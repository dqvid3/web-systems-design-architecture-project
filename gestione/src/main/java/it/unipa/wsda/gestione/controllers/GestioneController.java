package it.unipa.wsda.gestione.controllers;

import it.unipa.wsda.gestione.entities.Impianto;
import it.unipa.wsda.gestione.services.GestioneService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class GestioneController {
    @Autowired
    private GestioneService gestioneService;

    @GetMapping("/gestione")
    public String showImpianti(Model model, HttpSession session) {
        Iterable<Impianto> impianti = gestioneService.getImpianti();
        model.addAttribute("impianti", impianti);
        model.addAttribute("impiantoDaModificare", null);
        session.setAttribute("impianti", impianti);
        return "gestione";
    }

    @PostMapping("/gestione")
    public String modificaImpianto(Model model, HttpSession session, @RequestParam Integer codImpianto) {
        System.out.println(gestioneService.getImpianto(codImpianto).getDescrizione());
        model.addAttribute("impianti", session.getAttribute("impianti"));
        model.addAttribute("palinsesti", gestioneService.getPalinsesti());
        model.addAttribute("impiantoDaModificare", gestioneService.getImpianto(codImpianto));
        return "gestione";
    }

    @GetMapping("/aggiungi_impianto")
    public String showAggiungiImpianto(Model model) {
        model.addAttribute("palinsesti", gestioneService.getPalinsesti());
        model.addAttribute("impianto", new Impianto());
        return "aggiungi_impianto";
    }

    @PostMapping("/aggiungi_impianto")
    public String aggiungiImpianto(@ModelAttribute("impianto") Impianto impianto, Model model) {
        try {
            gestioneService.aggiungiImpianto(impianto);
        } catch (RuntimeException e) {
            model.addAttribute("errore", "Errore nell'aggiunta di un impianto");
            return "error";
        }

        return "redirect:/gestione";
    }
}
