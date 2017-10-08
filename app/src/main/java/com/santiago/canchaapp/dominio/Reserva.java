package com.santiago.canchaapp.dominio;

import java.io.Serializable;

public class Reserva implements Serializable {

    private Club club;

    private TipoCancha tipoCancha;

    private String fecha;

    private Horario horario;

    private EstadoReserva estado;

    private String motivoCancelacion;

    private String usuario;

    public Reserva(Club club, TipoCancha tipoCancha, String fecha, Horario horario, EstadoReserva estado, String motivoCancelacion, String usuario) {
        this.club = club;
        this.tipoCancha = tipoCancha;
        this.fecha = fecha;
        this.horario = horario;
        this.estado = estado;
        this.motivoCancelacion = motivoCancelacion;
        this.usuario = usuario;
    }

    public Reserva(Club club, TipoCancha tipoCancha, String fecha, Horario horario, EstadoReserva estado, String motivoCancelacion) {
        this(club, tipoCancha, fecha, horario, estado, motivoCancelacion, null);
    }

    public Reserva(Club club, TipoCancha tipoCancha, String fecha, Horario horario, EstadoReserva estado) {
        this(club, tipoCancha, fecha, horario, estado, null);
    }

    public TipoCancha getTipoCancha() {
        return tipoCancha;
    }

    public String getFecha() {
        return fecha;
    }

    public Horario getHorario() {
        return horario;
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

    public String getUsuario() {
        return usuario;
    }

}
