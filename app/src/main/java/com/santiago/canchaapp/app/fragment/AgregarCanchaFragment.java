package com.santiago.canchaapp.app.fragment;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.santiago.canchaapp.R;

import java.io.Serializable;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AgregarCanchaFragment extends Fragment {

    @BindView(R.id.btnContinuar)
    public Button continuar;

    public static AgregarCanchaFragment nuevaInstancia() {

        /* Por si hay que agregar el link del club.
        Bundle args = new Bundle();
        args.putSerializable(ARG_CANCHAS, (Serializable) datosDeCanchas());
        fragment.setArguments(args);*/

        return new AgregarCanchaFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_agregar_cancha, container, false);
        ButterKnife.bind(this, view);

        continuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirFragment();
            }
        });

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Agregar cancha");

        return view;
    }

    private void abrirFragment() {
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.content_frame, new AgregarCanchaFragment())
                .addToBackStack(null)
                .commit();
    }

}
