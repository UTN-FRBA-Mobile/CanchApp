package com.santiago.canchaapp.app.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.santiago.canchaapp.R;
import com.santiago.canchaapp.dominio.Cancha;
import com.santiago.canchaapp.servicios.Servidor;
import com.squareup.picasso.Picasso;

import java.io.Serializable;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.view.View.GONE;

public class DatosCanchaFragment extends Fragment {

    private static String ARG_CANCHA = "cancha";

    @BindView(R.id.foto_principal)
    public ImageView fotoPrincipal;

    @BindView(R.id.nombre)
    public TextView textoNombre;

    @BindView(R.id.tipo)
    public TextView textoTipo;

    @BindView(R.id.superficie)
    public TextView textoSuperficie;

    @BindView(R.id.extra)
    public TextView textoExtra;

    @BindView(R.id.fotos)
    public LinearLayout fotos;

    public static DatosCanchaFragment nuevaInstancia() {
        DatosCanchaFragment fragment = new DatosCanchaFragment();

        Bundle args = new Bundle();
        args.putSerializable(ARG_CANCHA, Servidor.instancia().getCancha());
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_datos_cancha, container, false);
        ButterKnife.bind(this, rootView);
        cargarVista(inflater, rootView, cancha());
        return rootView;
    }

    private void cargarVista(LayoutInflater inflater, View view, Cancha cancha) {
        // Setea textos
        textoNombre.setText(cancha.getDatos().getNombre());
        textoTipo.setText(cancha.getDatos().getTipoCancha().nombre);
        textoSuperficie.setText(view.getResources().getString(R.string.txtCanchaSuperficie, cancha.getDatos().getSuperficie().nombre));
        if (cancha.getDatos().esTechada()) {
            textoExtra.setText(view.getResources().getString(R.string.txtCanchaTechada));
        } else {
            textoExtra.setVisibility(GONE);
        }

        // Setea imagen principal
        Picasso.with(view.getContext()).load(cancha.getDatos().getFotoUrl()).fit().centerCrop().into(fotoPrincipal);

        // Setea resto de las imagenes
        for (String foto : cancha.getFotos()) {
            View fotosView = inflater.inflate(R.layout.item_foto_cancha, fotos, false);
            ImageView fotoView = fotosView.findViewById(R.id.foto_cancha_item);
            Picasso.with(view.getContext()).load(foto).fit().centerCrop().into(fotoView);
            fotos.addView(fotosView);
        }
    }

    private Cancha cancha() {
        return (Cancha) getArguments().getSerializable(ARG_CANCHA);
    }

}