package it.unipa.wsda.gestione.repositories;

import it.unipa.wsda.gestione.entities.Report;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface ReportRepository extends CrudRepository<Report, Integer> {

    @Query(value = "SELECT v.ref_cartellone, c.nome, " +
            "CASE " +
            "    WHEN :operator = 'COUNT' THEN COUNT(*) " +
            "    WHEN :operator = 'AVG' THEN AVG(v.durata_visualizzazione) " +
            "    WHEN :operator = 'SUM' THEN SUM(v.durata_visualizzazione) " +
            "    WHEN :operator = 'MAX' THEN MAX(v.durata_visualizzazione) " +
            "    WHEN :operator = 'MIN' THEN MIN(v.durata_visualizzazione) " +
            "END AS risultato " +
            "FROM visualizzazione v, cartellone c " +
            "WHERE v.ultimo_segnale BETWEEN :startDate AND :endDate " +
            "AND c.cod_cartellone = v.ref_cartellone " +
            "AND (:cartelloneName = '%' OR c.nome LIKE :cartelloneName) " +
            "GROUP BY v.ref_cartellone " +
            "HAVING risultato > :minViews " +
            "ORDER BY CASE WHEN :sortOrder = 'ASC' THEN risultato END ASC, " +
            "CASE WHEN :sortOrder = 'DESC' THEN risultato END DESC " +
            "LIMIT :limit", nativeQuery = true)
    List<Report> findCustomReport(@Param("startDate") LocalDateTime startDate,
                                  @Param("endDate") LocalDateTime endDate,
                                  @Param("cartelloneName") String cartelloneName,
                                  @Param("operator") String operator,
                                  @Param("minViews") int minViews,
                                  @Param("sortOrder") String sortOrder,
                                  @Param("limit") int limit);
}