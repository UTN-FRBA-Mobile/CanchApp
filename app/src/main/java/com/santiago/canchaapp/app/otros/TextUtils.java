package com.santiago.canchaapp.app.otros;

import android.support.design.widget.TextInputLayout;
import android.util.Patterns;

public class TextUtils {

    public static boolean isEmpty(String text,TextInputLayout til) {
        if (isEmpty(text) && til != null) {
            til.setError("El campo no puede estar vacío");
        } else {
            til.setError(null);
        }
        return isEmpty(text);
    }

    public static boolean isEmpty(String text) {
        return text == null || text.replace(" ", "").isEmpty();
    }

    /*public static boolean isEmail(String text) {
        return Patterns.EMAIL_ADDRESS.matcher(text).matches();
    }*/

    public static String textoOVacio(String texto) {
        return (texto == null || texto.isEmpty()) ? "" : texto;
    }

}
