package com.santiago.canchaapp.dominio;

import java.io.Serializable;
import java.util.List;

public class Cancha implements Serializable {

    private CanchaHeader datos;

    private List<String> fotos;

    public Cancha(CanchaHeader datos, List<String> fotos) {
        this.datos = datos;
        this.fotos = fotos;
    }

    public CanchaHeader getDatos() {
        return datos;
    }

    public List<String> getFotos() {
        return fotos;
    }

}
