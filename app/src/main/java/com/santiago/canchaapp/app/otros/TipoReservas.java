package com.santiago.canchaapp.app.otros;

public enum TipoReservas {
    APPROVED("Aprobadas"), CANCELLED("Canceladas"), PENDING("Pendientes");

    public String titulo;

    TipoReservas(String title) {
        this.titulo = title;
    }

    public static TipoReservas enPosicion(int pos) {
        return TipoReservas.values()[pos];
    }

}