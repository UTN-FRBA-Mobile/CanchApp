package com.santiago.canchaapp.dominio;

import java.io.Serializable;
import java.util.Date;

import static com.santiago.canchaapp.dominio.EstadoReserva.PENDIENTE;

public class SlotReserva implements Serializable {

    private Horario horario;

    private Reserva reserva;

    public SlotReserva(Horario horario, Reserva reserva) {
        this.horario = horario;
        this.reserva = reserva;
    }

    public SlotReserva(Date fecha, Horario horario) {
        this(horario, null);
    }

    public Horario getHorario() {
        return horario;
    }

    public Reserva getReserva() {
        return reserva;
    }

    public boolean estaLibre() {
        return reserva == null;
    }

    public boolean estaPendienteDeAprobacion() {
        return reserva.getEstado() == PENDIENTE;
    }

}
