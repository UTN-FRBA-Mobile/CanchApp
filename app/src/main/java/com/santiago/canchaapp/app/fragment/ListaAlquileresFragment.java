package com.santiago.canchaapp.app.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.santiago.canchaapp.R;
import com.santiago.canchaapp.app.adapter.AlquileresAdapter;
import com.santiago.canchaapp.app.adapter.ReservasAdapter;
import com.santiago.canchaapp.app.otros.AccionesSobreReserva;
import com.santiago.canchaapp.app.otros.DateUtils;
import com.santiago.canchaapp.app.otros.TipoReservas;
import com.santiago.canchaapp.dominio.Alquiler;
import com.santiago.canchaapp.dominio.Club;
import com.santiago.canchaapp.dominio.DataBase;
import com.santiago.canchaapp.dominio.Reserva;
import com.santiago.canchaapp.servicios.Servidor;
import com.santiago.canchaapp.servicios.Sesion;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.google.firebase.database.DatabaseError.PERMISSION_DENIED;
import static com.santiago.canchaapp.app.otros.AccionesSobreReserva.NINGUNA;
import static com.santiago.canchaapp.app.otros.AccionesSobreReserva.SOLO_CANCELAR;
import static com.santiago.canchaapp.app.otros.AccionesSobreReserva.TODAS;
import static com.santiago.canchaapp.app.otros.TipoReservas.APROBADAS;
import static com.santiago.canchaapp.app.otros.TipoReservas.PENDIENTES;
import static com.santiago.canchaapp.app.otros.TipoReservas.valueOf;

public class ListaAlquileresFragment extends Fragment {

    private static String ARG_TIPO_RESERVAS = "tipo_reservas";

    private static String ARG_ALQUILERES = "alquileres";

    @BindView(R.id.recycler_view_alquileres)
    public RecyclerView alquileresRecyclerView;

    private RecyclerView.LayoutManager layoutManager;

    private AlquileresAdapter adapter;

    public static ListaAlquileresFragment nuevaInstancia(TipoReservas tipo) {
        ListaAlquileresFragment fragment = new ListaAlquileresFragment();

        Bundle args = new Bundle();
        args.putString(ARG_TIPO_RESERVAS, tipo.toString());
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_lista_alquileres, container, false);
        cargarVista(rootView);
        return rootView;
    }

    private void cargarVista(View rootView) {
        ButterKnife.bind(this, rootView);

        // Recycler view
        layoutManager = new LinearLayoutManager(getActivity());
        alquileresRecyclerView.setLayoutManager(layoutManager);

        // Adapter
        adapter = new AlquileresAdapter(tipoReservas(), accionesSobreReservas());
        alquileresRecyclerView.setAdapter(adapter);

        // Datos
        Query ref = DataBase.getInstancia().getReferenceAlquileresPorClub(Sesion.getInstancia().getUsuario().getIdClub());
        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                actualizarLista(dataSnapshot);
            }
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                actualizarLista(dataSnapshot);
            }
            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {}
            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {}
            @Override
            public void onCancelled(DatabaseError databaseError) {
                if (databaseError.getCode() != PERMISSION_DENIED)
                    Toast.makeText(getContext(), R.string.txtErrorDescargandoInfo, Toast.LENGTH_LONG).show();
            }

            private void actualizarLista(DataSnapshot snapshotAlquiler) {
                Alquiler alquiler = snapshotAlquiler.getValue(Alquiler.class);
                adapter.actualizarLista(alquiler);
            }

        });
    }

    private AccionesSobreReserva accionesSobreReservas() {
       // Boolean alquileres = sonAlquileres();
        TipoReservas tipo = tipoReservas();
        if (tipo == PENDIENTES)
            return TODAS;
        return NINGUNA;
    }


    private TipoReservas tipoReservas() {
        return valueOf(getArguments().getString(ARG_TIPO_RESERVAS));
    }

}