CREATE TABLE utente (
    username VARCHAR(50) NOT NULL PRIMARY KEY,
    cognome VARCHAR(50) NOT NULL,
    nome VARCHAR(50) NOT NULL,
    email VARCHAR(80) NOT NULL UNIQUE,
    password_hash VARCHAR(64) NOT NULL,
    ref_ruolo INT NOT NULL
);

CREATE TABLE ruolo (
    
);



INSERT INTO utente(username, cognome, nome, email, password_hash) VALUES ('davidebonura', 'Bonura', 'Davide', 'davide.bonura@mailfalsa.com', '6affdae3b3c1aa6aa7689e9b6a7b3225a636aa1ac0025f490cca1285ceaf1487'); -- pass = '124'
INSERT INTO utente(username, cognome, nome, email, password_hash) VALUES ('gabrielebova01', 'Bova', 'Gabriele', 'gabriele.bova@mailfalsa.com',
        '6258a5e0eb772911d4f92be5b5db0e14511edbe01d1d0ddd1d5a2cb9db9a56ba'); -- pass = 'prova'
INSERT INTO utente(username, cognome, nome, email, password_hash) VALUES ('ciaosalvo', 'D\'Anna', 'Salvatore', 'salvodanna@mailfalsa.com', '65e84be33532fb784c48129675f9eff3a682b27168c0ea744b2cf58ee02337c5'); -- pass = 'qwerty'

INSERT INTO ruolo(nome) VALUES ('Addetto Gestione');
INSERT INTO ruolo(nome) VALUES ('Addetto Reportistica');

INSERT INTO ruolo_utente(ref_utente, ref_ruolo) VALUES (1, 1);
INSERT INTO ruolo_utente(ref_utente, ref_ruolo) VALUES (1, 2);
INSERT INTO ruolo_utente(ref_utente, ref_ruolo) VALUES (2, 1);
INSERT INTO ruolo_utente(ref_utente, ref_ruolo) VALUES (3, 2);
