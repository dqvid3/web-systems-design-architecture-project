package it.unipa.wsda.gestione.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Palinsesto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer codPalinsesto;
    private String nomePalinsesto;
    private String pathPalinsesto;

    public int getCodPalinsesto() {
        return codPalinsesto;
    }

    public void setCodPalinsesto(int codPalinsesto) {
        this.codPalinsesto = codPalinsesto;
    }

    public String getNomePalinsesto() {
        return nomePalinsesto;
    }

    public void setNomePalinsesto(String nomePalinsesto) {
        this.nomePalinsesto = nomePalinsesto;
    }

    public String getPathPalinsesto() {
        return pathPalinsesto;
    }

    public void setPathPalinsesto(String pathPalinsesto) {
        this.pathPalinsesto = pathPalinsesto;
    }
}