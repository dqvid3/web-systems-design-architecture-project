package it.unipa.wsda.gestione.repositories;

import it.unipa.wsda.gestione.entities.Impianto;
import org.springframework.data.repository.CrudRepository;

public interface ImpiantoRepository extends CrudRepository<Impianto, Integer> {
    Impianto findByCodImpianto(int codImpianto);
}
