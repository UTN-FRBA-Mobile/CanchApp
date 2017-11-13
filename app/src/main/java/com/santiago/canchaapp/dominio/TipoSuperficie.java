package com.santiago.canchaapp.dominio;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public enum TipoSuperficie {
    BALDOSA("Baldosa"), PASTO("Césped"), SINTETICO("Sintético"), CAUCHO("Caucho");

    public String nombre;

    TipoSuperficie(String nombre){
        this.nombre = nombre;
    }

    public static List<String> nombres() {
        List<String> nombres = new ArrayList<>();
        for (TipoSuperficie elem : TipoSuperficie.values()) {
            nombres.add(elem.nombre);
        }
        return nombres;
    }

    public static TipoSuperficie deNombre(String nombre) {
        for (TipoSuperficie elem : TipoSuperficie.values()) {
            if (Objects.equals(elem.nombre, nombre)) {
                return elem;
            }
        }
        throw new RuntimeException("No existe el elemento");
    }

}
