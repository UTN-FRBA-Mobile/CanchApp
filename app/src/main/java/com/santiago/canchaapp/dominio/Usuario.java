package com.santiago.canchaapp.dominio;

/**
 * Created by sleira on 6/10/17.
 */

public class Usuario {

    private String uid;
    private boolean esDuenio;
    private String nombre;
    private String email;

    public Usuario() { }

    public Usuario(String uid, boolean esDuenio, String nombre, String email){
        this.uid = uid;
        this.esDuenio = esDuenio;
        this.nombre = nombre;
        this.email = email;
    }

    public String getUid(){return uid;}
    public Boolean getEsDuenio(){return esDuenio;}
    public String getNombre(){return nombre;}
    public String getEmail(){return email;}

}
