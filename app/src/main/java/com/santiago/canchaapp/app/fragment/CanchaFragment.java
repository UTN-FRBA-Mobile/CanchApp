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
import com.santiago.canchaapp.app.adapter.page.CanchaPageAdapter;
import com.santiago.canchaapp.dominio.Cancha;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CanchaFragment extends Fragment {

    private static String ARG_CANCHA = "cancha";

    @BindView(R.id.container_cancha)
    public ViewPager viewPager;

    @BindView(R.id.tabs_cancha)
    public TabLayout tabs;

    private CanchaPageAdapter adapter;

    public static CanchaFragment nuevaInstancia(Cancha cancha) {
        CanchaFragment fragment = new CanchaFragment();

        Bundle args = new Bundle();
        args.putSerializable(ARG_CANCHA, cancha);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // General
        super.onCreate(savedInstanceState);

        // View
        View view = inflater.inflate(R.layout.fragment_cancha, container, false);
        ButterKnife.bind(this, view);

        // Tabs canchas
        adapter = new CanchaPageAdapter(getChildFragmentManager(), cancha());
        viewPager.setAdapter(adapter);
        tabs.setupWithViewPager(viewPager);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Datos de Cancha");

        return view;
    }

    private Cancha cancha() {
        return (Cancha) getArguments().getSerializable(ARG_CANCHA);
    }

}
