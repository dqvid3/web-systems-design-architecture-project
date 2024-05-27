package it.unipa.wsda.gestione.repositories;

import it.unipa.wsda.gestione.entities.Utente;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UtenteRepository extends CrudRepository<Utente, Integer> {
    @Query("SELECT u FROM Utente u WHERE u.username = :username")
    public Utente getUserByUsername(@Param("username") String username);
}
