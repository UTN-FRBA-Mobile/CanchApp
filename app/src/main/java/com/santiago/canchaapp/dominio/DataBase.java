package com.santiago.canchaapp.dominio;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import static com.santiago.canchaapp.app.otros.DateUtils.dateToStringtoSave;

public class DataBase {

    private static DataBase instancia = null;
    private static DatabaseReference mDatabase;
    private static String keyUsuarios = "usuarios";
    private static String keyClubes = "clubes";
    private static String keyCanchas = "canchas";
    private static String keyAlquileres = "alquileres";
    private static String keyReservas = "reservas";
    private static String keyAlquileresPorClub = "alquileresPorUsuario";

    private DataBase() {
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    public static DataBase getInstancia(){
        instancia = instancia == null ? new DataBase() : instancia;
        return instancia;
    }

    //usuarios/:idUsuario
    public DatabaseReference getReferenceUser(String uid){ return mDatabase.child(keyUsuarios).child(uid); }

    //usuarios/:idUsuario/idClub
    public DatabaseReference getReferenceIdClubUser(String uid){ return getReferenceUser(uid).child("idClub"); }

    //usuarios/:idUsuario/horarioClub
    public DatabaseReference getReferenceHorarioClubUser(String uid){ return getReferenceUser(uid).child("horarioClub"); }


    //reservas/:idUsuario
    public DatabaseReference getReferenceReservasUser(String idUsuario) { return mDatabase.child(keyReservas).child(idUsuario); }

    //reservas/:idUsuario/:idReserva
    public DatabaseReference getReferenceReserva(String idUsuario, String idReserva) { return getReferenceReservasUser(idUsuario).child(idReserva); }

    //reservas/:idUsuario/ con :fecha >= fechaActual
    public Query getReferenceReservasActuales(String idUsuario, Date fechaActual) { return getReferenceReservasUser(idUsuario).orderByChild("fecha").startAt(dateToStringtoSave(fechaActual)); }

    //clubes
    public DatabaseReference getReferenceClubes() {
        return mDatabase.child(keyClubes);
    }

    //clubes/:idClub
    public DatabaseReference getReferenceClub(String idClub){ return getReferenceClubes().child(idClub); }

    //alquileres/:idClub/:idCancha/:fecha
    public Query getReferenceAlquileres(String idClub, String idCancha, Date fecha) { return mDatabase.child(keyAlquileres).child(idClub).child(idCancha).child(dateToStringtoSave(fecha)); }

    //alquileresPorClub/:idClub
    public Query getReferenceAlquileresPorClub(String idClub) { return mDatabase.child(keyAlquileresPorClub).child(idClub); }

    //alquileresPorUsuario/:idClub/:idAlquiler
    public DatabaseReference getReferenceAlquilerPorClub(String idClub, String idAlquiler) { return ((DatabaseReference) getReferenceAlquileresPorClub(idClub)).child(idAlquiler); }

    //canchas
    public DatabaseReference getReferenceCanchas() {
        return mDatabase.child(keyCanchas);
    }

    //canchas/:idClub
    public DatabaseReference getReferenceCanchasClub(String idClub) {
        return getReferenceCanchas().child(idClub);
    }

    //usuarios/:idUsuario
    public Usuario insertUser(FirebaseUser user, boolean esDuenio){
        Usuario usuario = new Usuario(user.getUid(), esDuenio, user.getDisplayName(), user.getEmail());
        getReferenceUser(usuario.getUid()).setValue(usuario);
        return usuario;
    }

    //reservas/:idUsuario/:idReserva
    public void insertReserva(String idUsuario, Reserva reserva) { getReferenceReserva(idUsuario, reserva.getUuid()).setValue(reserva); }

    //alquileres/:idClub/:idCancha/:fecha
    public void insertAlquiler(String idClub, String idCancha, Date fecha, Alquiler alquiler) { ((DatabaseReference) getReferenceAlquileres(idClub, idCancha, fecha)).child(alquiler.getUuid()).setValue(alquiler); }

    //alquileresPorUsuario/:idUsuario/:idAlquiler
    public void insertAlquilerPorClub(String idClub, Alquiler alquiler){ getReferenceAlquilerPorClub(idClub, alquiler.getUuid()).setValue(alquiler); }

    //clubes/:idClub
    public void insertClub(Usuario usuario, Club club){
        getReferenceIdClubUser(usuario.getUid()).setValue(club.getUuid());
        getReferenceHorarioClubUser(usuario.getUid()).setValue(club.getRangoHorario());
        getReferenceClub(club.getUuid()).setValue(club);
    }

    ///alquileres/:idClub/:idCancha/:fecha/:idAlquiler
    public void updateEstadoAlquiler(String idClub, String idCancha, Date fecha, String idAlquiler, EstadoReserva nuevoEstado) { ((DatabaseReference) getReferenceAlquileres(idClub, idCancha, fecha)).child(idAlquiler).child("estado").setValue(nuevoEstado); }

    ///alquileresPorClub/:idClub/:id:idAlquiler
    public void updateEstadoAlquilerPorClub(String idClub, String idAlquiler, EstadoReserva nuevoEstado) { getReferenceAlquilerPorClub(idClub, idAlquiler).child("estado").setValue(nuevoEstado); }

    // actualiza el estado de /reservas/:idClub/:idReserva
    public void updateEstadoReserva(String idUsuario, String idReserva, EstadoReserva nuevoEstado) { getReferenceReserva(idUsuario, idReserva).child("estado").setValue(nuevoEstado); }

    //canchas/:idClub/:idCancha
    public void insertCancha(String idClub, Cancha cancha) {
        getReferenceCanchas().child(idClub).child(cancha.getUuid()).setValue(cancha);
    }

    public boolean isOnline(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isAvailable() && networkInfo.isConnected();
    }

    public void setTimeoutFirebase(final DatabaseReference referenceUser, final ValueEventListener valueEventListener, final AppCompatActivity activity, final Runnable task) {
        referenceUser.addListenerForSingleValueEvent(valueEventListener);
        final Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                timer.cancel();
                referenceUser.removeEventListener(valueEventListener);
                activity.runOnUiThread(task);
            }
        };
        timer.schedule(timerTask, 30000L);
    }

}
