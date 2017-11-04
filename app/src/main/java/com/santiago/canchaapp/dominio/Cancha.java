package com.santiago.canchaapp.dominio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Cancha implements Serializable {

    private String uuid;

    private String nombre;

    private int precio;

    private TipoCancha tipoCancha;

    private TipoSuperficie superficie;

    private Boolean techada;

    private List<String> fotosUrls = new ArrayList<>();

    // Denormalizado para facilitar queries
    private String idClub;

    private Horario rangoHorario;

    public Cancha(UUID uuid, String nombre, TipoCancha tipoCancha, TipoSuperficie superficie, Boolean techada, int precio, List<String> fotosUrls, UUID idClub, Horario horario) {
        this.uuid = uuid.toString();
        this.nombre = nombre;
        this.tipoCancha = tipoCancha;
        this.superficie = superficie;
        this.techada = techada;
        this.fotosUrls = fotosUrls;
        this.idClub = idClub.toString();
        this.rangoHorario = horario;
        this.precio = precio;
    }

    public String getUuid() {
        return uuid;
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

    public Boolean getTechada() {
        return techada;
    }

    public String getIdClub() {
        return idClub;
    }

    public Horario getRangoHorario() {
        return rangoHorario;
    }

    public int getPrecio() {
        return precio;
    }

}
