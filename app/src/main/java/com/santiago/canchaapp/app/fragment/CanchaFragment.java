package com.santiago.canchaapp.app.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.santiago.canchaapp.R;
import com.santiago.canchaapp.app.adapter.page.CanchaPageAdapter;
import com.santiago.canchaapp.app.adapter.page.ReservasPageAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CanchaFragment extends Fragment {

    @BindView(R.id.container_cancha)
    public ViewPager viewPager;

    @BindView(R.id.tabs_cancha)
    public TabLayout tabs;

    private CanchaPageAdapter adapter;

    public static CanchaFragment nuevaInstancia() {
        return new CanchaFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // General
        super.onCreate(savedInstanceState);

        // View
        View view = inflater.inflate(R.layout.fragment_cancha, container, false);
        ButterKnife.bind(this, view);

        // Tabs canchas
        adapter = new CanchaPageAdapter(getChildFragmentManager());
        viewPager.setAdapter(adapter);
        tabs.setupWithViewPager(viewPager);

        return view;
    }

}
