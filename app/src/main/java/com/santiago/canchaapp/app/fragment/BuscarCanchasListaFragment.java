package com.santiago.canchaapp.app.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.santiago.canchaapp.dominio.Club;
import com.santiago.canchaapp.servicios.Servidor;

import java.io.Serializable;
import java.util.List;

public class BuscarCanchasListaFragment extends Fragment {

    private static String ARG_CANCHAS = "canchas";

    public static BuscarCanchasListaFragment nuevaInstancia() {
        BuscarCanchasListaFragment fragment = new BuscarCanchasListaFragment();

        Bundle args = new Bundle();
        args.putSerializable(ARG_CANCHAS, (Serializable) datosDeClubes());
        fragment.setArguments(args);

        return fragment;
    }

    private static List<Club> datosDeClubes() {
        return Servidor.instancia().getClubes();
    }


}
