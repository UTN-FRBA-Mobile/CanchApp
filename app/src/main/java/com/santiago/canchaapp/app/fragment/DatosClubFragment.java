package com.santiago.canchaapp.app.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
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

public class DatosClubFragment extends Fragment {

    private static String ARG_CLUB = "club";

    public static DatosClubFragment nuevaInstancia() {
        DatosClubFragment fragment = new DatosClubFragment();

        Bundle args = new Bundle();
        args.putSerializable(ARG_CLUB, Servidor.instancia().getCancha());
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_datos_club, container, false);
        ButterKnife.bind(this, rootView);

        return rootView;
    }

}
