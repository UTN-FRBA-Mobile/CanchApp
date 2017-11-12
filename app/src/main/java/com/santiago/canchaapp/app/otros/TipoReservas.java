package com.santiago.canchaapp.app.otros;

import com.santiago.canchaapp.dominio.EstadoReserva;

import static com.santiago.canchaapp.dominio.EstadoReserva.APROBADA;
import static com.santiago.canchaapp.dominio.EstadoReserva.CANCELADA;
import static com.santiago.canchaapp.dominio.EstadoReserva.PENDIENTE;

public enum TipoReservas {
    PENDIENTES("Pendientes", "Pendientes"),
    APROBADAS("Aprobadas", "Aprobados"),
    CANCELADAS("Canceladas", "Cancelados");

    public String tituloReservas;

    public String tituloAlquileres;

    TipoReservas(String tituloReservas, String tituloAlquileres) {
        this.tituloAlquileres = tituloAlquileres;
        this.tituloReservas = tituloReservas;
    }

    public static String nombreEnMasculino(TipoReservas tipo){
        if(tipo == PENDIENTES)
            return "Pendientes";
        else if(tipo == APROBADAS)
            return "Aprobados";
        else
            return "Cancelados";

        //perdón por esta garcha
    }

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