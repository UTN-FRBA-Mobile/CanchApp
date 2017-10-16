package com.santiago.canchaapp.app.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.santiago.canchaapp.R;
import com.santiago.canchaapp.dominio.Cancha;
import com.santiago.canchaapp.dominio.Club;
import com.santiago.canchaapp.servicios.Servidor;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.view.View.GONE;

public class DatosClubFragment extends Fragment {

    @BindView(R.id.nombre)
    public TextView textoNombre;

    @BindView(R.id.direccion)
    public TextView textoDireccion;

    @BindView(R.id.telefono)
    public TextView textoTelefono;

    @BindView(R.id.email)
    public TextView textoEmail;

    @BindView(R.id.horario)
    public TextView textoHorario;

    @BindView(R.id.mapa_club)
    public ImageView imagenMapa;

    private static String ARG_CLUB = "club";

    public static DatosClubFragment nuevaInstancia() {
        DatosClubFragment fragment = new DatosClubFragment();

        Bundle args = new Bundle();
        args.putSerializable(ARG_CLUB, Servidor.instancia().getClub());
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_datos_club, container, false);
        ButterKnife.bind(this, rootView);

        cargarVista(inflater, rootView, club());

        rootView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener()
        {
            @Override
            public boolean onPreDraw()
            {
                rootView.getViewTreeObserver().removeOnPreDrawListener(this);

                LinearLayout layoutMapa = (LinearLayout) rootView.findViewById(R.id.layoutMapaClub);
                double width = layoutMapa.getWidth();
                double height = layoutMapa.getHeight();

                double proporcion;
                if (width > height) {
                    proporcion = width / 640;
                } else {
                    proporcion = height / 640;
                }

                double finalWidth = width/proporcion;
                double finalHeight = height/proporcion;

                // Setea mapita estático
                String urlMapa =
                        "https://maps.googleapis.com/maps/api/staticmap?center=" +
                                club().getDireccion().replace(" ", "+") +
                                "&zoom=16&size=" +
                                (int) finalWidth + "x" + (int) finalHeight +
                                "&key=AIzaSyBfbfsDgjD9_U8j1PpzRlkHtqnlDwD1cGI";

                Picasso.with(rootView.getContext()).load(urlMapa).fit().into(imagenMapa);

                return false;
            }
        });

        return rootView;
    }

    private void cargarVista(LayoutInflater inflater, View view, Club club) {
        // Setea textos
        textoNombre.setText(club.getNombre());
        textoDireccion.setText(club.getDireccion());
        textoTelefono.setText("Teléfono: " + club.getTelefono());
        textoEmail.setText("Email: " + club.getEmail());
        textoHorario.setText(
                "Abierto de " + club.getRangoHorario().getDesde() +
                        " a " + club.getRangoHorario().getHasta() + "hs.");
    }

 //   public View onSizeChanged(LayoutInflater inflater, ViewGroup container,
 //                            Bundle savedInstanceState) {
 //       View rootView = inflater.inflate(R.layout.fragment_datos_club, container, false);
 //       ButterKnife.bind(this, rootView);

        //cargarVista(inflater, rootView, club());

    //    return rootView;
   // }

    private Club club() {
        return (Club) getArguments().getSerializable(ARG_CLUB);
    }

}
