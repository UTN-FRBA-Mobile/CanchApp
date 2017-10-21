package com.santiago.canchaapp.app.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.santiago.canchaapp.R;
import com.santiago.canchaapp.app.adapter.page.HorariosPageAdapter;
import com.santiago.canchaapp.dominio.Cancha;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HorariosCanchaFragment extends Fragment {

    private static String ARG_CANCHA = "cancha";

    private static String ARG_MI_CANCHA = "miCancha";

    @BindView(R.id.container_horarios)
    public ViewPager viewPager;

    private HorariosPageAdapter adapter;

    public static HorariosCanchaFragment nuevaInstancia(Cancha cancha, boolean esMiCancha) {
        HorariosCanchaFragment fragment = new HorariosCanchaFragment();

        Bundle args = new Bundle();
        args.putSerializable(ARG_CANCHA, cancha);
        args.putBoolean(ARG_MI_CANCHA, esMiCancha);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_horarios_cancha, container, false);
        ButterKnife.bind(this, view);

        adapter = new HorariosPageAdapter(getChildFragmentManager(), cancha(), esMiCancha());
        viewPager.setAdapter(adapter);

        return view;
    }

    private Cancha cancha() {
        return (Cancha) getArguments().getSerializable(ARG_CANCHA);
    }

    private boolean esMiCancha() {
        return getArguments().getBoolean(ARG_MI_CANCHA);
    }

}
