package it.unipa.wsda.gestione.services;

import it.unipa.wsda.gestione.entities.Impianto;
import it.unipa.wsda.gestione.entities.Palinsesto;
import it.unipa.wsda.gestione.repositories.ImpiantoRepository;
import it.unipa.wsda.gestione.repositories.PalinsestoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GestioneService {
    @Autowired
    private ImpiantoRepository impiantoRepository;

    @Autowired
    private PalinsestoRepository palinsestoRepository;

    public Iterable<Impianto> getImpianti() {
        return impiantoRepository.findAll();
    }

    public Iterable<Palinsesto> getPalinsesti() {
        return palinsestoRepository.findAll();
    }
    /*
    public void eliminaImpianto(Integer codImpianto) {
        try {
            impiantoRepository.deleteById(codImpianto);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    */
    public void salvaImpianto(Impianto impianto) {
        try {
            impiantoRepository.save(impianto);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
