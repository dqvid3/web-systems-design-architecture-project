CREATE DATABASE IF NOT EXISTS db_monitoraggio;
USE db_monitoraggio;

CREATE TABLE impianto (
    cod_impianto INT AUTO_INCREMENT PRIMARY KEY,
    descrizione VARCHAR(255) NOT NULL,
    latitudine DECIMAL(10, 6) NOT NULL,
    longitudine DECIMAL(10, 6) NOT NULL,
    UNIQUE (latitudine, longitudine)
);

CREATE TABLE cartellone (
    cod_cartellone INT NOT NULL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL
);

CREATE TABLE visualizzazione (
    cod_segnalazione INT AUTO_INCREMENT PRIMARY KEY,
    ref_impianto INT NOT NULL,
    cod_palinsesto INT NOT NULL,
    ref_cartellone INT NOT NULL,
    durata_visualizzazione INT NOT NULL,
    ultimo_segnale DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (ref_impianto) REFERENCES impianto(cod_impianto),
    FOREIGN KEY (ref_cartellone) REFERENCES cartellone(cod_cartellone)
);

INSERT INTO impianto(cod_impianto, descrizione, latitudine, longitudine) VALUES (1, "Stazione Palermo Centrale", 38.110202, 13.366752);
INSERT INTO impianto(cod_impianto, descrizione, latitudine, longitudine) VALUES (2, "Universit&agrave; degli Studi di Palermo", 38.106200, 13.350856);
INSERT INTO impianto(cod_impianto, descrizione, latitudine, longitudine) VALUES (3, "Piazza Castelnuovo Palermo", 38.124381, 13.355076);

SELECT i.cod_impianto, i.descrizione, i.latitudine, i.longitudine FROM impianto i;

INSERT INTO cartellone(cod_cartellone, nome) VALUES (1, "arancine");
INSERT INTO cartellone(cod_cartellone, nome) VALUES (2, "arcteryx");
INSERT INTO cartellone(cod_cartellone, nome) VALUES (3, "guinness");
INSERT INTO cartellone(cod_cartellone, nome) VALUES (4, "lidl");
INSERT INTO cartellone(cod_cartellone, nome) VALUES (5, "mc");
INSERT INTO cartellone(cod_cartellone, nome) VALUES (6, "omega");
INSERT INTO cartellone(cod_cartellone, nome) VALUES (7, "phone2a");
INSERT INTO cartellone(cod_cartellone, nome) VALUES (8, "pizza");
INSERT INTO cartellone(cod_cartellone, nome) VALUES (9, "tesla");
INSERT INTO cartellone(cod_cartellone, nome) VALUES (10, "tommy");

SELECT c.cod_cartellone, c.nome FROM cartellone c;

-- Esempio di annidata (inizialmente i cartelloni non avevano un id, ma solo un nome...):
INSERT INTO visualizzazione(ref_impianto, cod_palinsesto, ref_cartellone, durata_visualizzazione) VALUES (1, 1, (SELECT c.cod_cartellone FROM cartellone c WHERE c.nome = "omega"), 30);
-- Altri esempi:
INSERT INTO visualizzazione(ref_impianto, cod_palinsesto, ref_cartellone, durata_visualizzazione) VALUES (1, 1, 1, 30);
INSERT INTO visualizzazione(ref_impianto, cod_palinsesto, ref_cartellone, durata_visualizzazione) VALUES (1, 2, 3, 20);
INSERT INTO visualizzazione(ref_impianto, cod_palinsesto, ref_cartellone, durata_visualizzazione, ultimo_segnale) VALUES (2, 3, 1, 10, "2024-04-28 17:52:13");
INSERT INTO visualizzazione(ref_impianto, cod_palinsesto, ref_cartellone, durata_visualizzazione) VALUES (3, 2, 4, 20);
INSERT INTO visualizzazione(ref_impianto, cod_palinsesto, ref_cartellone, durata_visualizzazione, ultimo_segnale) VALUES (1, 2, 5, 20, "2024-04-29 19:50:43");

SELECT v.cod_segnalazione, v.ref_impianto, v.cod_palinsesto, v.ref_cartellone, v.durata_visualizzazione, v.ultimo_segnale FROM visualizzazione v;

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
