package it.unipa.wsda.gestione.repositories;

import it.unipa.wsda.gestione.entities.Cartellone;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface CartelloneRepository extends CrudRepository<Cartellone, Integer> {
    List<Cartellone> findByNomeLike(String nome);
}
