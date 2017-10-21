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

    // Denormalizado para facilitar queries
    private DatosClub datosClub;

    private List<String> fotosUrls = new ArrayList<>();

    public Cancha(UUID uuid, String nombre, TipoCancha tipoCancha, TipoSuperficie superficie, Boolean techada, List<String> fotosUrls, UUID idClub, Horario horario) {
        this.uuid = uuid;
        this.nombre = nombre;
        this.tipoCancha = tipoCancha;
        this.superficie = superficie;
        this.techada = techada;
        this.fotosUrls = fotosUrls;
        this.datosClub = new DatosClub(idClub, horario);
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

    public DatosClub getDatosClub() {
        return datosClub;
    }

    public class DatosClub implements Serializable {

        private UUID idClub;

        private Horario rangoHorario;

        public DatosClub(UUID idClub, Horario rangoHorario) {

            this.idClub = idClub;
            this.rangoHorario = rangoHorario;
        }

        public String getIdClub() {
            return idClub.toString();
        }

        public Horario getRangoHorario() {
            return rangoHorario;
        }
    }

}
