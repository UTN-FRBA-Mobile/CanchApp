package com.santiago.canchaapp.app.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.santiago.canchaapp.R;

public class ReservasCanchaFragment extends Fragment {

    public static ReservasCanchaFragment nuevaInstancia() {
        return new ReservasCanchaFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_reservas_cancha, container, false);
        cargarVista(rootView);
        return rootView;
    }

    private void cargarVista(View view) {

    }

}
