package com.santiago.canchaapp.dominio;

import java.io.Serializable;

public class Club implements Serializable {

    private String nombre;

    private String direccion;

    private Horario rangoHorario;

    public Club(String nombre, String direccion) {
        this(nombre, direccion, null);
    }

    public Club(String nombre, String direccion, Horario rangoHorario) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.rangoHorario = rangoHorario;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public Horario getRangoHorario() {
        return rangoHorario;
    }

}
