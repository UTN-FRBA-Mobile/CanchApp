package com.santiago.canchaapp.dominio;

import com.santiago.canchaapp.app.otros.DateUtils;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import static com.santiago.canchaapp.app.otros.DateUtils.dateToString;

public class Reserva implements Serializable {

    private String uuid;

    private String fecha;

    private int hora;

    private EstadoReserva estado;

    private String idUsuario;

    private String idCancha;

    private String idClub;

    private String idAlquiler;

    // Datos denormalizados
    private String nombreUsuario;

    private String nombreCancha;

    private TipoCancha tipoCancha;

    private String nombreClub;

    private String direccionClub;
    //

    public Reserva(UUID uuid, Usuario usuario, Cancha cancha, Club club, Date fecha, Horario horario, EstadoReserva estado, UUID idAlquiler) {
        this.uuid = uuid.toString();
        this.fecha = dateToString(fecha);
        this.hora = horario.getDesde();
        this.estado = estado;
        this.idUsuario = usuario.getUid();
        this.nombreUsuario = usuario.getNombre();
        this.idClub = club.getUuid();
        this.nombreClub = club.getNombre();
        this.direccionClub = club.getDireccion();
        this.idCancha = cancha.getUuid();
        this.nombreCancha = cancha.getNombre();
        this.tipoCancha = cancha.getTipoCancha();
        this.idAlquiler = idAlquiler.toString();
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

    public String getIdCancha() {
        return idCancha;
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

    public String getNombreClub() {
        return nombreClub;
    }

    public String getDireccionClub() {
        return direccionClub;
    }

    public String getIdAlquiler() {
        return idAlquiler;
    }

}
