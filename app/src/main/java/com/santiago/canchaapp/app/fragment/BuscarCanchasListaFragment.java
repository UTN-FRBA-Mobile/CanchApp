package com.santiago.canchaapp.app.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.santiago.canchaapp.R;
import com.santiago.canchaapp.app.adapter.ClubesAdapter;
import com.santiago.canchaapp.dominio.Club;
import com.santiago.canchaapp.servicios.Servidor;

import java.io.Serializable;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BuscarCanchasListaFragment extends Fragment {

    private static String ARG_CANCHAS = "canchas";

    @BindView(R.id.recycler_view_clubes)
    public RecyclerView clubesRecyclerView;

    private RecyclerView.LayoutManager layoutManager;

    private ClubesAdapter adapter;

    public static BuscarCanchasListaFragment nuevaInstancia() {
        BuscarCanchasListaFragment fragment = new BuscarCanchasListaFragment();

        Bundle args = new Bundle();
        args.putSerializable(ARG_CANCHAS, (Serializable) datosDeClubes());
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_lista_clubes, container, false);
        cargarVista(rootView);

        return rootView;
    }

    private void cargarVista(View rootView) {
        ButterKnife.bind(this, rootView);

        // Recycler view
        layoutManager = new LinearLayoutManager(getActivity());
        clubesRecyclerView.setLayoutManager(layoutManager);

        // Adapter
        adapter = new ClubesAdapter(getContext(), datosDeClubes());
        clubesRecyclerView.setAdapter(adapter);
    }

    private static List<Club> datosDeClubes() {
        return Servidor.instancia().getClubes();
    }


}
