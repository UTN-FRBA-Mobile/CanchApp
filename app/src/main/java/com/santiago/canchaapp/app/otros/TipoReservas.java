package com.santiago.canchaapp.app.otros;

import com.santiago.canchaapp.dominio.EstadoReserva;

import static com.santiago.canchaapp.dominio.EstadoReserva.APROBADA;
import static com.santiago.canchaapp.dominio.EstadoReserva.CANCELADA;
import static com.santiago.canchaapp.dominio.EstadoReserva.PENDIENTE;

public enum TipoReservas {
    PENDIENTES("Pendientes"), APROBADAS("Aprobadas"), CANCELADAS("Canceladas");

    public String titulo;

    TipoReservas(String titulo) { this.titulo = titulo; }

    public static TipoReservas enPosicion(int pos) { return TipoReservas.values()[pos]; }

    public EstadoReserva toEstado() {
        switch (this) {
            case PENDIENTES: return PENDIENTE;
            case APROBADAS: return APROBADA;
            case CANCELADAS: return CANCELADA;
        }
        return null;
    }

}