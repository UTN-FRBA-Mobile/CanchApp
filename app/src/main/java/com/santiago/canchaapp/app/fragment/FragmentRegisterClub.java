package com.santiago.canchaapp.app.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarChangeListener;
import com.crystal.crystalrangeseekbar.widgets.CrystalRangeSeekbar;
import com.santiago.canchaapp.R;
import com.santiago.canchaapp.dominio.Horario;
import butterknife.BindView;
import butterknife.ButterKnife;
import static com.santiago.canchaapp.app.otros.TextUtils.isEmpty;
import static java.lang.Integer.parseInt;

public class FragmentRegisterClub extends Fragment{

    @BindView(R.id.til_name)
    public TextInputLayout tilName;
    @BindView(R.id.txt_name)
    public EditText txtName;

    @BindView(R.id.til_phone)
    public TextInputLayout tilPhone;
    @BindView(R.id.txt_phone)
    public EditText txtPhone;

    @BindView(R.id.btn_continue)
    public FloatingActionButton fabNext;

    public static FragmentRegisterClub nuevaInstancia() {
        return new FragmentRegisterClub();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_register_club, container, false);
        ButterKnife.bind(this, view);
        setFabNext();
        setTxtEvent(txtName, tilName);
        setTxtEvent(txtPhone, tilPhone);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Registrar club");
        return view;
    }

    private void setFabNext() {
        fabNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validarCampos()) {
                    abrirFragmentSiguiente();
                }
            }
        });
    }

    private void setTxtEvent(EditText txt, final TextInputLayout til) {
        txt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != " ") til.setError(null);
            }
        });
    }

    private boolean validarCampos() {
        return !(isEmpty(name(), tilName) || isEmpty(phone(), tilPhone));
    }

    private void abrirFragmentSiguiente() {
      /*  Bundle args = getParameters();
        mapClubFragment.setArguments(args);
        mapClubFragment.setEnterTransition(new Slide(Gravity.RIGHT));
        mapClubFragment.setExitTransition(new Slide(Gravity.LEFT));
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.content_frame, mapClubFragment, MAP_CLUB.toString())
                .addToBackStack(REGISTRAR_CLUB.toString())
                .commit();*/
    }

    private Bundle getParameters() {
        Bundle args = new Bundle();
        args.putString("nombreClub", name());
        args.putString("telefono", phone());
        return args;
    }

    // Utils
    private String name() {
        return txtName.getText().toString();
    }

    private String phone() {
        return txtPhone.getText().toString();
    }

}
