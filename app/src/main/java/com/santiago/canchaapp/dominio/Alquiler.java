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

    // Datos denormalizados para simplificar queries
    private String idUsuario;

    private String nombreUsuario;

    private String nombreCancha;

    private TipoCancha tipoCancha;
    //

    public Alquiler() { }

    public Alquiler(UUID uuid, Date fecha, Horario horario, UUID idUsuario, String nombreUsuario,
                    String nombreCancha, TipoCancha tipoCancha, EstadoReserva estado) {
        this.uuid = uuid.toString();
        this.fecha = dateToString(fecha);
        this.hora = horario.getDesde();
        this.idUsuario = idUsuario != null ? idUsuario.toString() : null;
        this.nombreUsuario = nombreUsuario;
        this.nombreCancha = nombreCancha;
        this.tipoCancha = tipoCancha;
        this.estado = estado;
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

    public boolean alquiladaPorUsuario() {
        return idUsuario != null;
    }

}
