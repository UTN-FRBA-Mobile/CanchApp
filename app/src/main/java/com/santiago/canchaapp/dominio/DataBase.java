package com.santiago.canchaapp.dominio;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DataBase {

    private static DataBase instancia = null;
    private static DatabaseReference mDatabase;
    private static String keyUsuarios = "usuarios";
    private static String keyClubes = "clubes";

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

}
