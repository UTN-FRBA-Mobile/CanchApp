package com.santiago.canchaapp.app.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.santiago.canchaapp.R;
import com.santiago.canchaapp.app.otros.AccionesSobreReserva;
import com.santiago.canchaapp.app.viewholder.ReservaViewHolder;
import com.santiago.canchaapp.dominio.Reserva;

import java.util.List;

public class ReservasAdapter extends RecyclerView.Adapter<ReservaViewHolder> {

    private List<Reserva> reservas;

    private AccionesSobreReserva accionesSobreReserva;

    public ReservasAdapter(List<Reserva> reservas, AccionesSobreReserva accionesSobreReserva) {
        this.reservas = reservas;
        this.accionesSobreReserva = accionesSobreReserva;
    }

    @Override
    public ReservaViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        return new ReservaViewHolder(
                LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_reserva, viewGroup, false)
        );
    }

    @Override
    public void onBindViewHolder(ReservaViewHolder viewHolder, final int position) {
        viewHolder.cargarDatosEnVista(reservas.get(position), accionesSobreReserva);
    }

    @Override
    public int getItemCount() {
        return reservas.size();
    }

}
