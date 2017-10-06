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

import java.util.List;

import static com.santiago.canchaapp.app.otros.FragmentTags.CANCHA;

public class CanchasAdapter extends RecyclerView.Adapter<CanchaViewHolder> implements RecyclerViewOnItemClickListener {

    private List<Cancha> canchas;

    private Context context;

    public CanchasAdapter(Context context, List<Cancha> canchas) {
        this.canchas = canchas;
        this.context = context;
    }

    @Override
    public CanchaViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        return new CanchaViewHolder(
                LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_cancha, viewGroup, false),
                this
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

    // Podría existir una clase que se encargue de esto específicamente
    @Override
    public void onClick(View v, int posicion) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ((AppCompatActivity)context).getSupportFragmentManager().beginTransaction()
                        .replace(R.id.content_frame, CanchaFragment.nuevaInstancia(), CANCHA.toString())
                        .addToBackStack(null)
                        .commit();
            }
        }, 250);
    }
}
