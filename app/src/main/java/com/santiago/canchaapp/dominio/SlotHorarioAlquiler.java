package com.santiago.canchaapp.dominio;

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

}
