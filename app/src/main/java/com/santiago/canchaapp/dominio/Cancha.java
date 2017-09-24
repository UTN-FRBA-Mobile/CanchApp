package com.santiago.canchaapp.dominio;

import java.io.Serializable;

public class Cancha implements Serializable {

    private String nombre;

    private TipoCancha tipoCancha;

    private TipoSuperficie superficie;

    private Boolean techada;

    private String fotoUrl;

    public Cancha(String nombre, TipoCancha tipoCancha, TipoSuperficie superficie, Boolean techada, String fotoUrl) {
        this.nombre = nombre;
        this.tipoCancha = tipoCancha;
        this.superficie = superficie;
        this.techada = techada;
        this.fotoUrl = fotoUrl;
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

    public String getFotoUrl() {
        return this.fotoUrl;
    }

}
