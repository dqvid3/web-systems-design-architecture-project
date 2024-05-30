package it.unipa.wsda.gestione.controllers;

import it.unipa.wsda.gestione.entities.Impianto;
import it.unipa.wsda.gestione.entities.Palinsesto;
import it.unipa.wsda.gestione.services.GestioneService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.sql.SQLException;

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

    @PostMapping("/modifica_impianto")
    public String showModificaImpianti(@RequestParam Integer cod, @RequestParam String desc, @RequestParam BigDecimal lat, @RequestParam BigDecimal lon, @RequestParam String pal,  Model model) {
        model.addAttribute("codImpianto", cod);
        model.addAttribute("descrizione", desc);
        model.addAttribute("latitudine", lat);
        model.addAttribute("longitudine", lon);
        model.addAttribute("palinsesto", pal);
        model.addAttribute("palinsesti", gestioneService.getPalinsesti());
        model.addAttribute("impianto", new Impianto());
        System.out.println(desc);
//
        return "modifica_impianto";
    }

    @PostMapping("/modifica_impianto/continue")
    public String modificaImpianto(@ModelAttribute("impianto") Impianto impianto, Model model){
        try {
            gestioneService.modificaImpianto(impianto);
        } catch (RuntimeException e) {
            model.addAttribute("errore", "Errore nella modifica di un impianto");
            return "error";
        }
        return "redirect:/gestione";
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
