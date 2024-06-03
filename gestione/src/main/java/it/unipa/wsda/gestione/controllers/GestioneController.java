package it.unipa.wsda.gestione.controllers;

import it.unipa.wsda.gestione.entities.Impianto;
import it.unipa.wsda.gestione.services.GestioneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@Controller
public class GestioneController {
    @Autowired
    private GestioneService gestioneService;

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/403")
    public String _403() {
        return "403";
    }

    @GetMapping("/gestione")
    public String showImpianti(Model model) {
        Iterable<Impianto> impianti = gestioneService.getImpianti();
        model.addAttribute("impianti", impianti);
        model.addAttribute("palinsesti", gestioneService.getPalinsesti());
        model.addAttribute("impiantoDaModificare", null);
        return "gestione";
    }

    @PostMapping("gestione/salva_impianto")
    public String showModificaImpianto(@ModelAttribute("impianto") Impianto impianto, Model model) {
        model.addAttribute("message", "Modifica impianto");
        System.out.println(impianto.getStato());
        model.addAttribute("palinsesti", gestioneService.getPalinsesti());
        model.addAttribute("impianto", impianto);
        return "salva_impianto";
    }

    @PostMapping("gestione/salva_impianto_conferma")
    public String salvaImpianto(@ModelAttribute("impianto") Impianto impianto, Model model) {
        try {
            gestioneService.salvaImpianto(impianto);
        } catch (RuntimeException e) {
            String messaggio = "Errore durante il salvataggio dell'impianto";
            if (impianto.getCodImpianto() != null)
                messaggio = "Errore durante l'inserimento dell'impianto";
            model.addAttribute("errore", messaggio);
            model.addAttribute("impianto", impianto);
            model.addAttribute("palinsesti", gestioneService.getPalinsesti());
            return "salva_impianto";
        }
        return "redirect:/gestione";
    }

    @GetMapping("gestione/salva_impianto")
    public String showAggiungiImpianto(Model model) {
        model.addAttribute("message", "Aggiungi impianto");
        model.addAttribute("palinsesti", gestioneService.getPalinsesti());
        model.addAttribute("impianto", new Impianto());
        return "salva_impianto";
    }
    /*
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
    */
    @GetMapping("/suggerisci_coordinate")
    @ResponseBody
    public ResponseEntity<String> suggerisciCoordinate(@RequestParam String text) {
        String apiUrl = "https://nominatim.openstreetmap.org/search.php?q=" + text + "&countrycodes=it&format=jsonv2&limit=10";
        ResponseEntity<String> response = restTemplate.getForEntity(apiUrl, String.class);
        return ResponseEntity.ok(response.getBody());
    }
}
