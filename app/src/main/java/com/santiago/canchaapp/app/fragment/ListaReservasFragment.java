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
import com.santiago.canchaapp.app.otros.TipoReservas;
import com.santiago.canchaapp.dominio.Reserva;
import com.santiago.canchaapp.servicios.Servidor;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListaReservasFragment extends Fragment {

    private static String ARG_TIPO_RESERVAS = "tipo_reservas";

    @BindView(R.id.recycler_view_reservas)
    public RecyclerView reservasRecyclerView;

    private RecyclerView.LayoutManager layoutManager;

    private ReservasAdapter adapter;

    private List<Reserva> reservas;

    public ListaReservasFragment() {
    }

    public static ListaReservasFragment newInstance(TipoReservas type) {
        ListaReservasFragment fragment = new ListaReservasFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TIPO_RESERVAS, type.toString());
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cargarDatosDeReservas();
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
        adapter = new ReservasAdapter(reservas);
        reservasRecyclerView.setAdapter(adapter);
    }

    private void cargarDatosDeReservas() {
        TipoReservas tipo = TipoReservas.valueOf(getArguments().getString(ARG_TIPO_RESERVAS));
        Servidor servidor = Servidor.instancia();
        switch(tipo) {
            case APROBADAS:
                reservas = servidor.reservasAprobadas();
                break;
            case CANCELADAS:
                reservas = servidor.reservasCanceladas();
                break;
            case PENDIENTES:
                reservas = servidor.reservasPendientes();
                break;
        }
    }
}