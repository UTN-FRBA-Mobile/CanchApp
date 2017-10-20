package com.santiago.canchaapp.dominio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Cancha implements Serializable {

    private UUID uuid;

    private String nombre;

    private TipoCancha tipoCancha;

    private TipoSuperficie superficie;

    private Boolean techada;

    private Horario horario;

    private List<String> fotosUrls = new ArrayList<>();

    public Cancha(UUID uuid, String nombre, TipoCancha tipoCancha, TipoSuperficie superficie, Boolean techada, List<String> fotosUrls, Horario horario) {
        this.uuid = uuid;
        this.nombre = nombre;
        this.tipoCancha = tipoCancha;
        this.superficie = superficie;
        this.techada = techada;
        this.fotosUrls = fotosUrls;
        this.horario = horario;
    }

    public String getUuid() {
        return uuid.toString();
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

    public Horario getHorario() {
        return horario;
    }

}
