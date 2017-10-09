package com.santiago.canchaapp.dominio;

/**
 * Created by sleira on 6/10/17.
 */

public class Usuario {

    private String uId;
    private boolean esDuenio;
    private String nombre;
    private String email;

    public Usuario(String uId, boolean esDuenio, String nombre, String email){
        this.uId = uId;
        this.esDuenio = esDuenio;
        this.nombre = nombre;
        this.email = email;
    }

    public String getUId(){return uId;}
    public Boolean getEsDuenio(){return esDuenio;}
    public String getNombre(){return nombre;}
    public String getEmail(){return email;}

}
