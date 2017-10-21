package com.santiago.canchaapp.dominio;

import com.santiago.canchaapp.app.otros.DateUtils;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import static com.santiago.canchaapp.dominio.Horario.horaDesde;

public class Alquiler implements Serializable {

    private UUID uuid;

    private String fecha;

    private int hora;

    // Denormalizado para simplificar queries
    private DatosUsuario usuario;

    private DatosCancha cancha;

    private EstadoReserva estado;

    private String motivoCancelacion;

    public Alquiler(UUID uuid, Date fecha, Horario horario,
                    DatosUsuario usuario, DatosCancha cancha, EstadoReserva estado) {
        this.uuid = uuid;
        this.fecha = DateUtils.dateToString(fecha);
        this.hora = horario.getDesde();
        this.usuario = usuario;
        this.cancha = cancha;
        this.estado = estado;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getFecha() {
        return fecha;
    }

    public int getHora() {
        return hora;
    }

    public Horario getHorario() {
        return horaDesde(hora);
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

    public class DatosUsuario implements Serializable {

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

    public class DatosCancha implements Serializable {

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
