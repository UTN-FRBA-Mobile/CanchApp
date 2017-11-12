package com.santiago.canchaapp.app.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.santiago.canchaapp.R;
import com.santiago.canchaapp.app.adapter.HorariosAdapter;
import com.santiago.canchaapp.dominio.Alquiler;
import com.santiago.canchaapp.dominio.Cancha;
import com.santiago.canchaapp.dominio.Club;
import com.santiago.canchaapp.dominio.DataBase;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.view.View.FIND_VIEWS_WITH_CONTENT_DESCRIPTION;
import static android.view.View.INVISIBLE;
import static com.google.firebase.database.DatabaseError.PERMISSION_DENIED;
import static com.santiago.canchaapp.app.otros.DateUtils.hora;
import static com.santiago.canchaapp.app.otros.DateUtils.textoDia;

public class ListaHorariosFragment extends Fragment {

    private static String ARG_CANCHA = "cancha";
    private static String ARG_MI_CANCHA = "mi_cancha";
    private static String ARG_DIA = "dia";
    private static String ARG_VIEW_PAGER = "pager";
    private static String ARG_POSICION_DIA = "pos_dia";
    private RecyclerView.LayoutManager layoutManager;
    private HorariosAdapter adapter;
    private Query refDatos;
    private List<Alquiler> alquileresCargados = new ArrayList<>();

    @BindView(R.id.fecha_horarios)
    public TextView fecha;
    @BindView(R.id.dia_prev)
    public ImageView flechaDiaAnterior;
    @BindView(R.id.dia_next)
    public ImageView flechaDiaSiguiente;
    @BindView(R.id.recycler_view_horarios)
    public RecyclerView horariosRecyclerView;

    public static ListaHorariosFragment nuevaInstancia(Cancha cancha, Date dia, int posicionDia, boolean esMiCancha) {
        ListaHorariosFragment fragment = new ListaHorariosFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_CANCHA, cancha);
        args.putBoolean(ARG_MI_CANCHA, esMiCancha);
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

    private void changeVisibility(int posicion) {
        ViewPager viewPager = getActivity().findViewById(R.id.container_horarios);
        viewPager.setCurrentItem(posicion);
    }

    private void cargarVista(View rootView) {
        ButterKnife.bind(this, rootView);
        setOnClickFlechas();

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
        ((SimpleItemAnimator) horariosRecyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        ((SimpleItemAnimator) horariosRecyclerView.getItemAnimator()).setMoveDuration(0);

        // Para setear adapter
        getClub(cancha.getIdClub());

        // Datos
        refDatos = DataBase.getInstancia().getReferenceAlquileres(cancha.getIdClub(), cancha.getUuid(), getFecha());
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
                if (databaseError.getCode() != PERMISSION_DENIED)
                    Toast.makeText(getContext(), R.string.txtErrorDescargandoInfo, Toast.LENGTH_LONG).show();
            }

            private void actualizarLista(DataSnapshot snapshotAlquiler) {
                Alquiler alquiler = snapshotAlquiler.getValue(Alquiler.class);
                if (adapter != null) {
                    adapter.actualizarLista(alquiler);
                    if (alquileresCargados.size() > 0) {
                        for (Alquiler alq : alquileresCargados) {
                            adapter.actualizarLista(alq);
                        }
                        alquileresCargados.clear();
                    }
                } else {
                    alquileresCargados.add(alquiler);
                }
            }

        });

    }

    private void setOnClickFlechas() {
        flechaDiaAnterior.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                changeVisibility(getPosicionDia() - 1);
            }
        });
        flechaDiaSiguiente.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                changeVisibility(getPosicionDia() + 1);
            }
        });
    }

    private void getClub(String idClub){
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue() != null){
                    Club club = dataSnapshot.getValue(Club.class);
                    Cancha cancha = getCancha();
                    if (adapter == null) {
                        HorariosAdapter horariosAdapter = new HorariosAdapter(getActivity(), cancha, club, getFecha(),
                                esMiCancha(), cancha.getRangoHorario(), hora(getFecha()), primerDia());
                        adapter = horariosAdapter;
                    }
                    horariosRecyclerView.setAdapter(adapter);
                    for (Alquiler alquiler : alquileresCargados) {
                        adapter.actualizarLista(alquiler);
                    }
                    alquileresCargados.clear();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
        DatabaseReference referenceClub = DataBase.getInstancia().getReferenceClub(idClub);
        referenceClub.addListenerForSingleValueEvent(valueEventListener);
    }

    private String getTextoFecha() {
        Date dia = (Date) getArguments().getSerializable(ARG_DIA);
        return textoDia(dia);
    }

    private boolean primerDia() {
        return getPosicionDia() == 0;
    }

    private boolean ultimoDia() {
        return getPosicionDia() == 7;
    }

    private int getPosicionDia() { return getArguments().getInt(ARG_POSICION_DIA) ; }

    private Cancha getCancha() {
        return (Cancha) getArguments().getSerializable(ARG_CANCHA);
    }

    private boolean esMiCancha() {
        return getArguments().getBoolean(ARG_MI_CANCHA);
    }

    private Date getFecha() {
        return (Date) getArguments().getSerializable(ARG_DIA);
    }

}