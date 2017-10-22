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
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.CompoundButton;

import com.santiago.canchaapp.R;
import com.santiago.canchaapp.app.otros.TextUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.santiago.canchaapp.app.otros.FragmentTags.CARGAR_FOTOS_CANCHA;

public class AgregarCanchaFragment extends Fragment {

    @BindView(R.id.txtNombreCancha)
    public EditText txtNombreCancha;
    @BindView(R.id.txtDeporte)
    public EditText txtDeporte;
    @BindView(R.id.txtPrecio)
    public EditText txtPrecio;
    @BindView(R.id.txtSuperficie)
    public EditText txtSuperficie;
    @BindView(R.id.switchTechada)
    public Switch switchTechada;
    @BindView(R.id.floatingbtnContinuar)
    public FloatingActionButton continuar;
    public CargarFotosCanchaFragment unacargarFotosCanchaFragment = new CargarFotosCanchaFragment();

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
        switchTechada.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked){
                if(switchTechada.isChecked()){
                    Toast.makeText(getContext(), R.string.txtTechoSwitchOn, Toast.LENGTH_SHORT).show();
                } else{
                    Toast.makeText(getContext(), R.string.txtTechoSwitchOff, Toast.LENGTH_SHORT).show();
                }
            }
        });

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
        Bundle args = getParameters();
        unacargarFotosCanchaFragment.setArguments(args);
        unacargarFotosCanchaFragment.setEnterTransition(new Slide(Gravity.RIGHT));

        getFragmentManager()
                .beginTransaction()
                .replace(R.id.club_layout, unacargarFotosCanchaFragment, CARGAR_FOTOS_CANCHA.toString())
                .addToBackStack(null)
                .commit();
    }

    private boolean validarCampos(Context context) {
        if (estaVacio(nombreCancha()) || estaVacio(deporte()) || estaVacio(telefono()) || estaVacio(superficie())) {
            Toast.makeText(context, R.string.txtCompletarTodosLosCampos, Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private Bundle getParameters() {
        Bundle args = new Bundle();
        args.putString("nombreCancha", nombreCancha());
        args.putString("deporte", deporte());
        args.putString("telefono", telefono());
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
        return txtDeporte.getText().toString();
    }

    private String telefono() {
        return txtPrecio.getText().toString();
    }

    private String superficie() { return txtSuperficie.getText().toString(); }

    private Boolean opcionTechada() { return switchTechada.isChecked();}

}
