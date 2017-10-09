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

    private static String ARG_MICLUB = "MiClub";

    public static ClubFragment nuevaInstanciaParaMiClub() {
        return nuevaInstancia(true);
    }

    public static ClubFragment nuevaInstanciaParaOtroClub() {
        return nuevaInstancia(false);
    }

    public static ClubFragment nuevaInstancia(Boolean miClub) {
        ClubFragment fragment = new ClubFragment();

        Bundle args = new Bundle();
        args.putBoolean(ARG_MICLUB, miClub);

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
        adapter = new ClubPageAdapter(getChildFragmentManager(), getArguments().getBoolean(ARG_MICLUB));
        viewPager.setAdapter(adapter);
        tabs.setupWithViewPager(viewPager);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Club");

        return view;
    }
}
