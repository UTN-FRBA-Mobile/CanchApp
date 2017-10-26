package com.santiago.canchaapp.dominio;

import android.provider.ContactsContract;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.Date;

import static com.santiago.canchaapp.app.otros.DateUtils.dateToString;
import static com.santiago.canchaapp.app.otros.DateUtils.dateToStringtoSave;

public class DataBase {

    private static DataBase instancia = null;
    private static DatabaseReference mDatabase;
    private static String keyUsuarios = "usuarios";
    private static String keyClubes = "clubes";
    private static String keyAlquileres = "alquileres";
    private static String keyReservas = "reservas";

    private DataBase() {
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    public static DataBase getInstancia(){
        instancia = instancia == null ? new DataBase() : instancia;
        return instancia;
    }

    public Usuario insertUser(FirebaseUser user, boolean esDuenio){
        Usuario usuario = new Usuario(user.getUid(), esDuenio, user.getDisplayName(), user.getEmail());
        getReferenceUser(usuario.getUid()).setValue(usuario);
        return usuario;
    }

    public DatabaseReference getReferenceUser(String uid){
        return mDatabase.child(keyUsuarios).child(uid);
    }

    public DatabaseReference getReferenceClub(String idClub){
        return getReferenceClubes().child(idClub);
    }

    public DatabaseReference getReferenceClubes(){
        return mDatabase.child(keyClubes);
    }

    public DatabaseReference getReferenceIdClubUser(String uid){
        return getReferenceUser(uid).child("idClub");
    }

    public void insertClub(Usuario usuario, Club club){
        getReferenceIdClubUser(usuario.getUid()).setValue(club.getUuid());
        getReferenceClub(club.getUuid()).setValue(club);
    }

    // obtiene alquileres en /alquileres/:idClub/:idCancha/:fecha
    public Query getReferenceAlquileres(String idClub, String idCancha, Date fecha) {
        return mDatabase.child(keyAlquileres).child(idClub).child(idCancha).child(dateToStringtoSave(fecha));
    }

    // inserta alquiler en /alquileres/:idClub/:idCancha/:fecha
    public void insertAlquiler(String idClub, String idCancha, Date fecha, Alquiler alquiler) {
        mDatabase.child(keyAlquileres).child(idClub).child(idCancha)
                .child(dateToStringtoSave(fecha)).child(alquiler.getUuid()).setValue(alquiler);
    }

    // actualiza el estado de /alquileres/:idClub/:idCancha/:fecha/:idAlquiler
    public void updateEstadoAlquiler(String idClub, String idCancha, Date fecha, String idAlquiler, EstadoReserva nuevoEstado) {
        mDatabase.child(keyAlquileres).child(idClub).child(idCancha)
                .child(dateToStringtoSave(fecha)).child(idAlquiler).child("estado").setValue(nuevoEstado);
    }

    // inserta reserva en /reservas/:idUsuario/:fecha/:idReserva
    public void insertReserva(String idUsuario, Date fecha, Reserva reserva) {
        mDatabase.child(keyReservas).child(idUsuario).child(dateToStringtoSave(fecha))
                .child(reserva.getUuid()).setValue(reserva);
    }

    // actualiza el estado de /reservas/:idClub/:fecha/:idReserva
    public void updateEstadoReserva(String idUsuario, Date fecha, String idReserva, EstadoReserva nuevoEstado) {
        mDatabase.child(keyReservas).child(idUsuario).child(dateToStringtoSave(fecha))
                .child(idReserva).child("estado").setValue(nuevoEstado);
    }

}
