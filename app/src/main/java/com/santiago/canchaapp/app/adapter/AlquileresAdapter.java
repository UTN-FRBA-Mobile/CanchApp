package com.santiago.canchaapp.app.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.santiago.canchaapp.R;
import com.santiago.canchaapp.app.otros.AccionesSobreReserva;
import com.santiago.canchaapp.app.otros.DateUtils;
import com.santiago.canchaapp.app.otros.TipoReservas;
import com.santiago.canchaapp.app.viewholder.AlquilerViewHolder;
import com.santiago.canchaapp.dominio.Alquiler;
import com.santiago.canchaapp.dominio.Reserva;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import static com.santiago.canchaapp.app.otros.DateUtils.*;

public class AlquileresAdapter extends RecyclerView.Adapter<AlquilerViewHolder> {

    private List<Alquiler> alquileres;

    private TipoReservas tipoReservas;

    private AccionesSobreReserva accionesSobreAlquiler;

    private Activity activity;

    private String hoy;

    private int horaActual;

    public AlquileresAdapter(TipoReservas tipoReservas, AccionesSobreReserva accionesSobreAlquiler, Activity activity) {
        this.alquileres = new ArrayList<>();
        this.tipoReservas = tipoReservas;
        this.accionesSobreAlquiler = accionesSobreAlquiler;
        this.activity = activity;
        Date dia = hoy();
        this.hoy = dateToString(dia);
        this.horaActual = hora(dia);
    }

    public void actualizarLista(Alquiler alquiler) {
        actualizarAlquileres(alquiler);
    }

    @Override
    public AlquilerViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        return new AlquilerViewHolder(
                LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_alquiler, viewGroup, false),
                activity
        );
    }

    @Override
    public void onBindViewHolder(AlquilerViewHolder viewHolder, final int position) {
        viewHolder.cargarDatosEnVista(alquileres.get(position), accionesSobreAlquiler, hoy);
    }

    @Override
    public int getItemCount() {
        return alquileres.size();
    }

    // Auxiliar

    private void actualizarAlquileres(Alquiler alquilerActualizado) {
        if (alquilerActualizado.getFecha().equals(hoy) && alquilerActualizado.getHora() <= horaActual) {
            return; // Alquiler de hoy, pero en hora pasada
        }
        Integer i = indiceDeAlquiler(alquilerActualizado);
        if (i == null) {
            // Sólo se debe agregar una reserva nueva si es de este estado
            if (alquilerActualizado.getEstado() == tipoReservas.toEstado()) {
                alquileres.add(alquilerActualizado);
                notifyItemInserted(alquileres.size() - 1);
            }
        } else {
            if (alquilerActualizado.getEstado() != tipoReservas.toEstado()) {
                // si cambió el estado se saca de la lista
                alquileres.remove((int) i);
                notifyItemRemoved(i);
            } else {
                // en otro caso se la modifica
                alquileres.set(i, alquilerActualizado);
                notifyItemChanged(i);
            }
        }
    }

    private Integer indiceDeAlquiler(Alquiler alquiler) {
        for (int i = 0; i < alquileres.size(); i++) {
            if (Objects.equals(alquileres.get(i).getUuid(), alquiler.getUuid())) {
                return i;
            }
        }
        return null;
    }

}
