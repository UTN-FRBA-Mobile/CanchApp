package com.santiago.canchaapp.app.otros;

public enum TipoReservas {
    APROBADAS("Aprobadas"), CANCELADAS("Canceladas"), PENDIENTES("Pendientes");

    public String titulo;

    TipoReservas(String titulo) {
        this.titulo = titulo;
    }

    public static TipoReservas enPosicion(int pos) {
        return TipoReservas.values()[pos];
    }

}