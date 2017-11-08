package com.santiago.canchaapp.app.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

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
    public View getView(int i, View view, ViewGroup viewGroup) {
        // 1
        final Uri fotoUrl = fotos.get(i);

        // 2
        ImageView imageView;
        if (view == null) {
            imageView = new ImageView(context);
            imageView.setLayoutParams(new GridView.LayoutParams(300, 300));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        } else {
            imageView = (ImageView) view;
        }

        // 3
        Picasso.with(context).load(fotoUrl).into(imageView);
        return imageView;
    }

}
