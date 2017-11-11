package com.santiago.canchaapp.dominio;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import static com.santiago.canchaapp.app.otros.DateUtils.dateToString;
import static com.santiago.canchaapp.dominio.EstadoReserva.PENDIENTE;

public class Alquiler implements Serializable {

    private String uuid;

    private String fecha;

    private int hora;

    private EstadoReserva estado;

    private String idReserva;

    // Datos denormalizados para simplificar queries
    private String idUsuario;

    private String idClub;

    private String nombreUsuario;

    private String nombreCancha;

    private TipoCancha tipoCancha;

    private String idCancha;

    public Alquiler() { }

    public Alquiler(UUID uuid, Date fecha, Horario horario, String idUsuario, String nombreUsuario,
                    Cancha cancha, EstadoReserva estado, UUID idReserva) {
        this.uuid = uuid.toString();
        this.fecha = dateToString(fecha);
        this.hora = horario.getDesde();
        this.idUsuario = idUsuario != null ? idUsuario : null;
        this.nombreUsuario = nombreUsuario;
        this.nombreCancha = cancha.getNombre();
        this.tipoCancha = cancha.getTipoCancha();
        this.idCancha = cancha.getUuid();
        this.idClub = cancha.getIdClub();
        this.estado = estado;
        this.idReserva = idReserva != null ? idReserva.toString() : null;
    }

    public boolean esUsuarioRegistrado() {
        return idUsuario != null && !idUsuario.isEmpty();
    }

    public boolean alquiladaPorUsuario() {
        return idUsuario != null;
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

    public String getIdReserva() {
        return idReserva;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public String getIdClub() {
        return idClub;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public String getNombreCancha() {
        return nombreCancha;
    }

    public TipoCancha getTipoCancha() {
        return tipoCancha;
    }

    public String getIdCancha() {
        return idCancha;
    }
}
