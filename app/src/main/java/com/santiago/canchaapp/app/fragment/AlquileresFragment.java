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
import com.santiago.canchaapp.app.adapter.page.AlquileresPageAdapter;
import com.santiago.canchaapp.app.adapter.page.ReservasPageAdapter;
import com.santiago.canchaapp.dominio.Alquiler;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AlquileresFragment extends Fragment {

    @BindView(R.id.container_alquileres)
    public ViewPager viewPager;

    @BindView(R.id.tabs_alquileres)
    public TabLayout tabs;

    private AlquileresPageAdapter adapter;

    public static AlquileresFragment nuevaInstancia() {
        return new AlquileresFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_alquileres, container, false);
        ButterKnife.bind(this, view);

        adapter = new AlquileresPageAdapter(getChildFragmentManager());
        viewPager.setAdapter(adapter);
        tabs.setupWithViewPager(viewPager);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Mis alquileres");
        return view;
    }

}
