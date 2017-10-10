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

import static com.santiago.canchaapp.app.otros.FragmentTags.CARGAR_FOTOS_CANCHA;

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
                abrirFragmentSiguiente();
            }
        });

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Agregar cancha");

        return view;
    }

    private void abrirFragmentSiguiente() {
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.club_layout, CargarFotosCanchaFragment.nuevaInstancia(), CARGAR_FOTOS_CANCHA.toString())
                .addToBackStack(null)
                .commit();
    }

}
