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
    @BindView(R.id.precio)
    public TextView textoPrecio;
    @BindView(R.id.superficie)
    public TextView textoSuperficie;
    @BindView(R.id.extra)
    public TextView textoExtra;
    @BindView(R.id.fotos)
    public LinearLayout fotos;

    public static DatosCanchaFragment nuevaInstancia(Cancha cancha) {
        DatosCanchaFragment fragment = new DatosCanchaFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_CANCHA, cancha);
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
        textoNombre.setText(cancha.getNombre());
        textoTipo.setText(cancha.getTipoCancha().nombre);
        textoSuperficie.setText(view.getResources().getString(R.string.txtCanchaSuperficie, cancha.getSuperficie().nombre));
        textoPrecio.setText("Precio: $" + cancha.getPrecioString());

        if (cancha.esTechada()) {
            textoExtra.setText(view.getResources().getString(R.string.txtCanchaTechada));
        } else {
            textoExtra.setVisibility(GONE);
        }

        if (cancha.tieneFotos()) {
            Picasso.with(view.getContext())
                    .load(cancha.getFotoPrincipalUrl())
                    .placeholder(R.drawable.cancha_sin_foto)
                    .fit().centerCrop().into(fotoPrincipal);
            for (String foto : cancha.getFotosUrls().subList(1, cancha.getFotosUrls().size())) {
                View fotosView = inflater.inflate(R.layout.item_foto_cancha, fotos, false);
                ImageView fotoView = fotosView.findViewById(R.id.foto_cancha_item);
                Picasso.with(view.getContext()).load(foto).fit().centerCrop().into(fotoView);
                fotos.addView(fotosView);
            }
        } else {
            Picasso.with(view.getContext())
                    .load(R.drawable.cancha_sin_foto)
                    .fit().centerCrop().into(fotoPrincipal);
        }



    }

    private Cancha cancha() {
        return (Cancha) getArguments().getSerializable(ARG_CANCHA);
    }

}
