package com.santiago.canchaapp.app.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.santiago.canchaapp.LoginActivity;
import com.santiago.canchaapp.R;
import com.santiago.canchaapp.app.adapter.HorariosAdapter;
import com.santiago.canchaapp.app.otros.DateUtils;
import com.santiago.canchaapp.dominio.Alquiler;
import com.santiago.canchaapp.dominio.Cancha;
import com.santiago.canchaapp.dominio.DataBase;
import com.santiago.canchaapp.servicios.Servidor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.view.View.INVISIBLE;
import static com.santiago.canchaapp.app.otros.DateUtils.textoDia;

public class ListaHorariosFragment extends Fragment {

    private static String ARG_CANCHA = "cancha";

    private static String ARG_DIA = "dia";

    private static String ARG_POSICION_DIA = "pos_dia";

    @BindView(R.id.fecha_horarios)
    public TextView fecha;

    @BindView(R.id.dia_prev)
    public ImageView flechaDiaAnterior;

    @BindView(R.id.dia_next)
    public ImageView flechaDiaSiguiente;

    @BindView(R.id.recycler_view_horarios)
    public RecyclerView horariosRecyclerView;

    private RecyclerView.LayoutManager layoutManager;

    private HorariosAdapter adapter;

    private Query refDatos;

    public static ListaHorariosFragment nuevaInstancia(Cancha cancha, Date dia, int posicionDia) {
        ListaHorariosFragment fragment = new ListaHorariosFragment();

        Bundle args = new Bundle();
        args.putSerializable(ARG_CANCHA, cancha);
        args.putSerializable(ARG_DIA, dia);
        args.putInt(ARG_POSICION_DIA, posicionDia);
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

        // Appbar
        if (primerDia()) {
            fecha.setText(getResources().getString(R.string.txtHorariosFechaHoy));
            flechaDiaAnterior.setVisibility(INVISIBLE);
        } else {
            fecha.setText(getTextoFecha());
            if (ultimoDia()) {
                flechaDiaSiguiente.setVisibility(INVISIBLE);
            }
        }

        // Obtener cancha
        final Cancha cancha = getCancha();

        // Recycler view
        layoutManager = new LinearLayoutManager(getActivity());
        horariosRecyclerView.setLayoutManager(layoutManager);

        // Adapter
        adapter = new HorariosAdapter(cancha, getFecha());
        horariosRecyclerView.setAdapter(adapter);

        // Datos
        refDatos = DataBase.getInstancia().getReferenceAlquileres(cancha.getDatosClub().getIdClub(), cancha.getUuid(), getFecha());
        refDatos.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                actualizarLista(dataSnapshot);
            }
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                actualizarLista(dataSnapshot);
            }
            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                // NO DEBERÍA PASAR
            }
            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                // NO DEBERÍA PASAR
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getContext(), R.string.txtErrorDescargandoInfo, Toast.LENGTH_LONG).show();
            }

            private void actualizarLista(DataSnapshot snapshotAlquiler) {
                Alquiler alquiler = snapshotAlquiler.getValue(Alquiler.class);
                adapter.actualizarLista(cancha.getDatosClub().getRangoHorario(), alquiler);
            }

        });

    }

    private String getTextoFecha() {
        Date dia = (Date) getArguments().getSerializable(ARG_DIA);
        return textoDia(dia);
    }

    private boolean primerDia() {
        return getArguments().getInt(ARG_POSICION_DIA) == 0;
    }

    private boolean ultimoDia() {
        return getArguments().getInt(ARG_POSICION_DIA) == 7;
    }

    private Cancha getCancha() {
        return (Cancha) getArguments().getSerializable(ARG_CANCHA);
    }

    private Date getFecha() {
        return (Date) getArguments().getSerializable(ARG_DIA);
    }

}