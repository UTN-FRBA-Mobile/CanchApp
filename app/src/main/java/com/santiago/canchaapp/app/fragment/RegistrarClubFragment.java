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
import android.widget.TextView;
import android.widget.Toast;

import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarChangeListener;
import com.crystal.crystalrangeseekbar.widgets.CrystalRangeSeekbar;
import com.santiago.canchaapp.R;
import com.santiago.canchaapp.dominio.Horario;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.widget.Toast.LENGTH_SHORT;
import static com.santiago.canchaapp.app.otros.FragmentTags.MAP_CLUB;
import static com.santiago.canchaapp.app.otros.FragmentTags.REGISTRAR_CLUB;
import static com.santiago.canchaapp.app.otros.TextUtils.esUnEmail;
import static com.santiago.canchaapp.app.otros.TextUtils.estaVacio;
import static java.lang.Integer.parseInt;

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
        setRangoHorario();
        setBtnContinuar();
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Registrar club");
        return view;
    }

    private void setBtnContinuar() {
        continuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validarCampos(view.getContext())) {
                    abrirFragmentSiguiente();
                }
            }
        });
    }

    private void setRangoHorario() {
        rangoHorario.setOnRangeSeekbarChangeListener(new OnRangeSeekbarChangeListener() {
            @Override
            public void valueChanged(Number minValue, Number maxValue) {
                valorMinimo.setText(String.valueOf(minValue));
                valorMaximo.setText(String.valueOf(maxValue));
            }
        });
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
        Bundle args = getParameters();
        mapClubFragment.setArguments(args);
        mapClubFragment.setEnterTransition(new Slide(Gravity.RIGHT));
        mapClubFragment.setExitTransition(new Slide(Gravity.LEFT));
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.content_frame, mapClubFragment, MAP_CLUB.toString())
                .addToBackStack(REGISTRAR_CLUB.toString())
                .commit();
    }

    private Bundle getParameters() {
        Bundle args = new Bundle();
        args.putString("nombreClub", nombre());
        args.putString("telefono", telefono());
        args.putString("email", email());
        args.putSerializable("rangoHorario", new Horario(valorMinimo(), valorMaximo()));
        return args;
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

    private Integer valorMinimo() {return parseInt(valorMinimo.getText().toString());}

    private Integer valorMaximo() {return parseInt(valorMaximo.getText().toString());}

}
