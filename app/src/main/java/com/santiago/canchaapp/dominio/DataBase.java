package com.santiago.canchaapp.dominio;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.santiago.canchaapp.app.otros.DateUtils;

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

    public void insertUser(FirebaseUser user, boolean esDuenio){
        Usuario usuario = new Usuario(user.getUid(), esDuenio, user.getDisplayName(), user.getEmail());
        mDatabase.child(keyUsuarios).child(user.getUid()).setValue(usuario);
    }

    public DatabaseReference getReferenceUser(String uId){
        return mDatabase.child(keyUsuarios).child(uId);
    }

    public DatabaseReference getReferenceIdClubUser(String uId){
        return mDatabase.child(keyUsuarios).child(uId).child("idClub");
    }

    public void insertClub(FirebaseUser user, Club club){
        mDatabase.child(keyUsuarios).child(user.getUid()).child("idClub").setValue(club.getUuid());
        mDatabase.child(keyClubes).child(club.getUuid()).setValue(club);
    }

    // obtiene alquileres en /alquileres/:idClub/:idCancha/:fecha ordenados por hora
    public Query getReferenceAlquileres(String idClub, String idCancha, Date fecha) {
        return mDatabase.child(keyAlquileres).child(idClub).child(idCancha)
                .child(dateToString(fecha)).orderByChild("hora");
    }

    // inserta alquiler en /alquileres/:idClub/:idCancha/:fecha
    public void insertAlquiler(String idClub, String idCancha, Date fecha, Alquiler alquiler) {
        mDatabase.child(keyAlquileres).child(idClub).child(idCancha)
                .child(dateToString(fecha)).child(alquiler.getUuid()).setValue(alquiler);
    }

}
