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

import butterknife.BindView;
import butterknife.ButterKnife;

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
        String titulo = "Datos del Club";
        if(esMiClub)
            titulo = "Mi Club";
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(titulo);
        return view;
    }
}
