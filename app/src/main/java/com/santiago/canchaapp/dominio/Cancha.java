package com.santiago.canchaapp.dominio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Cancha implements Serializable {

    private String nombre;

    private TipoCancha tipoCancha;

    private TipoSuperficie superficie;

    private Boolean techada;

    private List<String> fotosUrls = new ArrayList<>();

    public Cancha(String nombre, TipoCancha tipoCancha, TipoSuperficie superficie, Boolean techada, List<String> fotosUrls) {
        this.nombre = nombre;
        this.tipoCancha = tipoCancha;
        this.superficie = superficie;
        this.techada = techada;
        this.fotosUrls = fotosUrls;
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

    public boolean tieneFotos() {
        return !fotosUrls.isEmpty();
    }

    public String getFotoPrincipalUrl() {
        return tieneFotos() ? fotosUrls.get(0) : null;
    }

    public List<String> getFotosUrls() {
        return this.fotosUrls;
    }

}
