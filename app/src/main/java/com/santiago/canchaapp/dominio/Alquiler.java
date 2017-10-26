package com.santiago.canchaapp.dominio;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import static com.santiago.canchaapp.app.otros.DateUtils.dateToString;

public class Alquiler implements Serializable {

    private String uuid;

    private String fecha;

    private int hora;

    private EstadoReserva estado;

    private String idReserva;

    // Datos denormalizados para simplificar queries
    private String idUsuario;

    private String nombreUsuario;

    private String nombreCancha;

    private TipoCancha tipoCancha;
    //

    public Alquiler() { }

    public Alquiler(UUID uuid, Date fecha, Horario horario, String idUsuario, String nombreUsuario,
                    String nombreCancha, TipoCancha tipoCancha, EstadoReserva estado, UUID idReserva) {
        this.uuid = uuid.toString();
        this.fecha = dateToString(fecha);
        this.hora = horario.getDesde();
        this.idUsuario = idUsuario != null ? idUsuario : null;
        this.nombreUsuario = nombreUsuario;
        this.nombreCancha = nombreCancha;
        this.tipoCancha = tipoCancha;
        this.estado = estado;
        this.idReserva = idReserva != null ? idReserva.toString() : null;
    }

    public String getUuid() {
        return uuid;
    }

    public String getFecha() {
        return fecha;
    }

    public int getHora() {
        return hora;
    }

    public EstadoReserva getEstado() {
        return estado;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public boolean esUsuarioRegistrado() {
        return uuid != null;
    }

    public String getNombreCancha() {
        return nombreCancha;
    }

    public TipoCancha getTipoCancha() {
        return tipoCancha;
    }

    public String getIdReserva() {
        return idReserva;
    }

    public boolean alquiladaPorUsuario() {
        return idUsuario != null;
    }

}
