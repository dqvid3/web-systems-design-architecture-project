SELECT i.cod_impianto, i.descrizione, i.latitudine, i.longitudine
FROM impianto i;
SELECT c.cod_cartellone, c.nome
FROM cartellone c;
SELECT v.cod_segnalazione,
       v.ref_impianto,
       v.cod_palinsesto,
       v.ref_cartellone,
       v.durata_visualizzazione,
       v.ultimo_segnale
FROM visualizzazione v;
SELECT p.cod_palinsesto, p.nome_palinsesto, p.path_palinsesto
FROM palinsesto p;

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
FROM visualizzazione v,
     impianto i
WHERE v.ref_impianto = i.cod_impianto
GROUP BY v.ref_impianto;

-- Fuso orario:
SELECT @@global.time_zone, @@session.time_zone;

SELECT i.ref_palinsesto
FROM impianto i
WHERE i.cod_impianto = 1;
SELECT p.path_palinsesto
FROM palinsesto p
WHERE p.cod_palinsesto = 1;

-- Esempio di annidata (inizialmente i cartelloni non avevano un id, ma solo un nome...):
INSERT INTO visualizzazione(ref_impianto, ref_cartellone, durata_visualizzazione)
VALUES (1, (SELECT c.cod_cartellone FROM cartellone c WHERE c.nome = 'omega'), 30);
-- Altri esempi:
INSERT INTO visualizzazione(ref_impianto, ref_cartellone, durata_visualizzazione)
VALUES (1, 1, 30);
INSERT INTO visualizzazione(ref_impianto, ref_cartellone, durata_visualizzazione)
VALUES (1, 3, 20);
INSERT INTO visualizzazione(ref_impianto, ref_cartellone, durata_visualizzazione, ultimo_segnale)
VALUES (2, 1, 10, '2024-04-28 17:52:13');
INSERT INTO visualizzazione(ref_impianto, ref_cartellone, durata_visualizzazione)
VALUES (3, 4, 20);
INSERT INTO visualizzazione(ref_impianto, ref_cartellone, durata_visualizzazione, ultimo_segnale)
VALUES (1, 5, 20, '2024-04-29 19:50:43');
INSERT INTO visualizzazione(ref_impianto, ref_cartellone, durata_visualizzazione, ultimo_segnale)
VALUES (4, 5, 20, '2024-04-27 18:50:43');
INSERT INTO visualizzazione(ref_impianto, ref_cartellone, durata_visualizzazione, ultimo_segnale)
VALUES (5, 5, 20, '2024-04-28 17:50:43');

