package com.santiago.canchaapp.dominio;

import java.io.Serializable;

public class Reserva implements Serializable {

    private Club club;

    private TipoCancha tipoCancha;

    private String fecha;

    private int hora;

    private EstadoReserva estado;

    private String motivoCancelacion;

    public Reserva(Club club, TipoCancha tipoCancha, String fecha, int hora, EstadoReserva estado, String motivoCancelacion) {
        this.club = club;
        this.tipoCancha = tipoCancha;
        this.fecha = fecha;
        this.hora = hora;
        this.estado = estado;
        this.motivoCancelacion = motivoCancelacion;
    }

    public Reserva(Club club, TipoCancha tipoCancha, String fecha, int hora, EstadoReserva estado) {
        this(club, tipoCancha, fecha, hora, estado, null);
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

    public EstadoReserva getEstado() {
        return estado;
    }

    public String getMotivoCancelacion() {
        return motivoCancelacion;
    }

}
