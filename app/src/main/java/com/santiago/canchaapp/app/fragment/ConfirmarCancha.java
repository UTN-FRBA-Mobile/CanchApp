package com.santiago.canchaapp.app.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.santiago.canchaapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.santiago.canchaapp.app.otros.FragmentTags.MIS_CANCHAS;

public class ConfirmarCancha extends Fragment {

    @BindView(R.id.btnContinuar)
    public Button continuar;

    public static ConfirmarCancha nuevaInstancia() {
        ConfirmarCancha fragment = new ConfirmarCancha();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_confirmar_cancha, container, false);
        ButterKnife.bind(this, view);

        continuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirFragmentSiguiente();
            }
        });

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Confirmar registro de cancha");

        return view;
    }

    private void abrirFragmentSiguiente() {
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.content_frame, CanchasFragment.nuevaInstancia(), MIS_CANCHAS.toString())
                .addToBackStack(null)
                .commit();
    }
}

