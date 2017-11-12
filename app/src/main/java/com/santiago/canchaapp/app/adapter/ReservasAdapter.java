package com.santiago.canchaapp.app.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.santiago.canchaapp.R;
import com.santiago.canchaapp.app.otros.AccionesSobreReserva;
import com.santiago.canchaapp.app.otros.TipoReservas;
import com.santiago.canchaapp.app.viewholder.ReservaViewHolder;
import com.santiago.canchaapp.dominio.Reserva;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import static com.santiago.canchaapp.app.otros.DateUtils.dateToStringtoSave;
import static com.santiago.canchaapp.app.otros.DateUtils.hora;
import static com.santiago.canchaapp.app.otros.DateUtils.hoy;
import static com.santiago.canchaapp.app.otros.DateUtils.stringToDateToSave;

public class ReservasAdapter extends RecyclerView.Adapter<ReservaViewHolder> {

    private List<Reserva> reservas;
    private TipoReservas tipoReservas;
    private AccionesSobreReserva accionesSobreReserva;
    private final Activity activity;
    private String hoy;
    private int horaActual;

    public ReservasAdapter(Activity activity, TipoReservas tipoReservas, AccionesSobreReserva accionesSobreReserva) {
        this.reservas = new ArrayList<>();
        this.tipoReservas = tipoReservas;
        this.accionesSobreReserva = accionesSobreReserva;
        this.activity = activity;
        Date dia = hoy();
        this.hoy = dateToStringtoSave(dia);
        this.horaActual = hora(dia);
    }

    public void actualizarLista(Reserva reserva) {
        actualizarReservas(reserva);
    }

    @Override
    public ReservaViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        return new ReservaViewHolder(
                LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_reserva, viewGroup, false),
                activity
        );
    }

    @Override
    public void onBindViewHolder(ReservaViewHolder viewHolder, final int position) {
        viewHolder.cargarDatosEnVista(reservas.get(position), accionesSobreReserva, hoy);
    }

    @Override
    public int getItemCount() {
        return reservas.size();
    }

    // Auxiliar

    private void actualizarReservas(Reserva reservaActualizada) {
        if (reservaActualizada.getFecha().equals(hoy) && reservaActualizada.getHora() <= horaActual) {
            return; // Alquiler de hoy, pero en hora pasada
        }
        Integer i = indiceDeReserva(reservaActualizada);
        if (i == null) {
            // Sólo se debe agregar una reserva nueva si es de este estado
            if (reservaActualizada.getEstado() == tipoReservas.toEstado()) {
                int indice = indiceDeInsercion(reservaActualizada);
                reservas.add(indice, reservaActualizada);
                notifyItemInserted(indice);
            }
        } else {
            if (reservaActualizada.getEstado() != tipoReservas.toEstado()) {
                // si cambió el estado se saca de la lista
                reservas.remove((int) i);
                notifyItemRemoved(i);
            } else {
                // en otro caso se la modifica
                reservas.set(i, reservaActualizada);
                notifyItemChanged(i);
            }
        }
    }

    private Integer indiceDeReserva(Reserva reserva) {
        for (int i = 0; i < reservas.size(); i++) {
            if (Objects.equals(reservas.get(i).getUuid(), reserva.getUuid())) {
                return i;
            }
        }
        return null;
    }

    private int indiceDeInsercion(Reserva reserva) {
        Date fechaReserva = stringToDateToSave(reserva.getFecha());
        int horaReserva = reserva.getHora();
        int i;
        for (i = 0; i < reservas.size(); i++) {
            Date otraFecha = stringToDateToSave(reservas.get(i).getFecha());
            int otraHora = reservas.get(i).getHora();
            if (fechaReserva.before(otraFecha) || (fechaReserva.equals(otraFecha) && horaReserva < otraHora)) {
                return i;
            }
        }
        return i;
    }

}
