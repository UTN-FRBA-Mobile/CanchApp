package com.santiago.canchaapp;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarChangeListener;
import com.crystal.crystalrangeseekbar.widgets.CrystalRangeSeekbar;

import butterknife.BindView;
import butterknife.ButterKnife;


public class RegistrarClub extends Fragment{

    @BindView(R.id.rangoHorario)
    public CrystalRangeSeekbar rangoHorario;
    @BindView(R.id.txtValorMinimo)
    public TextView valorMinimo;
    @BindView(R.id.txtValorMaximo)
    public TextView valorMaximo;

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
        return view;
    }
}
