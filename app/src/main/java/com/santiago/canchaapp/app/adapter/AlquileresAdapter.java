package com.santiago.canchaapp.app.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.santiago.canchaapp.R;
import com.santiago.canchaapp.app.otros.AccionesSobreReserva;
import com.santiago.canchaapp.app.otros.TipoReservas;
import com.santiago.canchaapp.app.viewholder.AlquilerViewHolder;
import com.santiago.canchaapp.dominio.Alquiler;
import com.santiago.canchaapp.dominio.Reserva;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AlquileresAdapter extends RecyclerView.Adapter<AlquilerViewHolder> {

    private List<Alquiler> alquileres;

    private TipoReservas tipoReservas;

    private AccionesSobreReserva accionesSobreAlquiler;

    public AlquileresAdapter(TipoReservas tipoReservas, AccionesSobreReserva accionesSobreAlquiler) {
        this.alquileres = new ArrayList<>();
        this.tipoReservas = tipoReservas;
        this.accionesSobreAlquiler = accionesSobreAlquiler;
    }

    public void actualizarLista(Alquiler alquiler) {
        if (actualizarAlquileres(alquiler)) {
            this.notifyDataSetChanged();
        }
    }

    @Override
    public AlquilerViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        return new AlquilerViewHolder(
                LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_alquiler, viewGroup, false)
        );
    }

    @Override
    public void onBindViewHolder(AlquilerViewHolder viewHolder, final int position) {
        viewHolder.cargarDatosEnVista(alquileres.get(position), accionesSobreAlquiler);
    }

    @Override
    public int getItemCount() {
        return alquileres.size();
    }

    // Auxiliar

    private boolean actualizarAlquileres(Alquiler alquilerActualizado) {
        Integer i = indiceDeAlquiler(alquilerActualizado);
        if (i == null) {
            // Sólo se debe agregar una reserva nueva si es de este estado
            if (alquilerActualizado.getEstado() == tipoReservas.toEstado()) {
                alquileres.add(alquilerActualizado);
            } else {
                return false;
            }
        } else {
            if (alquilerActualizado.getEstado() != tipoReservas.toEstado()) {
                // si cambió el estado se saca de la lista
                alquileres.remove((int) i);
            } else {
                // en otro caso se la modifica
                alquileres.set(i, alquilerActualizado);
            }
        }
        return true;
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
