package com.santiago.canchaapp.dominio;

import java.util.Objects;
import java.util.UUID;

/**
 * Created by sleira on 6/10/17.
 */

public class Usuario {

    private String uid;
    private boolean esDuenio;
    private String nombre;
    private String email;
    private String idClub;
    private Horario horarioClub; // denormalizado

    public Usuario() { }

    public Usuario(String uid, boolean esDuenio, String nombre, String email){
        this.uid = uid;
        this.esDuenio = esDuenio;
        this.nombre = nombre;
        this.email = email;
        this.idClub = "";
        this.horarioClub = null;
    }

    public String getIdClub() { return idClub; }
    public Horario getHorarioClub(){
        return horarioClub;
    }
    public String getUid() { return uid; }
    public Boolean getEsDuenio() { return esDuenio; }
    public String getNombre() { return nombre; }
    public String getEmail() { return email; }
    public void setIdClub(String idClub){
        this.idClub = idClub;
    }
    public void setHorarioClub(Horario horario) {
        this.horarioClub = horario;
    }
    public Boolean esMiClub(String idClub){
        return Objects.equals(this.idClub, idClub);
    }

    public boolean tieneClub() {
        return idClub != null && !idClub.isEmpty();
    }
}
