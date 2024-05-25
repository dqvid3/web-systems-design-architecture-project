package it.unipa.wsda.gestione;
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
        Impianto impianto = impiantoRepository.findById(codImpianto);
        int codPalinsesto = impianto.getRefPalinsesto();
        Palinsesto palinsesto = palinsestoRepository.findById(codPalinsesto);
        String path = palinsesto.getPathPalinsesto();
        System.out.println(path);
        model.addAttribute("path", path);
        return "led_wall";
    }
}
