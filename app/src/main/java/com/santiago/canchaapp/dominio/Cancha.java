package com.santiago.canchaapp.dominio;

import java.io.Serializable;

public class Cancha implements Serializable {

    private String nombre;

    private TipoCancha tipoCancha;

    private TipoSuperficie superficie;

    private Boolean techada;

    public Cancha(String nombre, TipoCancha tipoCancha, TipoSuperficie superficie, Boolean techada) {
        this.nombre = nombre;
        this.tipoCancha = tipoCancha;
        this.superficie = superficie;
        this.techada = techada;
    }

    public String getNombre() {
        return nombre;
    }

    public TipoCancha getTipoCancha() {
        return tipoCancha;
    }

    public TipoSuperficie getSuperficie() {
        return superficie;
    }

    public Boolean esTechada() {
        return techada;
    }
}
