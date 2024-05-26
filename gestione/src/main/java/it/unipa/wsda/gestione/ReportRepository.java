package it.unipa.wsda.gestione;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ReportRepository extends CrudRepository<ReportEntry, Integer> {

    @Query(value = "SELECT v.ref_cartellone, c.nome, COUNT(*) AS numero_visualizzazioni " +
            "FROM visualizzazione v, cartellone c " +
            "WHERE v.ultimo_segnale BETWEEN :startDate AND :endDate " +
            "AND c.cod_cartellone = v.ref_cartellone " +
            "AND c.nome LIKE :cartelloneName " +
            "GROUP BY v.ref_cartellone " +
            "HAVING numero_visualizzazioni > :minViews " +
            "ORDER BY numero_visualizzazioni DESC " +
            "LIMIT 3", nativeQuery = true)
    List<ReportEntry> findCustomReport(LocalDateTime startDate, LocalDateTime endDate, String cartelloneName, int minViews);
}
