package com.santiago.canchaapp.app.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarChangeListener;
import com.crystal.crystalrangeseekbar.widgets.CrystalRangeSeekbar;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.santiago.canchaapp.R;

import static com.santiago.canchaapp.app.otros.FragmentTags.MAP_CLUB;

import butterknife.BindView;
import butterknife.ButterKnife;



public class RegistrarClubFragment extends Fragment{

    @BindView(R.id.rangoHorario)
    public CrystalRangeSeekbar rangoHorario;
    @BindView(R.id.txtValorMinimo)
    public TextView valorMinimo;
    @BindView(R.id.txtValorMaximo)
    public TextView valorMaximo;
    @BindView(R.id.btnContinuar)
    public Button continuar;
    public MapClubFragment mapClubFragment = new MapClubFragment();

    public static RegistrarClubFragment nuevaInstancia() {
        return new RegistrarClubFragment();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_registrar_club, container, false);
        ButterKnife.bind(this, view);
        rangoHorario.setOnRangeSeekbarChangeListener(new OnRangeSeekbarChangeListener() {
            @Override
            public void valueChanged(Number minValue, Number maxValue) {
                valorMinimo.setText(String.valueOf(minValue));
                valorMaximo.setText(String.valueOf(maxValue));
            }
        });
        continuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirFragment();
            }
        });
        return view;
    }

    private void abrirFragment() {
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.content_frame, mapClubFragment, MAP_CLUB.toString())
                .addToBackStack(null)
                .commit();
    }

}
