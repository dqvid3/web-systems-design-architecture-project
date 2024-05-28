package it.unipa.wsda.gestione.services;

import it.unipa.wsda.gestione.entities.Impianto;
import it.unipa.wsda.gestione.repositories.ImpiantoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GestioneService {
    @Autowired
    private ImpiantoRepository impiantoRepository;

    public Iterable<Impianto> getImpianti() {
        return impiantoRepository.findAll();
    }
}