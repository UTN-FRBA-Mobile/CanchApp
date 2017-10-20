package com.santiago.canchaapp.dominio;

import com.santiago.canchaapp.app.otros.DateUtils;

import java.util.Date;
import java.util.UUID;

public class Alquiler {

    private UUID uuid;

    private Long fecha;

    private Horario horario;

    private DatosUsuario usuario;

    private DatosCancha cancha;

    private EstadoReserva estado;

    public Alquiler(UUID uuid, Long fecha, Horario horario,
                    DatosUsuario usuario, DatosCancha cancha, EstadoReserva estado) {
        this.uuid = uuid;
        this.fecha = fecha;
        this.horario = horario;
        this.usuario = usuario;
        this.cancha = cancha;
        this.estado = estado;
    }

    public UUID getUuid() {
        return uuid;
    }

    public Date getFecha() {
        return DateUtils.timestampToDate(fecha);
    }

    public Horario getHorario() {
        return horario;
    }

    public DatosUsuario getUsuario() {
        return usuario;
    }

    public DatosCancha getCancha() {
        return cancha;
    }

    public EstadoReserva getEstado() {
        return estado;
    }

    private class DatosUsuario {

        private UUID uuid;

        private String nombre;

        public DatosUsuario(UUID uuid, String nombre) {
            this.uuid = uuid;
            this.nombre = nombre;
        }

        public UUID getUuid() {
            return uuid;
        }

        public String getNombre() {
            return nombre;
        }

        public boolean esUsuarioRegistrado() {
            return uuid != null;
        }

    }

    private class DatosCancha {

        private UUID uuid;

        private String nombre;

        private TipoCancha tipo;

        public DatosCancha(String nombre, TipoCancha tipo) {
            this.nombre = nombre;
            this.tipo = tipo;
        }

        public String getNombre() {
            return nombre;
        }

        public TipoCancha getTipo() {
            return tipo;
        }
    }

}
