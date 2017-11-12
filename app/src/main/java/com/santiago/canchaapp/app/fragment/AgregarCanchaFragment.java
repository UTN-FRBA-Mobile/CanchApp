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
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.santiago.canchaapp.R;
import com.santiago.canchaapp.dominio.TipoCancha;
import com.santiago.canchaapp.dominio.TipoSuperficie;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.santiago.canchaapp.app.otros.FragmentTags.CARGAR_FOTOS_CANCHA;

public class AgregarCanchaFragment extends Fragment {

    @BindView(R.id.txtNombreCancha)
    public EditText txtNombreCancha;
    @BindView(R.id.txtPrecio)
    public EditText txtPrecio;
    @BindView(R.id.spinnerDeporte)
    public Spinner spinnerDeporte;
    @BindView(R.id.spinnerSuperficie)
    public Spinner spinnerSuperficie;
    @BindView(R.id.switchTechada)
    public Switch switchTechada;
    @BindView(R.id.floatingbtnContinuar)
    public FloatingActionButton continuar;
    public CargarFotosCanchaFragment cargarFotosCanchaFragment = new CargarFotosCanchaFragment();

    public static AgregarCanchaFragment nuevaInstancia() {
        return new AgregarCanchaFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_agregar_cancha, container, false);
        ButterKnife.bind(this, view);
        setActionFloatingBtn();
        setSpinner(spinnerDeporte, TipoCancha.nombres(), view);
        setSpinner(spinnerSuperficie, TipoSuperficie.nombres(), view);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Agregar cancha");
        return view;
    }

    private void setActionFloatingBtn(){
        continuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (camposValidos()) {
                    abrirFragmentSiguiente();
                }
                else {
                    Toast.makeText(view.getContext(), R.string.txtCompletarTodosLosCampos, Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void setSpinner(Spinner spinner, List<String> options, View view) {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_spinner_item, options);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    private void abrirFragmentSiguiente() {
        Bundle args = getParameters();
        cargarFotosCanchaFragment.setArguments(args);
        cargarFotosCanchaFragment.setEnterTransition(new Slide(Gravity.RIGHT));
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content_frame, cargarFotosCanchaFragment, CARGAR_FOTOS_CANCHA.toString())
                .addToBackStack(null)
                .commit();
    }

    private boolean camposValidos() {
        return !estaVacio(nombreCancha()) && !estaVacio(precio());
    }

    private Bundle getParameters() {
        Bundle args = new Bundle();
        args.putString("nombreCancha", nombreCancha());
        args.putString("deporte", deporte());
        args.putInt("precio", Integer.parseInt(precio()));
        args.putString("superficie", superficie());
        args.putBoolean("opcTechada", opcionTechada());
        return args;
    }

    // Utils
    public static boolean estaVacio(String texto) {
        return texto == null || texto.replace(" ", "").isEmpty();
    }

    private String nombreCancha() {
        return txtNombreCancha.getText().toString();
    }

    private String deporte() {
        return spinnerDeporte.getSelectedItem().toString();
    }

    private String precio() {
        return txtPrecio.getText().toString();
    }

    private String superficie() { return spinnerSuperficie.getSelectedItem().toString(); }

    private Boolean opcionTechada() { return switchTechada.isChecked();}

}
