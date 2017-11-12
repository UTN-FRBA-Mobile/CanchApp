package com.santiago.canchaapp.app.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.santiago.canchaapp.R;
import com.santiago.canchaapp.app.viewholder.FotoCanchaView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class FotosCanchaAdapter extends BaseAdapter {

    private List<Uri> fotos;
    private Context context;

    public FotosCanchaAdapter(Context context) {
        this.context = context;
        fotos = new ArrayList<>();
    }

    public void agregarFotos(List<Uri> fotos) {
        this.fotos.addAll(fotos);
        notifyDataSetChanged();
    }

    public List<Uri> getFotos() {
        return fotos;
    }

    @Override
    public int getCount() {
        return fotos.size();
    }

    @Override
    public Object getItem(int i) {
        return null; // innecesario
    }

    @Override
    public long getItemId(int i) {
        return 0; // innecesario
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(LAYOUT_INFLATER_SERVICE);
        View view = new View(context);
        view = inflater.inflate(R.layout.item_foto_cancha_grid, null);

        final Uri fotoUrl = fotos.get(i);
        FotoCanchaView imageView = view.findViewById(R.id.item_foto_cancha);
        Picasso.with(context).load(fotoUrl).fit().centerCrop().into(imageView);

        return view;
    }

}
