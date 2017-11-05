package com.santiago.canchaapp.dominio;

import android.net.Uri;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class Storage {

    private static Storage instancia = null;

    private StorageReference storageReference = null;

    private static String pathFotosCancha = "imagenes/canchas";

    public static Storage getInstancia(){
        instancia = instancia == null ? new Storage() : instancia;
        return instancia;
    }

    private StorageReference ref() {
        storageReference = storageReference == null ? FirebaseStorage.getInstance().getReference() : storageReference;
        return storageReference;
    }

    // Sube foto en /imagenes/canchas/:idClub/:idCancha
    public UploadTask subirFotoCancha(String idClub, String idCancha, Uri pathFoto, int ubicacion) {
        StorageReference riversRef = ref().child(pathFotosCancha)
                .child(idClub).child(idCancha).child("foto_" + ubicacion);
        return riversRef.putFile(pathFoto);
    }

}
