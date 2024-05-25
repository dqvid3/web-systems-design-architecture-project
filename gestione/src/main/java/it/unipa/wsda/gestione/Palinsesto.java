package it.unipa.wsda.gestione;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
@Entity
public class Palinsesto {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int codPalinsesto;

    private String pathPalinsesto;

    public String getPathPalinsesto() {
        return this.pathPalinsesto;
    }

    public void setPathPalinsesto(String pathPalinsesto) {
        this.pathPalinsesto = pathPalinsesto;
    }
}