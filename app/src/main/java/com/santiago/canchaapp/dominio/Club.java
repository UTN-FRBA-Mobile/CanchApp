package com.santiago.canchaapp.dominio;

import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Club implements Serializable {

    private String nombre;
    private String direccion;
    private Coordenada coordenadas;
    private String email;
    private String telefono;
    private Horario rangoHorario;
    private String uuid;
    private List<Cancha> canchas;
    public Club(String nombre, String direccion) {
        this(nombre, direccion, null);
    }

    public Club(String nombre, String direccion, Horario rangoHorario) {
        this(null, nombre, direccion, null, null, null, rangoHorario, null);
    }

    public Club() {}

    public Club(UUID uuid, String nombre, String direccion, LatLng coordenadas, String email, String telefono, Horario rangoHorario, List<Cancha> canchas) {
        this.uuid = uuid.toString();
        this.nombre = nombre;
        this.direccion = direccion;
        this.coordenadas = new Coordenada(coordenadas);
        this.email = email;
        this.telefono = telefono;
        this.rangoHorario = rangoHorario;
        this.canchas = (canchas == null ? new ArrayList<Cancha>() : canchas);
    }

    public String getNombre() {return nombre;}

    public String getDireccion() {return direccion;}

    public Coordenada getCoordenadas() {return coordenadas;}

    public String getEmail() {return email;}

    public String getTelefono() {return telefono;}

    public Horario getRangoHorario() {return rangoHorario;}

    public String getUuid() {return uuid.toString();}

    public List<Cancha> getCanchas() {
        canchas = canchas == null ? new ArrayList<Cancha>() : canchas;
        return canchas;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

}
