package it.unipa.wsda.gestione;

import org.springframework.data.repository.CrudRepository;

public interface ImpiantoRepository extends CrudRepository<Impianto, Integer> {
    Impianto findByCodImpianto(int codImpianto);
}
