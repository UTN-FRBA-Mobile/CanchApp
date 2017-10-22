package com.santiago.canchaapp.servicios;

import com.santiago.canchaapp.dominio.Usuario;

public class Sesion {

    private static Sesion sesion;

    public static Sesion getInstancia() {
        if (sesion == null) {
            sesion = new Sesion();
        }
        return sesion;
    }

    private Usuario usuario;

    public void setDatosUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Usuario getUsuario() {
        return usuario;
    }

}
