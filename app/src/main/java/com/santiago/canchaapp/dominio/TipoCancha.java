package com.santiago.canchaapp.dominio;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public enum TipoCancha {
    FUTBOL5("Fútbol 5"), FUTBOL7("Fútbol 7"), TENIS("Tenis"), BASQUET("Basquet");

    public String nombre;

    TipoCancha(String nombre){
        this.nombre = nombre;
    }

    public static List<String> nombres() {
        List<String> nombres = new ArrayList<>();
        for (TipoCancha elem : TipoCancha.values()) {
            nombres.add(elem.nombre);
        }
        return nombres;
    }

    public static TipoCancha deNombre(String nombre) {
        for (TipoCancha elem : TipoCancha.values()) {
            if (Objects.equals(elem.nombre, nombre)) {
                return elem;
            }
        }
        throw new RuntimeException("No existe el elemento");
    }

}
