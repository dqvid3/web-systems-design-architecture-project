-- Genera 100 INSERT casuali per la tabella visualizzazione

DELIMITER //

CREATE PROCEDURE genera_visualizzazioni(IN num_inserts INT)
BEGIN
    DECLARE i INT DEFAULT 1;
    WHILE i <= num_inserts DO
        -- Genera valori casuali per ogni colonna
        SET @ref_impianto = FLOOR(RAND() * 5) + 1;
        SET @ref_cartellone = FLOOR(RAND() * 10) + 1;
        SET @durata_visualizzazione = (FLOOR(RAND() * 6) + 1) * 10;
        SET @ultimo_segnale = DATE_FORMAT(FROM_UNIXTIME(UNIX_TIMESTAMP('2024-01-01 00:00:00') + FLOOR(RAND() * (UNIX_TIMESTAMP(NOW()) - UNIX_TIMESTAMP('2024-01-01 00:00:00')))), '%Y-%m-%d %H:%i:%s');

        -- Esegui l'INSERT
        INSERT INTO visualizzazione(ref_impianto, ref_cartellone, durata_visualizzazione, ultimo_segnale)
        VALUES (@ref_impianto, @ref_cartellone, @durata_visualizzazione, @ultimo_segnale);

        SET i = i + 1;
    END WHILE;
END //

DELIMITER ;

-- Esegui la procedura per generare 100 INSERT
CALL genera_visualizzazioni(100);

-- Rimuovi la procedura (opzionale)
DROP PROCEDURE IF EXISTS genera_visualizzazioni;
