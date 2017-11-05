package com.santiago.canchaapp.app.adapter;

import android.content.Context;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.santiago.canchaapp.R;
import com.santiago.canchaapp.app.fragment.CanchaFragment;
import com.santiago.canchaapp.app.otros.RecyclerViewOnItemClickListener;
import com.santiago.canchaapp.app.viewholder.CanchaViewHolder;
import com.santiago.canchaapp.dominio.Cancha;
import com.santiago.canchaapp.dominio.Club;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.santiago.canchaapp.app.otros.FragmentTags.CANCHA;

public class CanchasAdapter extends RecyclerView.Adapter<CanchaViewHolder> implements RecyclerViewOnItemClickListener {

    private List<Cancha> canchas;

    private boolean esMiClub;

    private Context context;

    public CanchasAdapter(Context context, boolean esMiClub) {
        this.canchas = new ArrayList<>();
        this.context = context;
        this.esMiClub = esMiClub;
    }

    @Override
    public CanchaViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        return new CanchaViewHolder(
                LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_cancha, viewGroup, false),
                this
        );
    }

    public void actualizarLista(Cancha cancha) {
        actualizarCanchas(cancha);
        this.notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(CanchaViewHolder viewHolder, int position) {
        viewHolder.cargarDatosEnVista(canchas.get(position));
    }

    @Override
    public int getItemCount() {
        return canchas.size();
    }

    @Override
    public void onClick(View v, final int posicion) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ((AppCompatActivity)context).getSupportFragmentManager().beginTransaction()
                        .replace(R.id.content_frame, CanchaFragment.nuevaInstancia(canchas.get(posicion), esMiClub), CANCHA.toString())
                        .addToBackStack(null)
                        .commit();
            }
        }, 250);
    }

    private void actualizarCanchas(Cancha cancha) {
        Integer i = indiceDeCancha(cancha);
        if (i == null) {
            canchas.add(cancha);
        } else {
            canchas.set(i, cancha);
        }
    }

    private Integer indiceDeCancha(Cancha cancha) {
        for (int i = 0; i < canchas.size(); i++) {
            if (Objects.equals(canchas.get(i).getUuid(), cancha.getUuid())) {
                return i;
            }
        }
        return null;
    }
}
