package com.santiago.canchaapp.dominio;

import java.io.Serializable;

public class Reserva implements Serializable {

    private Club club;

    private TipoPartido tipoPartido;

    private String fecha;

    private int hora;

    public Reserva(Club club, TipoPartido tipoPartido, String fecha, int hora) {
        this.club = club;
        this.tipoPartido = tipoPartido;
        this.fecha = fecha;
        this.hora = hora;
    }

    public TipoPartido getTipoPartido() {
        return tipoPartido;
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
