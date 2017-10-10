package com.santiago.canchaapp.app.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.santiago.canchaapp.R;
import com.santiago.canchaapp.app.adapter.CanchasAdapter;
import com.santiago.canchaapp.dominio.Cancha;
import com.santiago.canchaapp.servicios.Servidor;

import static com.santiago.canchaapp.app.otros.FragmentTags.REGISTRAR_CANCHA;

import java.io.Serializable;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CanchasFragment extends Fragment {

    private static String ARG_CANCHAS = "canchas";

    @BindView(R.id.recycler_view_canchas)
    public RecyclerView canchasRecyclerView;

    @BindView(R.id.fab)
    public FloatingActionButton fab;

    private RecyclerView.LayoutManager layoutManager;

    private CanchasAdapter adapter;

    public static CanchasFragment nuevaInstancia() {
        CanchasFragment fragment = new CanchasFragment();

        Bundle args = new Bundle();
        args.putSerializable(ARG_CANCHAS, (Serializable) datosDeCanchas());
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_canchas, container, false);
        ButterKnife.bind(this, rootView);

        // Recycler view
        layoutManager = new LinearLayoutManager(getActivity());
        canchasRecyclerView.setLayoutManager(layoutManager);

        // Adapter
        adapter = new CanchasAdapter(getContext(), canchas());
        canchasRecyclerView.setAdapter(adapter);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirFragmentSiguiente();
            }
        });

        return rootView;
    }

    private void abrirFragmentSiguiente() {
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.club_layout, AgregarCanchaFragment.nuevaInstancia(), REGISTRAR_CANCHA.toString())
                .addToBackStack(null)
                .commit();
    }

    private static List<Cancha> datosDeCanchas() {
        return Servidor.instancia().getCanchas();
    }

    private List<Cancha> canchas() {
        return (List<Cancha>) getArguments().getSerializable(ARG_CANCHAS);
    }

}
