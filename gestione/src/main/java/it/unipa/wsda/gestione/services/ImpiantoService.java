package it.unipa.wsda.gestione.services;

import it.unipa.wsda.gestione.entities.Impianto;
import it.unipa.wsda.gestione.entities.Palinsesto;
import it.unipa.wsda.gestione.repositories.ImpiantoRepository;
import it.unipa.wsda.gestione.repositories.PalinsestoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImpiantoService {

    @Autowired
    private ImpiantoRepository impiantoRepository;
    @Autowired
    private PalinsestoRepository palinsestoRepository;

    public String getPathPalinsesto(int codImpianto) {
        Impianto impianto = impiantoRepository.findByCodImpianto(codImpianto);
        if (!impianto.isAttivo()) {
            throw new IllegalStateException("Impianto non attivo");
        }
        int codPalinsesto = impianto.getPalinsesto().getCodPalinsesto();
        Palinsesto palinsesto = palinsestoRepository.findByCodPalinsesto(codPalinsesto);
        return palinsesto.getPathPalinsesto();
    }
}

