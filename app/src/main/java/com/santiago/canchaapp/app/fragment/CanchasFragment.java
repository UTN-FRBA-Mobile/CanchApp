package com.santiago.canchaapp.app.fragment;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Slide;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.santiago.canchaapp.R;
import com.santiago.canchaapp.app.adapter.CanchasAdapter;
import com.santiago.canchaapp.dominio.Cancha;
import com.santiago.canchaapp.dominio.Club;
import com.santiago.canchaapp.servicios.Servidor;

import java.io.Serializable;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.santiago.canchaapp.app.otros.FragmentTags.REGISTRAR_CANCHA;

public class CanchasFragment extends Fragment {

    private static String ARG_ID_CLUB = "idClub";

    private static String ARG_MI_CLUB = "esMiClub";

    @BindView(R.id.recycler_view_canchas)
    public RecyclerView canchasRecyclerView;

    @BindView(R.id.fab)
    public FloatingActionButton fab;

    private RecyclerView.LayoutManager layoutManager;

    private CanchasAdapter adapter;

    public static CanchasFragment nuevaInstancia(String idClub, Boolean esMiClub) {
        CanchasFragment fragment = new CanchasFragment();
        Bundle args = new Bundle();
        args.putBoolean(ARG_MI_CLUB, esMiClub);
        args.putString(ARG_ID_CLUB, idClub);
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
        adapter = new CanchasAdapter(getContext(), canchas(), esMiClub());
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
        Fragment agregarCanchaFragment = AgregarCanchaFragment.nuevaInstancia();
        agregarCanchaFragment.setExitTransition(new Slide(Gravity.LEFT));
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.club_layout, agregarCanchaFragment, REGISTRAR_CANCHA.toString())
                .addToBackStack(null)
                .commit();
    }

    private List<Cancha> canchas() {
        return Servidor.instancia().getCanchas();
    }

    private boolean esMiClub() {
        return getArguments().getBoolean(ARG_MI_CLUB);
    }

}
