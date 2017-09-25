package com.santiago.canchaapp.app.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.santiago.canchaapp.R;
import com.santiago.canchaapp.app.viewholder.CanchaViewHolder;
import com.santiago.canchaapp.dominio.Cancha;

import java.util.List;

public class CanchasAdapter extends RecyclerView.Adapter<CanchaViewHolder> {

    private List<Cancha> canchas;

    public CanchasAdapter(List<Cancha> canchas) {
        this.canchas = canchas;
    }

    @Override
    public CanchaViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        return new CanchaViewHolder(
                LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_cancha, viewGroup, false)
        );
    }

    @Override
    public void onBindViewHolder(CanchaViewHolder viewHolder, int position) {
        viewHolder.cargarDatosEnVista(canchas.get(position));
    }

    @Override
    public int getItemCount() {
        return canchas.size();
    }
}
