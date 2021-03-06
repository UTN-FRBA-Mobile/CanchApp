package com.santiago.canchaapp.app.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.santiago.canchaapp.R;
import com.santiago.canchaapp.app.adapter.ReservasAdapter;
import com.santiago.canchaapp.app.otros.AccionesSobreReserva;
import com.santiago.canchaapp.app.otros.DateUtils;
import com.santiago.canchaapp.app.otros.TipoReservas;
import com.santiago.canchaapp.dominio.DataBase;
import com.santiago.canchaapp.dominio.Reserva;
import com.santiago.canchaapp.servicios.Sesion;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.google.firebase.database.DatabaseError.PERMISSION_DENIED;
import static com.santiago.canchaapp.app.otros.AccionesSobreReserva.NINGUNA;
import static com.santiago.canchaapp.app.otros.AccionesSobreReserva.SOLO_CANCELAR;
import static com.santiago.canchaapp.app.otros.TipoReservas.APROBADAS;
import static com.santiago.canchaapp.app.otros.TipoReservas.PENDIENTES;
import static com.santiago.canchaapp.app.otros.TipoReservas.valueOf;

public class ListaReservasFragment extends Fragment {

    private static String ARG_TIPO_RESERVAS = "tipo_reservas";

    @BindView(R.id.recycler_view_reservas)
    public RecyclerView reservasRecyclerView;
    @BindView(R.id.sinReservas)
    public TextView sinReservas;

    private RecyclerView.LayoutManager layoutManager;

    private ReservasAdapter adapter;

    public static ListaReservasFragment nuevaInstancia(TipoReservas tipo) {
        ListaReservasFragment fragment = new ListaReservasFragment();

        Bundle args = new Bundle();
        args.putString(ARG_TIPO_RESERVAS, tipo.toString());
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_lista_reservas, container, false);

        cargarVista(rootView);
        return rootView;
    }

    private void cargarVista(View rootView) {
        ButterKnife.bind(this, rootView);

        sinReservas.setText("No hay reservas " + tipoReservas().toString().toLowerCase());

        // Recycler view
        layoutManager = new LinearLayoutManager(getActivity());
        reservasRecyclerView.setLayoutManager(layoutManager);

        // Adapter
        adapter = new ReservasAdapter(getActivity(), tipoReservas(), accionesSobreReservas());
        reservasRecyclerView.setAdapter(adapter);

        // Datos
        Query ref = DataBase.getInstancia().getReferenceReservasActuales(Sesion.getInstancia().getUsuario().getUid(), DateUtils.hoy());
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
                if (databaseError.getCode() != PERMISSION_DENIED) {
                    Toast.makeText(getContext(), R.string.txtErrorDescargandoInfo, Toast.LENGTH_LONG).show();
                }
            }

            private void actualizarLista(DataSnapshot snapshotReserva) {
                Reserva reserva = snapshotReserva.getValue(Reserva.class);
                if (reserva.getEstado() == tipoReservas().toEstado())
                    sinReservas.setVisibility(GONE);

                adapter.actualizarLista(reserva);

                if (adapter.noQuedaronReservas())
                    sinReservas.setVisibility(VISIBLE);
            }

        });
    }

    private AccionesSobreReserva accionesSobreReservas() {
        TipoReservas tipo = tipoReservas();
        if (tipo == PENDIENTES || tipo == APROBADAS) {
            return SOLO_CANCELAR;
        }
        return NINGUNA;
    }

    private TipoReservas tipoReservas() { return valueOf(getArguments().getString(ARG_TIPO_RESERVAS)); }

}