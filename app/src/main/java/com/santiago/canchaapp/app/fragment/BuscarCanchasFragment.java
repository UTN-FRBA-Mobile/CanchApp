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
import com.santiago.canchaapp.app.adapter.page.BuscarCanchasPageAdapter;
import com.santiago.canchaapp.app.adapter.page.ClubPageAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BuscarCanchasFragment extends Fragment{

    @BindView(R.id.container_buscar_canchas)
    public ViewPager viewPager;

    @BindView(R.id.tabs_buscar_canchas)
    public TabLayout tabs;

    private BuscarCanchasPageAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // General
        super.onCreate(savedInstanceState);

        // View
        View view = inflater.inflate(R.layout.fragment_buscar_canchas, container, false);
        ButterKnife.bind(this, view);

        //Tabs club
        adapter = new BuscarCanchasPageAdapter(getChildFragmentManager());
        viewPager.setAdapter(adapter);
        tabs.setupWithViewPager(viewPager);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Buscar canchas");

        return view;
    }

    public static Fragment nuevaInstancia() {
        return new BuscarCanchasFragment();
    }
}
