package com.santiago.canchaapp.app.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.santiago.canchaapp.R;

import java.util.Date;

import butterknife.ButterKnife;

public class ListaHorariosFragment extends Fragment {

    private static String ARG_DIA = "dia";
/*
    @BindView(R.id.recycler_view_reservas)
    public RecyclerView reservasRecyclerView;

    private RecyclerView.LayoutManager layoutManager;

    private ReservasAdapter adapter;*/

    public static ListaHorariosFragment nuevaInstancia(Date dia) {
        ListaHorariosFragment fragment = new ListaHorariosFragment();

        Bundle args = new Bundle();
        args.putSerializable(ARG_DIA, dia);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_lista_horarios, container, false);
        cargarVista(rootView);
        return rootView;
    }

    private void cargarVista(View rootView) {
        ButterKnife.bind(this, rootView);
    }

}