package com.santiago.canchaapp.app.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarChangeListener;
import com.crystal.crystalrangeseekbar.widgets.CrystalRangeSeekbar;
import com.santiago.canchaapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.widget.Toast.LENGTH_SHORT;
import static com.santiago.canchaapp.app.otros.FragmentTags.MAP_CLUB;
import static com.santiago.canchaapp.app.otros.TextUtils.esUnEmail;
import static com.santiago.canchaapp.app.otros.TextUtils.estaVacio;



public class RegistrarClubFragment extends Fragment{

    @BindView(R.id.txtNombre)
    public EditText txtNombre;
    @BindView(R.id.txtTelefono)
    public EditText txtTelefono;
    @BindView(R.id.txtEmail)
    public EditText txtEmail;
    @BindView(R.id.rangoHorario)
    public CrystalRangeSeekbar rangoHorario;
    @BindView(R.id.txtValorMinimo)
    public TextView valorMinimo;
    @BindView(R.id.txtValorMaximo)
    public TextView valorMaximo;
    @BindView(R.id.boton_continuar)
    public FloatingActionButton continuar;
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
                if (validarCampos(view.getContext())) {
                    abrirFragmentSiguiente();
                }
            }
        });
        return view;
    }

    private boolean validarCampos(Context context) {
        if (estaVacio(nombre()) || estaVacio(telefono()) || estaVacio(email())) {
            Toast.makeText(context, R.string.txtCompletarTodosLosCampos, LENGTH_SHORT).show();
            return false;
        } else if (!esUnEmail(email())) {
            Toast.makeText(context, R.string.txtEmailIncorrecto, LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void abrirFragmentSiguiente() {
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.content_frame, mapClubFragment, MAP_CLUB.toString())
                .addToBackStack(null)
                .commit();
    }

    // Utils

    private String nombre() {
        return txtNombre.getText().toString();
    }

    private String telefono() {
        return txtTelefono.getText().toString();
    }

    private String email() {
        return txtEmail.getText().toString();
    }

}
