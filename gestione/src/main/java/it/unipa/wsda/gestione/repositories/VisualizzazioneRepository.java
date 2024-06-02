package it.unipa.wsda.gestione.repositories;

import it.unipa.wsda.gestione.entities.Visualizzazione;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface VisualizzazioneRepository extends CrudRepository<Visualizzazione, Integer> {
    @Query(value = "SELECT v.ref_cartellone, " +
            "CASE " +
            "    WHEN :operator = 'COUNT' THEN COUNT(*) " +
            "    WHEN :operator = 'AVG' THEN AVG(v.durata_visualizzazione) " +
            "    WHEN :operator = 'SUM' THEN SUM(v.durata_visualizzazione) " +
            "    WHEN :operator = 'MAX' THEN MAX(v.durata_visualizzazione) " +
            "    WHEN :operator = 'MIN' THEN MIN(v.durata_visualizzazione) " +
            "END AS risultato " +
            "FROM visualizzazione v " +
            "WHERE v.ultimo_segnale BETWEEN :startDate AND :endDate " +
            "AND (:refCartellone = 0 OR v.ref_cartellone = :refCartellone) " +
            "GROUP BY v.ref_cartellone " +
            "HAVING risultato >= :minViews " +
            "ORDER BY CASE WHEN :sortOrder = 'ASC' THEN risultato END ASC, " +
            "CASE WHEN :sortOrder = 'DESC' THEN risultato END DESC " +
            "LIMIT :limit", nativeQuery = true)
    List<List<Integer>> findVisualizzazioniByPeriodo(@Param("startDate") LocalDateTime startDate,
                                                     @Param("endDate") LocalDateTime endDate,
                                                     @Param("refCartellone") int refCartellone,
                                                     @Param("operator") String operator,
                                                     @Param("minViews") int minViews,
                                                     @Param("sortOrder") String sortOrder,
                                                     @Param("limit") int limit);
}
