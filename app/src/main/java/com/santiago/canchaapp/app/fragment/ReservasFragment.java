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

    private static String ARG_TITULO = "titulo";
    private static String ARG_ALQUILERES = "Alquileres";

    @BindView(R.id.container_reservas)
    public ViewPager viewPager;

    @BindView(R.id.tabs_reservas)
    public TabLayout tabs;

    private ReservasPageAdapter adapter;

    public static ReservasFragment nuevaInstanciaParaReservas() {
        return nuevaInstancia(false);
    }

    public static ReservasFragment nuevaInstanciaParaAlquileres() {
        return nuevaInstancia(true);
    }

    private static ReservasFragment nuevaInstancia(Boolean paraAlquileres) {
        ReservasFragment fragment = new ReservasFragment();

        Bundle args = new Bundle();
        args.putBoolean(ARG_ALQUILERES, paraAlquileres);

        if(paraAlquileres) {
            args.putCharSequence(ARG_TITULO, "Mis alquileres");
        } else {
            args.putCharSequence(ARG_TITULO, "Mis reservas");
        }

        fragment.setArguments(args);

        return fragment;
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
        adapter = new ReservasPageAdapter(getChildFragmentManager(), getArguments().getBoolean(ARG_ALQUILERES));
        viewPager.setAdapter(adapter);
        tabs.setupWithViewPager(viewPager);

        ((AppCompatActivity) getActivity()).getSupportActionBar()
                .setTitle(getArguments().getCharSequence(ARG_TITULO));

        return view;
    }

}
