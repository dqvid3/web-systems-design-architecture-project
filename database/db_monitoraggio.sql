CREATE
DATABASE IF NOT EXISTS db_monitoraggio;
USE
db_monitoraggio;

CREATE TABLE palinsesto
(
    cod_palinsesto  INT AUTO_INCREMENT PRIMARY KEY,
    nome_palinsesto VARCHAR(255) NOT NULL,
    path_palinsesto VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE impianto
(
    cod_impianto   INT AUTO_INCREMENT PRIMARY KEY,
    descrizione    VARCHAR(255)   NOT NULL,
    latitudine     DECIMAL(10, 6) NOT NULL,
    longitudine    DECIMAL(10, 6) NOT NULL,
    ref_palinsesto INT            NOT NULL,
    stato          BIT            NOT NULL DEFAULT 0,
    UNIQUE (latitudine, longitudine),
    FOREIGN KEY (ref_palinsesto) REFERENCES palinsesto (cod_palinsesto)
);

CREATE TABLE cartellone
(
    cod_cartellone INT AUTO_INCREMENT PRIMARY KEY,
    nome           VARCHAR(255) NOT NULL
);

CREATE TABLE visualizzazione
(
    cod_segnalazione       INT AUTO_INCREMENT PRIMARY KEY,
    ref_impianto           INT NOT NULL,
    ref_palinsesto         INT NOT NULL, -- palinsesto al momento della visualizzazione
    ref_cartellone         INT NOT NULL,
    durata_visualizzazione INT NOT NULL,
    ultimo_segnale         DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (ref_impianto) REFERENCES impianto (cod_impianto),
    FOREIGN KEY (ref_palinsesto) REFERENCES palinsesto (cod_palinsesto),
    FOREIGN KEY (ref_cartellone) REFERENCES cartellone (cod_cartellone)
);

INSERT INTO palinsesto(nome_palinsesto, path_palinsesto)
VALUES ('Palinsesto 1', 'palinsesti/palinsesto1.xml');
INSERT INTO palinsesto(nome_palinsesto, path_palinsesto)
VALUES ('Palinsesto 2', 'palinsesti/palinsesto2.xml');
INSERT INTO palinsesto(nome_palinsesto, path_palinsesto)
VALUES ('Palinsesto 3', 'palinsesti/palinsesto3.xml');
INSERT INTO palinsesto(nome_palinsesto, path_palinsesto)
VALUES ('Palinsesto 4', 'palinsesti/palinsesto4.xml');
INSERT INTO palinsesto(nome_palinsesto, path_palinsesto)
VALUES ('Palinsesto 5', 'palinsesti/palinsesto5.xml');

INSERT INTO impianto(descrizione, latitudine, longitudine, ref_palinsesto)
VALUES ('Stazione Palermo Centrale', 38.110202, 13.366752, 1);
INSERT INTO impianto(descrizione, latitudine, longitudine, ref_palinsesto)
VALUES ('Universit&agrave; degli Studi di Palermo', 38.106200, 13.350856, 2);
INSERT INTO impianto(descrizione, latitudine, longitudine, ref_palinsesto)
VALUES ('Piazza Castelnuovo Palermo', 38.124381, 13.355076, 3);
INSERT INTO impianto(descrizione, latitudine, longitudine, ref_palinsesto)
VALUES ('Stazione Palermo Notarbartolo', 38.132684, 13.342086, 4);
INSERT INTO impianto(descrizione, latitudine, longitudine, ref_palinsesto)
VALUES ('Piazza Giuseppe Verdi', 38.120854, 13.357722, 5);

INSERT INTO cartellone(nome)
VALUES ('arancine');
INSERT INTO cartellone(nome)
VALUES ('arcteryx');
INSERT INTO cartellone(nome)
VALUES ('guinness');
INSERT INTO cartellone(nome)
VALUES ('lidl');
INSERT INTO cartellone(nome)
VALUES ('mc');
INSERT INTO cartellone(nome)
VALUES ('omega');
INSERT INTO cartellone(nome)
VALUES ('phone2a');
INSERT INTO cartellone(nome)
VALUES ('pizza');
INSERT INTO cartellone(nome)
VALUES ('tesla');
INSERT INTO cartellone(nome)
VALUES ('tommy');

-- Esempio di annidata (inizialmente i cartelloni non avevano un id, ma solo un nome...):
INSERT INTO visualizzazione(ref_impianto, ref_palinsesto, ref_cartellone, durata_visualizzazione)
VALUES (1, 1, (SELECT c.cod_cartellone FROM cartellone c WHERE c.nome = 'omega'), 30);
-- Altri esempi:
INSERT INTO visualizzazione(ref_impianto, ref_palinsesto, ref_cartellone, durata_visualizzazione)
VALUES (1, 1, 1, 30);
INSERT INTO visualizzazione(ref_impianto, ref_palinsesto, ref_cartellone, durata_visualizzazione)
VALUES (1, 2, 3, 20);
INSERT INTO visualizzazione(ref_impianto, ref_palinsesto, ref_cartellone, durata_visualizzazione, ultimo_segnale)
VALUES (2, 3, 1, 10, '2024-04-28 17:52:13');
INSERT INTO visualizzazione(ref_impianto, ref_palinsesto, ref_cartellone, durata_visualizzazione)
VALUES (3, 2, 4, 20);
INSERT INTO visualizzazione(ref_impianto, ref_palinsesto, ref_cartellone, durata_visualizzazione, ultimo_segnale)
VALUES (1, 2, 5, 20, '2024-04-29 19:50:43');
INSERT INTO visualizzazione(ref_impianto, ref_palinsesto, ref_cartellone, durata_visualizzazione, ultimo_segnale)
VALUES (4, 2, 5, 20, '2024-04-27 18:50:43');
INSERT INTO visualizzazione(ref_impianto, ref_palinsesto, ref_cartellone, durata_visualizzazione, ultimo_segnale)
VALUES (5, 2, 5, 20, '2024-04-28 17:50:43');

UPDATE impianto
SET stato = 1
WHERE cod_impianto = 1;
