package com.santiago.canchaapp.app.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.santiago.canchaapp.R;
import com.santiago.canchaapp.app.adapter.ReservasAdapter;
import com.santiago.canchaapp.app.otros.AccionesSobreReserva;
import com.santiago.canchaapp.app.otros.TipoReservas;
import com.santiago.canchaapp.dominio.Reserva;
import com.santiago.canchaapp.servicios.Servidor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.santiago.canchaapp.app.otros.AccionesSobreReserva.NINGUNA;
import static com.santiago.canchaapp.app.otros.AccionesSobreReserva.SOLO_CANCELAR;
import static com.santiago.canchaapp.app.otros.AccionesSobreReserva.TODAS;
import static com.santiago.canchaapp.app.otros.TipoReservas.APROBADAS;
import static com.santiago.canchaapp.app.otros.TipoReservas.PENDIENTES;
import static com.santiago.canchaapp.app.otros.TipoReservas.valueOf;

public class ListaReservasFragment extends Fragment {

    private static String ARG_TIPO_RESERVAS = "tipo_reservas";

    private static String ARG_ALQUILERES = "alquileres";

    private static String ARG_RESERVAS = "reservas";

    @BindView(R.id.recycler_view_reservas)
    public RecyclerView reservasRecyclerView;

    private RecyclerView.LayoutManager layoutManager;

    private ReservasAdapter adapter;

    public static ListaReservasFragment nuevaInstancia(TipoReservas tipo, Boolean paraAlquileres) {
        ListaReservasFragment fragment = new ListaReservasFragment();

        Bundle args = new Bundle();
        args.putString(ARG_TIPO_RESERVAS, tipo.toString());
        args.putBoolean(ARG_ALQUILERES, paraAlquileres);
        args.putSerializable(ARG_RESERVAS, (Serializable) datosDeReservas(tipo, paraAlquileres));

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

        // Recycler view
        layoutManager = new LinearLayoutManager(getActivity());
        reservasRecyclerView.setLayoutManager(layoutManager);

        // Adapter
        adapter = new ReservasAdapter(reservas(), accionesSobreReservas());
        reservasRecyclerView.setAdapter(adapter);
    }

    private static List<Reserva> datosDeReservas(TipoReservas tipo, Boolean sonAlquileres) {
        Servidor servidor = Servidor.instancia();
        switch(tipo) {
            case APROBADAS:
                return sonAlquileres ? servidor.getAlquileresAprobados() : servidor.getReservasAprobadas();
            case CANCELADAS:
                return sonAlquileres ? servidor.getAlquileresCancelados() : servidor.getReservasCanceladas();
            case PENDIENTES:
                return sonAlquileres ? servidor.getAlquileresPendientes() : servidor.getReservasPendientes();
        }
        return new ArrayList<Reserva>();
    }

    private AccionesSobreReserva accionesSobreReservas() {
        Boolean alquileres = sonAlquileres();
        TipoReservas tipo = tipoReservas();
        if (alquileres && tipo == PENDIENTES) {
            return TODAS;
        } else if (tipo == APROBADAS || (tipo == PENDIENTES && !sonAlquileres())) {
            return SOLO_CANCELAR;
        }
        return NINGUNA;
    }

    private Boolean sonAlquileres() {
        return getArguments().getBoolean(ARG_ALQUILERES);
    }

    private TipoReservas tipoReservas() {
        return valueOf(getArguments().getString(ARG_TIPO_RESERVAS));
    }

    private List<Reserva> reservas() {
        return (List<Reserva>) getArguments().getSerializable(ARG_RESERVAS);
    }

}