package com.santiago.canchaapp.app.otros;

import android.util.Patterns;

public class TextUtils {

    public static boolean estaVacio(String texto) {
        return texto == null || texto.replace(" ", "").isEmpty();
    }

    public static boolean esUnEmail(String texto) {
        return Patterns.EMAIL_ADDRESS.matcher(texto).matches();
    }

}
