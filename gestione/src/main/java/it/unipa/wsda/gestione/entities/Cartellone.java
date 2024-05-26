package it.unipa.wsda.gestione.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Cartellone {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int codCartellone;
    private String nome;

    public Integer getCodCartellone() {
        return codCartellone;
    }

    public void setCodCartellone(int codCartellone) {
        this.codCartellone = codCartellone;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String toString(){
        return nome;
    }
}