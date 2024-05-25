package it.unipa.wsda.gestione;

import org.springframework.data.repository.CrudRepository;

public interface PalinsestoRepository extends CrudRepository<Palinsesto, Integer>{

    Palinsesto findById(int id);
}
