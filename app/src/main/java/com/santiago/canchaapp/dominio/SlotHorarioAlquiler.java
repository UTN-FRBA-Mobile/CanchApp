package com.santiago.canchaapp.dominio;

import java.util.Date;

import static com.santiago.canchaapp.dominio.EstadoReserva.PENDIENTE;

public class SlotHorarioAlquiler {

    private Horario horario;

    private Alquiler alquiler;

    public SlotHorarioAlquiler(Horario horario, Alquiler alquiler) {
        this.horario = horario;
        this.alquiler = alquiler;
    }

    public SlotHorarioAlquiler(Date fecha, Horario horario) {
        this(horario, null);
    }

    public Horario getHorario() {
        return horario;
    }

    public Alquiler getAlquiler() {
        return alquiler;
    }

    public boolean estaLibre() {
        return alquiler == null;
    }

    public boolean estaPendienteDeAprobacion() {
        return alquiler.getEstado() == PENDIENTE;
    }

}
