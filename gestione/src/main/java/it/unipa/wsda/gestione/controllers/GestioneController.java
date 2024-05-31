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
        model.addAttribute("palinsesti", gestioneService.getPalinsesti());
        model.addAttribute("impiantoDaModificare", null);
        session.setAttribute("impianti", impianti);
        return "gestione";
    }

    @PostMapping("/salva_impianto")
    public String showModificaImpianto(@ModelAttribute("impianto") Impianto impianto,  Model model) {
        model.addAttribute("palinsesti", gestioneService.getPalinsesti());
        model.addAttribute("impianto", impianto);
        return "salva_impianto";
    }

    @PostMapping("/salva_impianto_successo")
    public String salvaImpianto(@ModelAttribute("impianto") Impianto impianto, Model model){
        try {
            gestioneService.salvaImpianto(impianto);
        } catch (RuntimeException e) {
            model.addAttribute("errore", "Errore nella modifica di un impianto");
            return "error";
        }
        return "redirect:/gestione";
    }

    @GetMapping("/salva_impianto")
    public String showAggiungiImpianto(Model model) {
        model.addAttribute("palinsesti", gestioneService.getPalinsesti());
        model.addAttribute("impianto", new Impianto());
        return "salva_impianto";
    }

    @PostMapping("/elimina_impianto")
    public String eliminaImpianto(@RequestParam Integer codImpianto, Model model) {
        try {
            gestioneService.eliminaImpianto(codImpianto);
        } catch (RuntimeException e) {
            model.addAttribute("errore", "Errore nell'eliminazione dell'impianto");
            return "error";
        }
        return "redirect:/gestione";
    }
}
