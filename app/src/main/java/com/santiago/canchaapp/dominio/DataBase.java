package com.santiago.canchaapp.dominio;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.Date;

import static com.santiago.canchaapp.app.otros.DateUtils.dateToString;

public class DataBase {

    private static DataBase instancia = null;
    private static DatabaseReference mDatabase;
    private static String keyUsuarios = "usuarios";
    private static String keyClubes = "clubes";
    private static String keyAlquileres = "alquileres";

    private DataBase() {
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    public static DataBase getInstancia(){
        instancia = instancia == null ? new DataBase() : instancia;
        return instancia;
    }

    public Usuario insertUser(FirebaseUser user, boolean esDuenio){
        Usuario usuario = new Usuario(user.getUid(), esDuenio, user.getDisplayName(), user.getEmail());
        getReferenceUser(user.getUid()).setValue(usuario);
        return usuario;
    }

    public DatabaseReference getReferenceUser(String uid){
        return mDatabase.child(keyUsuarios).child(uid);
    }

    public DatabaseReference getReferenceClub(String idClub){
        return mDatabase.child(keyClubes).child(idClub);
    }

    public DatabaseReference getReferenceIdClubUser(String uid){
        return getReferenceUser(uid).child("idClub");
    }

    public void insertClub(FirebaseUser user, Club club){
        getReferenceIdClubUser(user.getUid()).setValue(club.getUuid());
        getReferenceClub(club.getUuid()).setValue(club);
    }

    // obtiene alquileres en /alquileres/:idClub/:idCancha/:fecha
    public Query getReferenceAlquileres(String idClub, String idCancha, Date fecha) {
        return mDatabase.child(keyAlquileres).child(idClub).child(idCancha).child(dateToString(fecha));
    }

    // inserta alquiler en /alquileres/:idClub/:idCancha/:fecha
    public void insertAlquiler(String idClub, String idCancha, Date fecha, Alquiler alquiler) {
        mDatabase.child(keyAlquileres).child(idClub).child(idCancha)
                .child(dateToString(fecha)).child(alquiler.getUuid()).setValue(alquiler);
    }

    // actualiza el estado de /alquileres/:idClub/:idCancha/:fecha/:idAlquiler
    public void updateEstadoAlquiler(String idClub, String idCancha, Date fecha, String idAlquiler, EstadoReserva nuevoEstado) {
        mDatabase.child(keyAlquileres).child(idClub).child(idCancha)
                .child(dateToString(fecha)).child(idAlquiler).child("estado").setValue(nuevoEstado);
    }

}
