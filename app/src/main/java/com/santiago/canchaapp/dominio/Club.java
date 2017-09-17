package com.santiago.canchaapp.dominio;

import java.io.Serializable;

public class Club implements Serializable {

    private String nombre;

    private String direccion;

    public Club(String nombre, String direccion) {
        this.nombre = nombre;
        this.direccion = direccion;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDireccion() {
        return direccion;
    }
}
