package com.santiago.canchaapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

public class CargarFotosCancha extends AppCompatActivity {
    private ImageButton btn_cargarFotosCancha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cargar_fotos_cancha);

        btn_cargarFotosCancha = (ImageButton) findViewById(R.id.btnImagen01);
        btn_cargarFotosCancha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Esto si funciona",Toast.LENGTH_LONG).show();
            }
        });
    }
}
