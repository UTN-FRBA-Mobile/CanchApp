package com.santiago.canchaapp.servicios;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class Preferencias {

    private static Preferencias preferencias;

    private static String NO_CONF_RESERVAS = "no_conf_reservas";

    public static Preferencias getInstancia() {
        if (preferencias == null) {
            preferencias = new Preferencias();
        }
        return preferencias;
    }

    public void deshabilitarConfirmacionReservas(Activity context) {
        setearPreferencia(context, NO_CONF_RESERVAS, true);
    }

    public boolean confirmacionHabilitada(Activity context) {
        return !valorPreferencia(context, NO_CONF_RESERVAS);
    }

    private SharedPreferences getPreferencias(Activity context) {
        return context.getPreferences(Context.MODE_PRIVATE);
    }

    private void setearPreferencia(Activity context, String preferencia, boolean valor) {
        SharedPreferences.Editor editor = getPreferencias(context).edit();
        editor.putBoolean(preferencia, valor);
        editor.commit();
    }

    private boolean valorPreferencia(Activity context, String preferencia) {
        return getPreferencias(context).getBoolean(NO_CONF_RESERVAS, false);
    }

}
