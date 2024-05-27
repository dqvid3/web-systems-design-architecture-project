package it.unipa.wsda.gestione.repositories;

import it.unipa.wsda.gestione.entities.Palinsesto;
import org.springframework.data.repository.CrudRepository;

public interface PalinsestoRepository extends CrudRepository<Palinsesto, Integer> {
    Palinsesto findByCodPalinsesto(int codPalinsesto);
}
