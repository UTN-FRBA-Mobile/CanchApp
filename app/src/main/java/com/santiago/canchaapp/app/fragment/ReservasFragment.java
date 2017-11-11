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
import com.santiago.canchaapp.app.adapter.page.ReservasPageAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReservasFragment extends Fragment {

    @BindView(R.id.container_reservas)
    public ViewPager viewPager;
    @BindView(R.id.tabs_reservas)
    public TabLayout tabs;

    private ReservasPageAdapter adapter;

    public static ReservasFragment  nuevaInstancia() {
        return new ReservasFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // General
        super.onCreate(savedInstanceState);

        // View
        View view = inflater.inflate(R.layout.fragment_reservas, container, false);
        ButterKnife.bind(this, view);

        // Tabs reservas
        // IMPORTANTE: usar getChildFragmentManager()
        adapter = new ReservasPageAdapter(getChildFragmentManager());
        viewPager.setAdapter(adapter);
        tabs.setupWithViewPager(viewPager);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Mis reservas");

        return view;
    }

}
