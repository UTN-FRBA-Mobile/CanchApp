package com.santiago.canchaapp.app.otros;

public enum TipoReservas {
    PENDIENTES("Pendientes"), APROBADAS("Aprobadas"), CANCELADAS("Canceladas");

    public String titulo;

    TipoReservas(String titulo) {
        this.titulo = titulo;
    }

    public static TipoReservas enPosicion(int pos) {
        return TipoReservas.values()[pos];
    }

}