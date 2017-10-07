package com.santiago.canchaapp.app.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.santiago.canchaapp.R;
import com.santiago.canchaapp.app.adapter.page.HorariosPageAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HorariosCanchaFragment extends Fragment {

    @BindView(R.id.container_horarios)
    public ViewPager viewPager;

    private HorariosPageAdapter adapter;

    public static HorariosCanchaFragment nuevaInstancia() {
        return new HorariosCanchaFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_horarios_cancha, container, false);
        ButterKnife.bind(this, view);

        adapter = new HorariosPageAdapter(getChildFragmentManager());
        viewPager.setAdapter(adapter);

        return view;
    }

}
