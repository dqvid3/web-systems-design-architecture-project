SELECT i.cod_impianto, i.descrizione, i.latitudine, i.longitudine FROM impianto i;
SELECT c.cod_cartellone, c.nome FROM cartellone c;
SELECT v.cod_segnalazione, v.ref_impianto, v.cod_palinsesto, v.ref_cartellone, v.durata_visualizzazione, v.ultimo_segnale FROM visualizzazione v;
SELECT p.cod_palinsesto, p.nome_palinsesto, p.path_palinsesto FROM palinsesto p;

-- Dati degli impianti che hanno inviato segnalazioni negli ultimi due minuti:
-- SELECT DISTINCT i.cod_impianto, i.descrizione, i.latitudine, i.longitudine, v.ultimo_segnale
-- FROM visualizzazione v, impianto i
-- WHERE v.ref_impianto = i.cod_impianto AND v.ultimo_segnale >= NOW() - INTERVAL 2 MINUTE;

-- Dati degli impianti che non l'hanno fatto, riportando per ciascun impianto il timestamp dell'ultimo segnale inviato al sistema di monitoraggio:
-- SELECT i.cod_impianto, i.descrizione, i.latitudine, i.longitudine, MAX(v.ultimo_segnale) AS ultimo_segnale
-- FROM visualizzazione v, impianto i
-- WHERE v.ref_impianto = i.cod_impianto
-- GROUP BY v.ref_impianto
-- HAVING MAX(v.ultimo_segnale) < NOW() - INTERVAL 2 MINUTE;

-- Dati degli impianti (indipendentemente dall'ultimo segnale), riportando per ciascun impianto il timestamp dell'ultimo segnale inviato al sistema di monitoraggio:
SELECT i.cod_impianto, i.descrizione, i.latitudine, i.longitudine, MAX(v.ultimo_segnale) AS ultimo_segnale
FROM visualizzazione v, impianto i
WHERE v.ref_impianto = i.cod_impianto
GROUP BY v.ref_impianto;

-- Fuso orario:
SELECT @@global.time_zone, @@session.time_zone;

SELECT i.ref_palinsesto FROM impianto i WHERE i.cod_impianto = 1;
SELECT p.path_palinsesto FROM palinsesto p WHERE p.cod_palinsesto = 1;
