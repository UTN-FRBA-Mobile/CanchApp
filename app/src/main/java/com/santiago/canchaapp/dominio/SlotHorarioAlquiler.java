package com.santiago.canchaapp.dominio;

import static com.santiago.canchaapp.dominio.EstadoReserva.PENDIENTE;

public class SlotHorarioAlquiler {

    private Horario horario;

    private Alquiler alquiler;

    public SlotHorarioAlquiler(Horario horario, Alquiler alquiler) {
        this.horario = horario;
        this.alquiler = alquiler;
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
