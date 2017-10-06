package com.santiago.canchaapp.app.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.santiago.canchaapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AgregarCanchaFragment extends Fragment {

    @BindView(R.id.btnContinuar)
    public Button continuar;

    public static AgregarCanchaFragment nuevaInstancia() {
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
