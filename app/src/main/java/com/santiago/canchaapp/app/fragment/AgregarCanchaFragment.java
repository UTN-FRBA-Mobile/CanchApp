package com.santiago.canchaapp.app.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.transition.Slide;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.santiago.canchaapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.santiago.canchaapp.app.otros.FragmentTags.CARGAR_FOTOS_CANCHA;

public class AgregarCanchaFragment extends Fragment {

    @BindView(R.id.floatingbtnContinuar)
    public FloatingActionButton continuar;

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
                //if (validarCampos(view.getContext())) {
                abrirFragmentSiguiente();
                //}
            }
        });

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Agregar cancha");

        return view;
    }

    private void abrirFragmentSiguiente() {

        Fragment cargarFotosFragment = CargarFotosCanchaFragment.nuevaInstancia();
        cargarFotosFragment.setEnterTransition(new Slide(Gravity.RIGHT));

        getFragmentManager()
                .beginTransaction()
                .replace(R.id.club_layout, cargarFotosFragment, CARGAR_FOTOS_CANCHA.toString())
                .addToBackStack(null)
                .commit();
    }

}
