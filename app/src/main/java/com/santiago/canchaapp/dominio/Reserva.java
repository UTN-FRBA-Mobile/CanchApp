package com.santiago.canchaapp.dominio;

import java.io.Serializable;

public class Reserva implements Serializable {

    private Club club;

    private TipoCancha tipoCancha;

    private String fecha;

    private int hora;

    public Reserva(Club club, TipoCancha tipoCancha, String fecha, int hora) {
        this.club = club;
        this.tipoCancha = tipoCancha;
        this.fecha = fecha;
        this.hora = hora;
    }

    public TipoCancha getTipoCancha() {
        return tipoCancha;
    }

    public String getFecha() {
        return fecha;
    }

    public int getHora() {
        return hora;
    }

    public String getNombreClub() {
        return club.getNombre();
    }

    public String getDireccion() {
        return club.getDireccion();
    }

}
