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
    -- ref_palinsesto         INT NOT NULL, -- palinsesto al momento della visualizzazione
    ref_cartellone         INT NOT NULL,
    durata_visualizzazione INT NOT NULL,
    ultimo_segnale         DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (ref_impianto) REFERENCES impianto (cod_impianto),
    -- FOREIGN KEY (ref_palinsesto) REFERENCES palinsesto (cod_palinsesto),
    FOREIGN KEY (ref_cartellone) REFERENCES cartellone (cod_cartellone)
);

CREATE TABLE utente
(
    cod_utente    INT         NOT NULL AUTO_INCREMENT PRIMARY KEY,
    username      VARCHAR(50) NOT NULL,
    cognome       VARCHAR(50) NOT NULL,
    nome          VARCHAR(50) NOT NULL,
    email         VARCHAR(80) NOT NULL UNIQUE,
    password_hash VARCHAR(64) NOT NULL,
    enabled       BIT         NOT NULL DEFAULT 1
);

CREATE TABLE ruolo
(
    cod_ruolo INT         NOT NULL AUTO_INCREMENT PRIMARY KEY,
    nome      varchar(45) NOT NULL
);

CREATE TABLE ruolo_utente
(
    ref_utente INT NOT NULL,
    ref_ruolo  INT NOT NULL,
    PRIMARY KEY (ref_utente, ref_ruolo),
    FOREIGN KEY (ref_utente) REFERENCES utente (cod_utente),
    FOREIGN KEY (ref_ruolo) REFERENCES ruolo (cod_ruolo)
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

UPDATE impianto
SET stato = 1
WHERE cod_impianto = 1;

INSERT INTO utente(username, cognome, nome, email, password_hash)
VALUES ('davidebonura', 'Bonura', 'Davide', 'davide.bonura@mailfalsa.com',
        '$2y$10$z2aKc.WqF.w.9CnzLMXFk.UejZZKN/aed7y/ptSirnmgV3P82jl.y'); -- pass = '124'
INSERT INTO utente(username, cognome, nome, email, password_hash)
VALUES ('gabrielebova01', 'Bova', 'Gabriele', 'gabriele.bova@mailfalsa.com',
        '$2a$10$5tkWlCPD2.fJMoE3ppYdpeGEP/a6.qkaLbXCbxs6AEG/P4TxEodaq'); -- pass = 'prova'
INSERT INTO utente(username, cognome, nome, email, password_hash)
VALUES ('ciaosalvo', 'D\'Anna', 'Salvatore', 'salvodanna@mailfalsa.com', '$2a$10$QPS1SLC69IWmnBKhMdD6wewUh1e8aKg0xRE2McMhqdbI83bt15bU6'); -- pass = 'qwerty'

INSERT INTO ruolo(nome) VALUES ('ADMIN');
INSERT INTO ruolo(nome) VALUES ('REPORTISTICA');
INSERT INTO ruolo(nome) VALUES ('GESTIONE');

INSERT INTO ruolo_utente(ref_utente, ref_ruolo) VALUES (1, 1);
INSERT INTO ruolo_utente(ref_utente, ref_ruolo) VALUES (2, 2);
INSERT INTO ruolo_utente(ref_utente, ref_ruolo) VALUES (3, 3);
