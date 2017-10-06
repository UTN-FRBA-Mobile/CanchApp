package com.santiago.canchaapp.dominio;

/**
 * Created by sleira on 6/10/17.
 */

public class Usuario {

    private String uId;
    private boolean tieneClub;
    private String nombre;
    private String email;

    public Usuario(String uId, boolean tieneClub, String nombre, String email){
        this.uId = uId;
        this.tieneClub = tieneClub;
        this.nombre = nombre;
        this.email = email;
    }

    public String getUId(){return uId;}
    public Boolean getTieneClub(){return tieneClub;}
    public String getNombre(){return nombre;}
    public String getEmail(){return email;}

}
