package com.santiago.canchaapp.dominio;

public enum TipoSuperficie {
    BALDOSA("baldosa"), ALFOMBRA("alfombra"), PASTO("pasto"), POLVO_LADRILLO("polvo de ladrillo");

    public String nombre;

    TipoSuperficie(String nombre){
        this.nombre = nombre;
    }
}
