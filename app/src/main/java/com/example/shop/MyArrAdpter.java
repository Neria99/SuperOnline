package com.example.shop;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.squareup.picasso.Picasso;
import java.util.List;

public class MyArrAdpter extends ArrayAdapter<Item> {
    public MyArrAdpter(@NonNull Context context, int resource, @NonNull List<Item> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(getContext()).inflate(R.layout.myror,parent,false);
        TextView price = convertView.findViewById(R.id.tvPrice);
        TextView type = convertView.findViewById(R.id.tvType);
        TextView name = convertView.findViewById(R.id.tvName);
        ImageView image = convertView.findViewById(R.id.imageView2);

        price.setText(String.valueOf(getItem(position).price));
        name.setText(getItem(position).name);
        type.setText(getItem(position).type);

        if(getItem(position).link != null && !getItem(position).link.isEmpty())
            Picasso.get().load(getItem(position).link).into(image);

        return convertView;
    }
}
