package com.santiago.canchaapp.app.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.santiago.canchaapp.R;
import com.santiago.canchaapp.app.adapter.page.ClubPageAdapter;
import com.santiago.canchaapp.dominio.Club;
import com.santiago.canchaapp.servicios.Servidor;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ClubFragment extends Fragment {

    @BindView(R.id.container_club)
    public ViewPager viewPager;

    @BindView(R.id.tabs_club)
    public TabLayout tabs;

    private ClubPageAdapter adapter;

    //Creo que este no lo usamos más, lo dejo por retrocompatibilidad (?).
    private static String ARG_MICLUB = "MiClub";

    private static String ARG_CLUB = "club";

    public static ClubFragment nuevaInstanciaParaMiClub() {
        //TODO En vez de devolver este falso club aquí debería cargar el real de Firebase.
        return nuevaInstancia(true, Servidor.instancia().miClub());
    }

    public static ClubFragment nuevaInstanciaParaOtroClub(Club club) {
        return nuevaInstancia(false, club);
    }

    public static ClubFragment nuevaInstancia(Boolean miClub, Club unClub) {
        ClubFragment fragment = new ClubFragment();

        Bundle args = new Bundle();
        args.putBoolean(ARG_MICLUB, miClub);
        args.putSerializable(ARG_CLUB, unClub);

        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // General
        super.onCreate(savedInstanceState);

        // View
        View view = inflater.inflate(R.layout.fragment_club, container, false);
        ButterKnife.bind(this, view);

        //Tabs club
        adapter = new ClubPageAdapter(getChildFragmentManager(), getArguments().getBoolean(ARG_MICLUB), getArguments().getSerializable(ARG_CLUB));
        viewPager.setAdapter(adapter);
        tabs.setupWithViewPager(viewPager);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Datos del Club");

        return view;
    }
}
