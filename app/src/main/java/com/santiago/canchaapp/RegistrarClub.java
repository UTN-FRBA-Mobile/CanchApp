package com.santiago.canchaapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarChangeListener;
import com.crystal.crystalrangeseekbar.widgets.CrystalRangeSeekbar;

import butterknife.BindView;
import butterknife.ButterKnife;


public class RegistrarClub extends AppCompatActivity {

    @BindView(R.id.rangoHorario)
    public CrystalRangeSeekbar rangoHorario;
    @BindView(R.id.txtValorMinimo)
    public TextView valorMinimo;
    @BindView(R.id.txtValorMaximo)
    public TextView valorMaximo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_club);
        ButterKnife.bind(this);
        rangoHorario.setOnRangeSeekbarChangeListener(new OnRangeSeekbarChangeListener() {
            @Override
            public void valueChanged(Number minValue, Number maxValue) {
                valorMinimo.setText(String.valueOf(minValue));
                valorMaximo.setText(String.valueOf(maxValue));
            }
        });
    }
}
