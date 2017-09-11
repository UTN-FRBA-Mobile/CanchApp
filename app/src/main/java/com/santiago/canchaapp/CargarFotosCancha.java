package com.santiago.canchaapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CargarFotosCancha extends AppCompatActivity {
    private ImageButton btn_cargarFotosCancha;

    @BindView(R.id.btnImagen01)
    public ImageButton btnCargarFotosCancha;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cargar_fotos_cancha);
        ButterKnife.bind(this);
        btnCargarFotosCancha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Esto si funciona",Toast.LENGTH_LONG).show();
            }
        });
    }
}
