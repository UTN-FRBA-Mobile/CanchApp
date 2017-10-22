package com.santiago.canchaapp.app.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.santiago.canchaapp.MenuNavegacion;
import com.santiago.canchaapp.R;
import com.santiago.canchaapp.app.adapter.page.ClubPageAdapter;
import com.santiago.canchaapp.dominio.Cancha;
import com.santiago.canchaapp.dominio.Club;
import com.santiago.canchaapp.dominio.DataBase;
import com.santiago.canchaapp.dominio.Horario;
import com.santiago.canchaapp.servicios.Servidor;

import java.util.List;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.santiago.canchaapp.app.otros.FragmentTags.MI_CLUB;
import static com.santiago.canchaapp.app.otros.FragmentTags.REGISTRAR_CLUB;

public class ClubFragment extends Fragment {

    @BindView(R.id.container_club)
    public ViewPager viewPager;

    @BindView(R.id.tabs_club)
    public TabLayout tabs;

    private ClubPageAdapter adapter;

    private static String ARG_ID_CLUB = "idClub";
    private static String ARG_MI_CLUB = "esMiClub";



    public static ClubFragment nuevaInstancia(String idClub, Boolean esMiClub) {
        ClubFragment fragment = new ClubFragment();
        Bundle args = new Bundle();
        args.putString(ARG_ID_CLUB, idClub);
        args.putBoolean(ARG_MI_CLUB, esMiClub);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_club, container, false);
        ButterKnife.bind(this, view);
        String idClub = getArguments().getString(ARG_ID_CLUB);
        Boolean esMiClub = getArguments().getBoolean(ARG_MI_CLUB);
        adapter = new ClubPageAdapter(getChildFragmentManager(), idClub, esMiClub);
        viewPager.setAdapter(adapter);
        tabs.setupWithViewPager(viewPager);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Datos del Club");
        return view;
    }
}
