package com.santiago.canchaapp.dominio;

public enum TipoPartido {
    FUTBOL5("Fútbol 5"), FUTBOL7("Fútbol 7"), TENIS("Tenis");

    public String nombre;

    TipoPartido(String nombre){
        this.nombre = nombre;
    }
}
