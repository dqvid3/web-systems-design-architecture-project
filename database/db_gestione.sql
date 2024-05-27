CREATE
DATABASE IF NOT EXISTS db_gestione;
USE
db_gestione;

-- Fatturazione...


CREATE TABLE utente (
    username VARCHAR(50) NOT NULL,
    cognome VARCHAR(50) NOT NULL,
    nome VARCHAR(50) NOT NULL,
    email VARCHAR(80) NOT NULL UNIQUE, -- Recupero pass?
    pass VARCHAR(64) NOT NULL,
    ruolo INT NOT NULL,
    PRIMARY KEY (username)
);

INSERT INTO utente(username, cognome, nome, email, pass) VALUES ('', 'Bonura', 'Davide', '', '');
INSERT INTO utente(username, cognome, nome, email, pass) VALUES ('gabrielebova01', 'Bova', 'Gabriele', 'gabriele.bova@mailfalsa.com',
        '6258a5e0eb772911d4f92be5b5db0e14511edbe01d1d0ddd1d5a2cb9db9a56ba'); -- pass = 'prova'
INSERT INTO utente(username, cognome, nome, email, pass) VALUES ('', 'D\'Anna', 'Salvatore', '', '');
