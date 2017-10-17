package com.santiago.canchaapp.dominio;

public enum TipoCancha {
    FUTBOL5("Fútbol 5"), FUTBOL7("Fútbol 7"), TENIS("Tenis"), BASQUET("Basquet");

    public String nombre;

    TipoCancha(String nombre){
        this.nombre = nombre;
    }
}
