package it.unipa.wsda.gestione;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReportRepository extends CrudRepository<ReportEntry, Integer> {

    @Query(value = "SELECT v.ref_cartellone, c.nome, COUNT(*) AS numero_visualizzazioni " +
            "FROM visualizzazione v, cartellone c " +
            "WHERE v.ultimo_segnale BETWEEN :startDate AND :endDate " +
            "AND c.cod_cartellone = v.ref_cartellone " +
            "AND c.nome LIKE :cartelloneName " +
            "GROUP BY v.ref_cartellone " +
            "HAVING numero_visualizzazioni > :minViews " +
            "ORDER BY numero_visualizzazioni :sortOrder " +
            "LIMIT 3", nativeQuery = true)
    List<ReportEntry> findCustomReport(String startDate, String endDate, String cartelloneName, int minViews, String sortOrder);

    @Query("SELECT DISTINCT c.nome FROM Cartellone c")
    List<String> findAllCartelloni();
}
