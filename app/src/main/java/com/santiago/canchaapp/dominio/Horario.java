package com.santiago.canchaapp.dominio;

import android.os.Parcelable;

import java.io.Serializable;

public class Horario implements Serializable {

    public static int HORA_MIN = 7;

    public static int HORA_MAX = 24;

    private int desde;

    private int hasta;

    public Horario(int desde, int hasta) {
        if (desde < HORA_MIN || hasta > HORA_MAX || desde >= hasta) {
            throw new RuntimeException("El rango es incorrecto");
        }
        this.desde = desde;
        this.hasta = hasta;
    }

    public Horario() {}

    public int getDesde() {
        return desde;
    }

    public int getHasta() {
        return hasta;
    }

    public static Horario horaDesde(int hora) {
        return new Horario(hora, hora +1);
    }

    public boolean contiene(int hora) {
        return hora >= desde && hora <= hasta;
    }

    @Override
    public String toString() {
        return desde + "-" + hasta + "hs";
    }

}
