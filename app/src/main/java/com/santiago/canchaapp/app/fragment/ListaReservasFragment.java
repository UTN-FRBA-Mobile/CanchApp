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

import static com.santiago.canchaapp.app.otros.TipoReservas.PENDIENTES;
import static com.santiago.canchaapp.app.otros.TipoReservas.valueOf;

public class ListaReservasFragment extends Fragment {

    private static String ARG_TIPO_RESERVAS = "tipo_reservas";

    private static String ARG_ALQUILERES = "alquileres";

    @BindView(R.id.recycler_view_reservas)
    public RecyclerView reservasRecyclerView;

    private RecyclerView.LayoutManager layoutManager;

    private ReservasAdapter adapter;

    private List<Reserva> reservas;

    public ListaReservasFragment() {
    }

    public static ListaReservasFragment nuevaInstancia(TipoReservas type, Boolean paraAlquileres) {
        ListaReservasFragment fragment = new ListaReservasFragment();

        Bundle args = new Bundle();
        args.putString(ARG_TIPO_RESERVAS, type.toString());
        args.putBoolean(ARG_ALQUILERES, paraAlquileres);

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
        adapter = new ReservasAdapter(reservas, sonAlquileres() && tipoReservas() == PENDIENTES);
        reservasRecyclerView.setAdapter(adapter);
    }

    private void cargarDatosDeReservas() {
        Servidor servidor = Servidor.instancia();
        switch(tipoReservas()) {
            case APROBADAS:
                reservas = sonAlquileres() ? servidor.getAlquileresAprobados() : servidor.getReservasAprobadas();
                break;
            case CANCELADAS:
                reservas = sonAlquileres() ? servidor.getAlquileresCancelados() : servidor.getReservasCanceladas();
                break;
            case PENDIENTES:
                reservas = sonAlquileres() ? servidor.getAlquileresPendientes() : servidor.getReservasPendientes();
                break;
        }
    }

    private Boolean sonAlquileres() {
        return getArguments().getBoolean(ARG_ALQUILERES);
    }

    private TipoReservas tipoReservas() {
        return valueOf(getArguments().getString(ARG_TIPO_RESERVAS));
    }

}