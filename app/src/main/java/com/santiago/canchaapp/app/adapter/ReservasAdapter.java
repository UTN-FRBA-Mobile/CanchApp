package com.santiago.canchaapp.app.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.santiago.canchaapp.R;
import com.santiago.canchaapp.app.otros.AccionesSobreReserva;
import com.santiago.canchaapp.app.otros.TipoReservas;
import com.santiago.canchaapp.app.viewholder.ReservaViewHolder;
import com.santiago.canchaapp.dominio.Reserva;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ReservasAdapter extends RecyclerView.Adapter<ReservaViewHolder> {

    private List<Reserva> reservas;

    private TipoReservas tipoReservas;

    private AccionesSobreReserva accionesSobreReserva;

    public ReservasAdapter(TipoReservas tipoReservas, AccionesSobreReserva accionesSobreReserva) {
        this.reservas = new ArrayList<>();
        this.tipoReservas = tipoReservas;
        this.accionesSobreReserva = accionesSobreReserva;
    }

    public void actualizarLista(Reserva reserva) {
        if (actualizarReservas(reserva)) {
            this.notifyDataSetChanged();
        }
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

    // Auxiliar

    private boolean actualizarReservas(Reserva reservaActualizada) {
        Integer i = indiceDeReserva(reservaActualizada);
        if (i == null) {
            // Sólo se debe agregar una reserva nueva si es de este estado
            if (reservaActualizada.getEstado() == tipoReservas.toEstado()) {
                reservas.add(reservaActualizada);
            } else {
                return false;
            }
        } else {
            if (reservaActualizada.getEstado() != tipoReservas.toEstado()) {
                // si cambió el estado se saca de la lista
                reservas.remove((int) i);
            } else {
                // en otro caso se la modifica
                reservas.set(i, reservaActualizada);
            }
        }
        return true;
    }

    private Integer indiceDeReserva(Reserva reserva) {
        for (int i = 0; i < reservas.size(); i++) {
            if (Objects.equals(reservas.get(i).getUuid(), reserva.getUuid())) {
                return i;
            }
        }
        return null;
    }

}
