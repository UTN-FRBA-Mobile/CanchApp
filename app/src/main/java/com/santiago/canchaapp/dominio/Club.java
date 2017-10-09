package com.santiago.canchaapp.dominio;

import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;

public class Club implements Serializable {

    private String nombre;
    private String direccion;
    private LatLng coordenadas;
    private String email;
    private String telefono;
    private Horario rangoHorario;

    public Club(String nombre, String direccion) {
        this(nombre, direccion, null);
    }

    public Club(String nombre, String direccion, Horario rangoHorario) {
        this(nombre, direccion, null, null, null, rangoHorario);
    }

    public Club(String nombre, String direccion, LatLng coordenadas, String email, String telefono, Horario rangoHorario) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.coordenadas = coordenadas;
        this.email = email;
        this.telefono = telefono;
        this.rangoHorario = rangoHorario;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public LatLng getCoordenadas() {
        return coordenadas;
    }

    public String getEmail() {
        return email;
    }

    public String getTelefono() {
        return telefono;
    }

    public Horario getRangoHorario() {
        return rangoHorario;
    }
}
